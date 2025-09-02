package com.bit2025.mysite.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

import com.bit2025.mysite.dao.BoardDao;
import com.bit2025.mysite.vo.BoardVo;
import com.bit2025.mysite.vo.UserVo;

@MultipartConfig(
		fileSizeThreshold = 1024 * 1024, // 1MB
	    maxFileSize = 1024 * 1024 * 10,  // 10MB
	    maxRequestSize = 1024 * 1024 * 50 // 50MB
)

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		
		BoardDao dao = new BoardDao();
		
		if("writeform".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(request, response);
			
		} else if ("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");
			
			Part filePart = request.getPart("uploadFile");
	        String fileName = null;
	        if (filePart != null && filePart.getSize() > 0) {
	            fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
	            String uploadPath = request.getServletContext().getRealPath("/uploads");
	            
	            java.io.File uploadDir = new java.io.File(uploadPath);
	            if (!uploadDir.exists()) uploadDir.mkdirs();

	            filePart.write(uploadPath + java.io.File.separator + fileName);
	        }
			
			if (authUser != null) {
				BoardVo vo= new BoardVo();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setWriter(authUser.getName());
				vo.setFileName(fileName);
				
				dao.insert(vo);
			}
			
		
			response.sendRedirect(request.getContextPath() + "/board");
			
		} else if ("delete".equals(action)){
			String idStr = request.getParameter("id");
			UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");
			
			if(idStr != null && !idStr.isEmpty() && authUser != null) {
				Long id = Long.valueOf(idStr);
				BoardVo post = dao.findById(id);
				
				if(post != null && authUser.getName().equals(post.getWriter())) {
					dao.delete(id);
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/board");
			
		} else if ("replyform".equals(action)){
			Long parentId = Long.valueOf(request.getParameter("id"));
		    BoardVo parentPost = new BoardDao().findById(parentId);
		    request.setAttribute("parentPost", parentPost);
		    request.getRequestDispatcher("/WEB-INF/views/board/reply.jsp").forward(request, response);
		
		} else if ("reply".equals(action)){
			UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");
		    if(authUser != null) {
		        Long parentId = Long.valueOf(request.getParameter("parentId"));
		        BoardVo parentPost = new BoardDao().findById(parentId);

		        BoardVo vo = new BoardVo();
		        vo.setTitle(request.getParameter("title"));
		        vo.setContent(request.getParameter("content"));
		        vo.setWriter(authUser.getName());
		        vo.setDepth(parentPost.getDepth() + 1);
		        vo.setParentId(parentId);

		        new BoardDao().insertReply(vo);
		    }
		    response.sendRedirect(request.getContextPath() + "/board");
		    
		} else if ("view".equals(action)){
			String idStr = request.getParameter("id");
			if(idStr != null && !idStr.isEmpty()) {
				Long id = Long.valueOf(idStr);
				dao.increaseViewCountId(id);
				BoardVo post = dao.findById(id);
				
				if(post != null) {
					request.setAttribute("post", post);
					request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
					
					return;
				}
			}
			
			response.sendRedirect(request.getContextPath() + "/board");
			
		} else if ("list".equals(action)) {
		    String serch = request.getParameter("serch"); 
		    List<BoardVo> posts;

		    if (serch == null || serch.trim().isEmpty()) {
		        posts = dao.findAll();           
		    } else {
		        posts = dao.findAll(serch.trim()); 
		    }

		    request.setAttribute("posts", posts);
		    request.setAttribute("serch", serch); 
		    request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
			
			
		} else if ("updateform".equals(action)){
			String idStr = request.getParameter("id");
			if(idStr != null && !idStr.isEmpty()) {
				Long id = Long.valueOf(idStr);
				BoardVo post = dao.findById(id);
				UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");
				
				if(post != null && authUser !=null && authUser.getName().equals(post.getWriter())) {
					request.setAttribute("post", post);
					request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp").forward(request, response);
					return;
				}
			}
			response.sendRedirect(request.getContextPath() + "/board");
			
		} else if ("update".equals(action)){
			String idStr = request.getParameter("id");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			UserVo authUser = (UserVo) request.getSession().getAttribute("authUser");
			
			if(idStr != null && !idStr.isEmpty() && authUser !=null) {
				Long id = Long.valueOf(idStr);
				BoardVo post = dao.findById(id);
				
				if(post != null && authUser.getName().equals(post.getWriter())) {
					post.setTitle(title);
					post.setContent(content);
					
					Part filePart = request.getPart("uploadFile");
		            String deleteFile = request.getParameter("deleteFile");

		            String uploadPath = request.getServletContext().getRealPath("/uploads");

		            // 기존 파일 삭제
		            if ("true".equals(deleteFile) && post.getFileName() != null) {
		                java.io.File oldFile = new java.io.File(uploadPath, post.getFileName());
		                if (oldFile.exists()) oldFile.delete();
		                post.setFileName(null);
		            }

		            // 새 파일 업로드
		            if (filePart != null && filePart.getSize() > 0) {
		                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
		                java.io.File uploadDir = new java.io.File(uploadPath);
		                if (!uploadDir.exists()) uploadDir.mkdirs();

		                filePart.write(uploadPath + java.io.File.separator + fileName);
		                post.setFileName(fileName);

		                // 기존 파일이 남아있으면 삭제
		                if (post.getFileName() != null && !"true".equals(deleteFile)) {
		                    java.io.File oldFile = new java.io.File(uploadPath, post.getFileName());
		                    if (oldFile.exists()) oldFile.delete();
		                }
		            }
		            
						dao.update(post);
				}
			}
			response.sendRedirect(request.getContextPath() + "/board");
			
		} else {
			List<BoardVo> posts = dao.findAll();
			request.setAttribute("posts", posts);
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
