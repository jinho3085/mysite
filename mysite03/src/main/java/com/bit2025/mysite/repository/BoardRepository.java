package com.bit2025.mysite.repository;

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

    public List<BoardVo> findAll() {
        return sqlSession.selectList("board.findAll");
    }

    public BoardVo findById(Long no) {
        return sqlSession.selectOne("board.findById", no);
    }

    public int insert(BoardVo vo) {
        return sqlSession.insert("board.insert", vo);
    }

    public int insertReply(BoardVo vo) {
        return sqlSession.insert("board.insertReply", vo);
    }

    public int update(BoardVo vo) {
        return sqlSession.update("board.update", vo);
    }

    public int delete(Long no) {
        return sqlSession.delete("board.delete", no);
    }

    public int increaseViewCount(Long no) {
        return sqlSession.update("board.increaseViewCount", no);
    }

    public int getTotalCount(String search) {
        return sqlSession.selectOne("board.getTotalCount", search);
    }

    public List<BoardVo> findPage(String search, int start, int pageSize) {
    	if(search == null) search = "";
        return sqlSession.selectList("board.findPage", 
                Map.of("search", search, "start", start, "pageSize", pageSize));
    }
}
