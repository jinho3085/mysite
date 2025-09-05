package com.bit2025.mysite.controller;

import com.bit2025.mysite.service.BoardService;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 목록
    @GetMapping("")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "search", required = false) String search,
            Model model) {

        int pageSize = 5;
        List<BoardVo> posts = boardService.getPage(search, page, pageSize);
        int totalCount = boardService.getTotalCount(search);
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("posts", posts);
        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);

        return "board/list";
    }

    // 글쓰기 폼
    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    // 글쓰기 처리
    @PostMapping("/write")
    public String write(
            @ModelAttribute BoardVo vo,
            @RequestParam("uploadFile") MultipartFile file,
            HttpSession session) throws IOException {

        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if (authUser == null) {
            return "redirect:/user/login";
        }

        vo.setWriter(authUser.getName());

        // 파일 업로드
        if (!file.isEmpty()) {
            String uploadPath = session.getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadDir, fileName));
            vo.setFileName(fileName);
        }

        boardService.addPost(vo);
        return "redirect:/board";
    }

    // 게시글 보기
    @GetMapping("/view/{no}")
    public String view(@PathVariable("no") Long no, Model model) {
        BoardVo post = boardService.findById(no);
        if (post == null) {
            return "redirect:/board";
        }
        boardService.increaseViewCount(no);
        model.addAttribute("post", post);
        return "board/view";
    }

    // 수정 폼
    @GetMapping("/update/{no}")
    public String updateForm(@PathVariable("no") Long no, HttpSession session, Model model) {
        BoardVo post = boardService.findById(no);
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        if (post == null || authUser == null || !authUser.getName().equals(post.getWriter())) {
            return "redirect:/board";
        }
        model.addAttribute("post", post);
        return "board/modify";
    }

    // 수정 처리
    @PostMapping("/update/{no}")
    public String update(
            @PathVariable("no") Long no,
            @ModelAttribute BoardVo vo, 
            @RequestParam(value = "uploadFile", required = false) MultipartFile file,
            @RequestParam(value = "deleteFile", required = false) String deleteFile,
            @RequestParam(value = "redirectType", required = false, defaultValue = "view") String redirectType, 
            HttpSession session) throws IOException {

        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if (authUser == null) return "redirect:/user/login";

        BoardVo post = boardService.findById(no);
        if (post == null || !authUser.getName().equals(post.getWriter())) {
            return "redirect:/board";
        }

        // 제목/내용 null 체크 후 기존 값 유지
        String title = vo.getTitle();
        String contents = vo.getContents();

        if (title == null || title.trim().isEmpty()) {
            title = post.getTitle();
        }
        if (contents == null || contents.trim().isEmpty()) {
            contents = post.getContents();
        }

        post.setNo(no);
        post.setTitle(title);
        post.setContents(contents);
        
        String uploadPath = session.getServletContext().getRealPath("/uploads");

        // 파일 삭제
        if ("true".equals(deleteFile) && post.getFileName() != null) {
            new File(uploadPath, post.getFileName()).delete();
            post.setFileName(null);
        }

        // 새 파일 업로드
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath, fileName));

            if (post.getFileName() != null) {
                new File(uploadPath, post.getFileName()).delete();
            }
            post.setFileName(fileName);
        }

        // DB 업데이트
        boardService.updatePost(post);

        // redirectType에 따른 이동
        if ("edit".equals(redirectType)) {
            return "redirect:/board/update/" + no; // 수정 화면으로
        } else {
            return "redirect:/board/view/" + no; // 글보기
        }
    }


    // 삭제
    @GetMapping("/delete/{no}")
    public String delete(@PathVariable("no") Long no, HttpSession session) {
        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if (authUser != null) {
            boardService.deletePost(no, authUser.getName());
        }
        return "redirect:/board";
    }

    // 답글 폼
    @GetMapping("/reply/{no}")
    public String replyForm(@PathVariable("no") Long parentno, Model model) {
        BoardVo parent = boardService.findById(parentno);
        model.addAttribute("parentPost", parent);
        return "board/reply";
    }

    // 답글 처리
    @PostMapping("/reply/{no}")
    public String reply(
            @PathVariable("no") Long parentno,
            @ModelAttribute BoardVo vo,
            HttpSession session) {

        UserVo authUser = (UserVo) session.getAttribute("authUser");
        if (authUser != null) {
            vo.setWriter(authUser.getName());
            boardService.addReply(parentno, vo);
        }
        return "redirect:/board";
    }
}
