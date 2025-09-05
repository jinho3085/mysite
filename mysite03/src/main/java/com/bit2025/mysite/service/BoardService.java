package com.bit2025.mysite.service;

import com.bit2025.mysite.repository.BoardRepository;
import com.bit2025.mysite.vo.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 목록 페이징 처리
    public List<BoardVo> getPage(String search, int page, int pageSize) {
        if (search == null || search.trim().isEmpty()) {
            return boardRepository.findPage(null, page, pageSize);
        } else {
            return boardRepository.findPage(search.trim(), page, pageSize);
        }
    }

    // 게시글 총 개수
    public int getTotalCount(String search) {
        if (search == null || search.trim().isEmpty()) {
            return boardRepository.getTotalCount();
        } else {
            return boardRepository.getTotalCount(search.trim());
        }
    }

    // 게시글 추가
    public void addPost(BoardVo vo) {
        boardRepository.insert(vo);
    }

    // 게시글 상세 조회
    public BoardVo findById(Long id) {
        return boardRepository.findById(id);
    }

    // 조회수 증가
    public void increaseViewCount(Long id) {
        boardRepository.increaseViewCountId(id);
    }

    // 게시글 수정
    public void updatePost(BoardVo vo) {
        boardRepository.update(vo);
    }

    // 게시글 삭제 (작성자 확인)
    public void deletePost(Long id, String writer) {
        BoardVo post = boardRepository.findById(id);
        if (post != null && post.getWriter().equals(writer)) {
            boardRepository.delete(id);
        }
    }

    // 답글 추가
    public void addReply(Long parentId, BoardVo reply) {
        BoardVo parent = boardRepository.findById(parentId);
        if (parent != null) {
            reply.setDepth(parent.getDepth() + 1);
            reply.setParentId(parentId);
            boardRepository.insertReply(reply);
        }
    }
}
