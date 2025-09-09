package com.bit2025.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.repository.BoardRepository;
import com.bit2025.mysite.vo.BoardVo;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<BoardVo> getAllBoards() {
        return boardRepository.findAll();
    }

    public BoardVo getBoard(Long no) {
        return boardRepository.findById(no);
    }

    public void addBoard(BoardVo vo) {
        boardRepository.insert(vo);
    }

    public void addReply(BoardVo vo) {
        boardRepository.insertReply(vo);
    }

    public void updateBoard(BoardVo vo) {
        boardRepository.update(vo);
    }

    public void deleteBoard(Long no) {
        boardRepository.delete(no);
    }

    public void increaseViewCount(Long no) {
        boardRepository.increaseViewCount(no);
    }

    public int getTotalCount(String search) {
        return boardRepository.getTotalCount(search);
    }

    public List<BoardVo> getBoardsPage(String search, int start, int pageSize) {
        return boardRepository.findPage(search, start, pageSize);
    }

	public void insert(BoardVo vo) {
		boardRepository.insert(vo);
	}
}
