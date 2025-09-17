package com.bit2025.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handler(
		HttpServletRequest request,
		HttpServletResponse response,
		Exception e) throws Exception {
		
		//1. 로깅 (logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		//2. 요청 구분
		// json 요청: request header accept: application/json (o)
		// html 요청: request header accept: application/json (x)
		String accept = request.getHeader("accept");
		
		//3. json 응답
		if(accept.matches(".*application/json.*")) {
			Map map = Map.of("result", "fail", "message", errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(map);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
			return;
		}
		
		//4. HTML 응답: 사과 페이지 (종료)
		request.setAttribute("errors", errors);
		request
			.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp")
			.forward(request, response);
	}
}