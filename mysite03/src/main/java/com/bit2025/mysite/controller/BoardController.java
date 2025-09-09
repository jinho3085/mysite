package com.bit2025.mysite.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2025.mysite.service.BoardService;
import com.bit2025.mysite.vo.BoardVo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("")
    public String list(Model model,
                       @RequestParam(value="search", required=false) String search,
                       @RequestParam(value="page", required=false, defaultValue="1") int page) {

        int pageSize = 5;
        int totalCount = boardService.getTotalCount(search);
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);
        int start = (page - 1) * pageSize;

        List<BoardVo> boards = boardService.getBoardsPage(search, start, pageSize);

        model.addAttribute("posts", boards);
        model.addAttribute("search", search);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        return "board/list";
    }

    @GetMapping("/view/{no}")
    public String view(@PathVariable("no") Long no, Model model) {
        boardService.increaseViewCount(no);
        BoardVo post = boardService.getBoard(no);
        model.addAttribute("post", post);
        return "board/view";
    }

    @GetMapping("/write")
    public String writeForm() {
        return "board/write";
    }

    @PostMapping("/write")
	public String write(BoardVo vo,
		            HttpSession session,
		            @RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile) throws Exception {
		if (uploadFile != null && !uploadFile.isEmpty()) {
			String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
			Path savePath = Paths.get("C:/uploads/" + fileName);
			Files.copy(uploadFile.getInputStream(), savePath);
		
			vo.setFileName(fileName);
		}
		
		boardService.insert(vo);
		return "redirect:/board";
    }
    
 // 글 수정 폼 (기존 글 불러오기)
    @GetMapping("/update/{no}")
    public String updateForm(@PathVariable("no") Long no, Model model) {
        BoardVo post = boardService.getBoard(no);
        model.addAttribute("post", post);
        return "board/modify";  
    }

    // 글 수정 처리
    @PostMapping("/update/{no}")
    public String update(@PathVariable("no") Long no,
                         @ModelAttribute BoardVo vo,
                         @RequestParam(value="deleteFile", required=false) String deleteFile) throws Exception {

        vo.setNo(no); // 글 번호 세팅

        // 기존 파일 삭제
        if ("true".equals(deleteFile)) {
            vo.setFileName(null);
        }

        // 새 파일 업로드
        MultipartFile uploadFile = vo.getUploadFile();
        if (uploadFile != null && !uploadFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
            Path savePath = Paths.get("C:/uploads/" + fileName);
            Files.copy(uploadFile.getInputStream(), savePath);
            vo.setFileName(fileName);
        }

        boardService.updateBoard(vo);
        return "redirect:/board/view/" + no;
    	}
	}
