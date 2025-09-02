package com.bit2025.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bit2025.mysite.vo.BoardVo;

public class BoardDao {
	
	public List<BoardVo>findAll() {
		List<BoardVo> list = new ArrayList<>();
	    String sql = "SELECT no, title, contents, writer, fileName, hit, reg_date, depth "
	               + "FROM board ORDER BY no DESC";

	    try (
	        Connection con = getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	    ) {
	        while (rs.next()) {
	            BoardVo vo = new BoardVo();
	            vo.setId(rs.getLong("no"));
	            vo.setTitle(rs.getString("title"));
	            vo.setContent(rs.getString("contents"));
	            vo.setWriter(rs.getString("writer"));
	            vo.setFileName(rs.getString("fileName"));
	            vo.setViewCount(rs.getInt("hit"));
	            vo.setCreatedAt(rs.getTimestamp("reg_date"));
	            vo.setDepth(rs.getInt("depth"));

	            list.add(vo);
	        }
	    } catch (SQLException e) {
	        System.out.println("findAll() 오류: " + e);
	    }

	    return list;
	}
	
	public List<BoardVo> findPage(String search, int page, int pageSize) {
	    if (page < 1) page = 1;
	    int offset = (page - 1) * pageSize;
	    offset = Math.max(0, offset);

	    boolean hasSearch = search != null && !search.trim().isEmpty();
	    String sql;
	    if (hasSearch) {
	        sql = "SELECT * FROM board WHERE title LIKE ? OR contents LIKE ? OR writer LIKE ? "
	            + "ORDER BY no DESC LIMIT ? OFFSET ?";
	    } else {
	        sql = "SELECT * FROM board ORDER BY no DESC LIMIT ? OFFSET ?";
	    }

	    List<BoardVo> list = new ArrayList<>();

	    try (Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        if (hasSearch) {
	            String like = "%" + search + "%";
	            pstmt.setString(1, like);
	            pstmt.setString(2, like);
	            pstmt.setString(3, like);
	            pstmt.setInt(4, pageSize);
	            pstmt.setInt(5, offset);
	        } else {
	            pstmt.setInt(1, pageSize);
	            pstmt.setInt(2, offset);
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                BoardVo vo = new BoardVo();
	                vo.setId(rs.getLong("no"));
	                vo.setTitle(rs.getString("title"));
	                vo.setContent(rs.getString("contents"));
	                vo.setWriter(rs.getString("writer"));
	                vo.setFileName(rs.getString("fileName"));
	                vo.setViewCount(rs.getInt("hit"));
	                vo.setCreatedAt(rs.getTimestamp("reg_date"));
	                vo.setDepth(rs.getInt("depth"));
	                list.add(vo);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("findPage() 오류: " + e);
	    }

	    return list;
	}

	public int getTotalCount(String search) {
		boolean hasSearch = search != null && !search.trim().isEmpty();
	    String sql;
	    if (hasSearch) {
	        sql = "SELECT COUNT(*) FROM board WHERE title LIKE ? OR contents LIKE ? OR writer LIKE ?";
	    } else {
	        sql = "SELECT COUNT(*) FROM board";
	    }

	    try (Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        if (hasSearch) {
	            String like = "%" + search + "%";
	            pstmt.setString(1, like);
	            pstmt.setString(2, like);
	            pstmt.setString(3, like);
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("getTotalCount() 오류: " + e);
	    }

	    return 0;
	}
	
	public List<BoardVo>findAll(String serch) {
		List<BoardVo> list = new ArrayList<>();
		
		String sql =
		        "SELECT no, title, contents, writer, fileName, hit, reg_date, depth " +
		        "FROM board " +
		        "WHERE title LIKE ? OR contents LIKE ? OR writer LIKE ? " +
		        "ORDER BY no DESC";

		    try (Connection con = getConnection();
		         PreparedStatement pstmt = con.prepareStatement(sql)) {

		        String like = "%" + serch + "%";
		        pstmt.setString(1, like);
		        pstmt.setString(2, like);
		        pstmt.setString(3, like);

		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                BoardVo vo = new BoardVo();
		                vo.setId(rs.getLong("no"));
		                vo.setTitle(rs.getString("title"));
		                vo.setContent(rs.getString("contents"));
		                vo.setWriter(rs.getString("writer"));
		                vo.setFileName(rs.getString("fileName"));
		                vo.setViewCount(rs.getInt("hit"));
		                vo.setCreatedAt(rs.getTimestamp("reg_date"));
		                vo.setDepth(rs.getInt("depth"));
		                list.add(vo);
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println("findAll(serch) 오류: " + e);
		    }

		    return list;
		}
	
	public BoardVo findById(Long id) {
		BoardVo vo = null;
	    String sql = "SELECT no, title, contents, writer, fileName, hit, reg_date, depth from board where no=?";

	    try (Connection con = getConnection();
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            pstmt.setLong(1, id); 
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) { 
	                    vo = new BoardVo();
	                    vo.setId(rs.getLong("no"));
	                    vo.setTitle(rs.getString("title"));
	                    vo.setContent(rs.getString("contents"));
	                    vo.setWriter(rs.getString("writer"));
	                    vo.setFileName(rs.getString("fileName"));
	                    vo.setViewCount(rs.getInt("hit"));
	                    vo.setCreatedAt(rs.getTimestamp("reg_date"));
	                    vo.setDepth(rs.getInt("depth"));
	                }
	            }
	    } catch (SQLException e) {
	        System.out.println("findById() 오류: " + e);
	    }

	    return vo;
	}
	
	public int getTotalCount() {
		int total = 0;
	    String sql = "SELECT COUNT(*) FROM board";

	    try (Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        if (rs.next()) {
	            total = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        System.out.println("getTotalCount() 오류: " + e);
	    }
	    return total;
	}

	public List<BoardVo> findByPage(int start, int pageSize) {
		List<BoardVo> list = new ArrayList<>();
		String sql = "SELECT * FROM board ORDER BY no DESC LIMIT ? OFFSET ?";

	    try (Connection con = getConnection();
	         PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setInt(1, pageSize);       // 한 페이지 글 개수
	        pstmt.setInt(2, start);          // 시작 인덱스

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                BoardVo vo = new BoardVo();
	                vo.setId(rs.getLong("no"));
	                vo.setTitle(rs.getString("title"));
	                vo.setContent(rs.getString("contents"));
	                vo.setWriter(rs.getString("writer"));
	                vo.setFileName(rs.getString("fileName"));
	                vo.setViewCount(rs.getInt("hit"));
	                vo.setCreatedAt(rs.getTimestamp("reg_date"));
	                vo.setDepth(rs.getInt("depth"));
	                list.add(vo);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("findByPage() 오류: " + e);
	    }

	    return list;
	}
	
	 public int insert(BoardVo vo) {
	        int result = 0;

	        String sql = "INSERT INTO board (title, contents, writer, fileName, hit, reg_date, depth) "
	        			 + "VALUES (?, ?, ?, ?, 0, NOW(), 0)";

	        try (Connection con = getConnection();
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            pstmt.setString(1, vo.getTitle());
	            pstmt.setString(2, vo.getContent());
	            pstmt.setString(3, vo.getWriter());
	            pstmt.setString(4, vo.getFileName());
	            result = pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println("insert() 오류: " + e);
	        }

	        return result;
	    }
	 
	 public int insertReply(BoardVo vo) {
		    int result = 0;
		    String sql = "INSERT INTO board (title, contents, writer, fileName, hit, reg_date, depth, parent_id) "
		               + "VALUES (?, ?, ?, ?, 0, NOW(), ?, ?)";

		    try (Connection conn = getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, vo.getTitle());
		        pstmt.setString(2, vo.getContent());
		        pstmt.setString(3, vo.getWriter());
		        pstmt.setString(4, vo.getFileName());
		        pstmt.setInt(5, vo.getDepth());       
		        pstmt.setObject(6, vo.getParentId()); 
		        result = pstmt.executeUpdate();
		    } catch (SQLException e) {
		        System.out.println("insertReply() 오류: " + e);
		    }
		    return result;
		}
	 
	 public int delete(Long id) {
		 int result = 0;
		 String sql = "delete from board where no=?";
		 
		 try (Connection con = getConnection();
	          PreparedStatement pstmt = con.prepareStatement(sql)) {

	            pstmt.setLong(1, id);
	            result = pstmt.executeUpdate();

	        } catch (SQLException e) {
	            System.out.println("delete() 오류: " + e);
	        }

	        return result;
		}
	 
	 public int increaseViewCountId(Long id) {
		int result =0;
		String sql = "UPDATE board SET hit = hit + 1 where no=?";
		
		 try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setLong(1, id);
	            result = pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println("increaseViewCount() 오류: " + e);
	        }
		
		 return result;
		}
	 
	 public int update(BoardVo vo) {
		 int result =0;
		 String sql = "UPDATE board SET title = ?, contents = ?, fileName = ? where no = ?";
		 
		 try (Connection con = getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
		        pstmt.setString(1, vo.getTitle());
		        pstmt.setString(2, vo.getContent());
		        pstmt.setString(3, vo.getFileName());
		        pstmt.setLong(4, vo.getId());
		        result = pstmt.executeUpdate();
		    } catch (SQLException e) {
		        System.out.println("update() 오류: " + e);
		    }
		 
		 return result;
		}
	 
	private Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url  = "jdbc:mariadb://192.168.0.178:3306/webdb";
			con =  DriverManager.getConnection (url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}
		
		return con;		
	}
}
