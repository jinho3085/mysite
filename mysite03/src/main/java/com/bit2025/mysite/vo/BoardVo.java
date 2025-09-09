package com.bit2025.mysite.vo;

import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class BoardVo {
    private Long no;
    private String title;
    private String contents;
    private String writer;
    private String fileName;
    private int viewCount;
    private int depth;
    private Date createdAt;
    private Long parentId;
    private MultipartFile uploadFile; // form에서 넘어오는 파일

    // getter / setter
    public Long getNo() { return no; }
    public void setNo(Long no) { this.no = no; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }
    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public int getViewCount() { return viewCount; }
    public void setViewCount(int viewCount) { this.viewCount = viewCount; }
    public int getDepth() { return depth; }
    public void setDepth(int depth) { this.depth = depth; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public MultipartFile getUploadFile() { return uploadFile; }
    public void setUploadFile(MultipartFile uploadFile) { this.uploadFile = uploadFile; }

    @Override
    public String toString() {
        return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents +
               ", writer=" + writer + ", fileName=" + fileName + "]";
    }
}
