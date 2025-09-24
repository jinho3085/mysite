package com.bit2025.mysite.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2025.mysite.service.BoardService;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;
    
    // 게시판 목록
    @RequestMapping("")
    public String list(
        @RequestParam(value="p", required=false, defaultValue="1") Integer page,
        @RequestParam(value="kwd", required=false, defaultValue="") String keyword,
        Model model) {
        
        Map<String, Object> map = boardService.getContentsList(page, keyword);
        model.addAttribute("map", map);
        
        return "board/list"; 
    }

    // 게시글 보기
    @RequestMapping(value="/view/{no}")
    public String view(
        @PathVariable("no") Long no, Model model) {
    	BoardVo boardVo = boardService.getContents(no);
        model.addAttribute("boardVo", boardVo);
        return "board/view"; 
    }
    
    // 글쓰기 화면
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write() {
		return "board/write";
	}
    
    // 글쓰기 처리
    @RequestMapping(value="/write", method=RequestMethod.POST)    
    public String write(
    	Authentication authentication,
    	@ModelAttribute BoardVo boardVo,
        @RequestParam(value="p", required=true, defaultValue="1") Integer page,
        @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        
    	boardVo.setWriter(((UserVo)authentication.getPrincipal()).getName());
    	
    	boardVo.setUserId(((UserVo)authentication.getPrincipal()).getId());
    	
    	if (boardVo.getDepth() == null) { // 새 글이면 depth 0
    	    boardVo.setDepth(0);
    	    boardVo.setParentId(null); 
    	}
        boardService.addContents(boardVo);

        return "redirect:/board?p=" + page + "&kwd=" + keyword;
    }
    
    // 글 수정 화면
    @RequestMapping("/modify/{no}")	
	public String modify(
		Authentication authentication, @PathVariable("no") Long no, Model model) {
    	BoardVo boardVo = boardService.getContents(no);
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
    
    // 글 수정 처리
    @RequestMapping(value="/modify", method=RequestMethod.POST)    
    public String modify(
    	Authentication authentication,
    	@ModelAttribute BoardVo boardVo,
        @RequestParam(value="p", required=true, defaultValue="1") Integer page,
        @RequestParam(value="kwd", required=true, defaultValue="") String keyword) {
        
    	boardVo.setWriter(((UserVo)authentication.getPrincipal()).getName()); 
        boardService.modifyContents(boardVo);
        
        return "redirect:/board/view/" + boardVo.getNo() + 
                "?p=" + page + 
                "&kwd=" + keyword;
    }
    
    // 글 삭제
    @RequestMapping("/delete/{no}")
    public String delete(
    	Authentication authentication, 
        @PathVariable("no") Long boardNo,
        @RequestParam(value="p", required=false, defaultValue="1") Integer page,
        @RequestParam(value="kwd", required=false, defaultValue="") String keyword) {
        
    	boardService.deleteContents(boardNo, ((UserVo)authentication.getPrincipal()).getName()); 
        return "redirect:/board?p=" + page + "&kwd=" + keyword;
    }
    
    // 답글 작성 화면
    @RequestMapping(value="/reply/{no}")	
	public String reply(@PathVariable("no") Long no, Model model) {
		BoardVo parent = boardService.getContents(no);
		
		BoardVo reply = new BoardVo();
		reply.setParentId(parent.getNo());       
        reply.setDepth(parent.getDepth() + 1);   
		
		model.addAttribute("boardVo", reply);
		return "board/reply";
	}
    
}
