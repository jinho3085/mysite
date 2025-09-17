package com.bit2025.mysite.vo;

public class BoardVo {
    private Long no;
    private String title;
    private String contents;
    private String writer;
    private Integer hit = 0;
    private String regDate;
    private Integer depth = 0;
    private Long parentId;
    private Long userId;
    private String fileName;

    public Long getNo() { return no; }
    public void setNo(Long no) { this.no = no; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public Integer getHit() { return hit; }
    public void setHit(Integer hit) { this.hit = hit; }

    public String getRegDate() { return regDate; }
    public void setRegDate(String regDate) { this.regDate = regDate; }

    public Integer getDepth() { return depth; }
    public void setDepth(Integer depth) { this.depth = depth; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    @Override
    public String toString() {
        return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", writer=" + writer +
               ", hit=" + hit + ", regDate=" + regDate + ", depth=" + depth + ", parentId=" + parentId +
               ", userId=" + userId + ", fileName=" + fileName + "]";
    }
}
