package com.bit2025.mysite.repository;

import com.bit2025.mysite.vo.BoardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BoardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // BoardVo 매핑
    private RowMapper<BoardVo> rowMapper = new RowMapper<BoardVo>() {
        @Override
        public BoardVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            BoardVo vo = new BoardVo();
            vo.setNo(rs.getLong("no"));
            vo.setTitle(rs.getString("title"));
            vo.setContents(rs.getString("contents"));
            vo.setWriter(rs.getString("writer"));
            vo.setFileName(rs.getString("fileName"));
            vo.setViewCount(rs.getInt("hit"));
            vo.setCreatedAt(rs.getTimestamp("reg_date"));
            vo.setDepth(rs.getInt("depth"));
            vo.setParentId(rs.getObject("parent_id") != null ? rs.getLong("parent_id") : null);
            return vo;
        }
    };

    // 전체 조회
    public List<BoardVo> findAll() {
        String sql = "SELECT * FROM board ORDER BY no DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    // 페이징 + 검색
    public List<BoardVo> findPage(String search, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        if (search == null || search.trim().isEmpty()) {
            String sql = "SELECT * FROM board ORDER BY no DESC LIMIT ? OFFSET ?";
            return jdbcTemplate.query(sql, rowMapper, pageSize, offset);
        } else {
            String like = "%" + search.trim() + "%";
            String sql = "SELECT * FROM board WHERE title LIKE ? OR contents LIKE ? OR writer LIKE ? " +
                         "ORDER BY no DESC LIMIT ? OFFSET ?";
            return jdbcTemplate.query(sql, rowMapper, like, like, like, pageSize, offset);
        }
    }

    // 총 개수 (검색 포함)
    public int getTotalCount(String search) {
        if (search == null || search.trim().isEmpty()) {
            return getTotalCount(); // 검색 없으면 매개변수 없는 버전 호출
        }
        String like = "%" + search.trim() + "%";
        String sql = "SELECT COUNT(*) FROM board WHERE title LIKE ? OR contents LIKE ? OR writer LIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, like, like, like);
    }

    // 총 개수 (검색 없음)
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM board";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 상세 조회
    public BoardVo findById(Long id) {
        String sql = "SELECT * FROM board WHERE no=?";
        List<BoardVo> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // 조회수 증가
    public void increaseViewCountId(Long id) {
        String sql = "UPDATE board SET hit = hit + 1 WHERE no=?";
        jdbcTemplate.update(sql, id);
    }

    // 게시글 추가
    public void insert(BoardVo vo) {
        String sql = "INSERT INTO board (title, contents, writer, fileName, hit, reg_date, depth) " +
                     "VALUES (?, ?, ?, ?, 0, NOW(), 0)";
        jdbcTemplate.update(sql, vo.getTitle(), vo.getContents(), vo.getWriter(), vo.getFileName());
    }

    // 답글 추가
    public void insertReply(BoardVo vo) {
        String sql = "INSERT INTO board (title, contents, writer, fileName, hit, reg_date, depth, parent_id) " +
                     "VALUES (?, ?, ?, ?, 0, NOW(), ?, ?)";
        jdbcTemplate.update(sql, vo.getTitle(), vo.getContents(), vo.getWriter(), vo.getFileName(),
                            vo.getDepth(), vo.getParentId());
    }

    // 수정
    public void update(BoardVo vo) {
        String sql = "UPDATE board SET title=?, contents=?, fileName=? WHERE no=?";
        int updated = jdbcTemplate.update(sql, vo.getTitle(), vo.getContents(), vo.getFileName(), vo.getNo());
        System.out.println("update 실행됨: no=" + vo.getNo() + ", updated rows=" + updated);
    }

    // 삭제
    public void delete(Long no) {
        String sql = "DELETE FROM board WHERE no=?";
        jdbcTemplate.update(sql, no);
    }
}
