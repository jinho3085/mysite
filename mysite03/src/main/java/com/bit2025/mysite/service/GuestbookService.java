package com.bit2025.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit2025.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return null;
	}
	
	public void addMessage(GuestbookVo vo) {
		
	}
	
	public void deleteMessage(Long id, String password) {
		
	}
	
	
}
