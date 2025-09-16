package com.bit2025.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit2025.mysite.vo.BoardVo;

@Repository
public class BoardRepository {

    @Autowired
    private SqlSession sqlSession;

    public int insert(BoardVo boardVo) {
        return sqlSession.insert("board.insert", boardVo);
    }

    public List<BoardVo> findAllByPageAndKeword(String keyword, Integer page, Integer size) {
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", keyword);
        map.put("startIndex", (page - 1) * size);
        map.put("size", size);

        return sqlSession.selectList("board.findAllByPageAndKeword", map);
    }

    public int update(BoardVo boardVo) {
        return sqlSession.update("board.update", boardVo);
    }

    public int delete(Long id, String writer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("writer", writer);

        return sqlSession.delete("board.delete", map);
    }

    public BoardVo findById(Long id) {
        return sqlSession.selectOne("board.findById", id);
    }

    public BoardVo findByIdAndWriter(Long id, String writer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("writer", writer);
        return sqlSession.selectOne("board.findByIdAndWriter", map);
    }

    public int updateHit(Long id) {
        return sqlSession.update("board.updateHit", id);
    }

    public int getTotalCount(String keyword) {
        return sqlSession.selectOne("board.totalCount", keyword);
    }
}
