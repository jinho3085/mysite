package com.bit2025.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bit2025.mysite.repository.GuestbookRepository;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GuestbookRepository.class);
	
	@ExceptionHandler(Exception.class)
	public String handler(Model model, Exception e) {
		
		//1. 로그인
		StringWriter errors = new StringWriter(); 
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		//2. 사과 페이지(3.종료)
		model.addAttribute("errors", errors);
		return "errors/exception";
		
	}
}
