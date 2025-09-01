package com.bit2025.mysite.vo;

import java.util.Date;

public class BoardVo {
	private Long id;
	private String title;
	private String content;
	private String writer;
	private int viewCount;
	private Date createdAt;
	private int depth;
	private Long parentId;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Override
	public String toString() {
		return "BoardVo [id=" + id + ", title=" + title + ", content=" + content + ", writer=" + writer + ", viewCount="
				+ viewCount + ", createdAt=" + createdAt + ", depth=" + depth + ", parentId=" + parentId + "]";
	}
	
}
