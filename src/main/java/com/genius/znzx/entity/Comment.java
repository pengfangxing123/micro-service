package com.genius.znzx.entity;

import java.util.Date;
import java.util.List;

public class Comment {
	private int id;

    // 评论内容
    private String content;

    // 回复ID[默认为0,表示普通评论,回复评论时：replyId值是被评论数据的ID]
    private int is_sub;

    // 用户ID
    private String userId;

    // 被评论的对象ID[文章ID,新闻ID...]
    private int parent_id;

    // 是否已经删除[0：正常状态,1：已删除]
    private int isshow;

    // 评论时间
    private Date createTime;

    // 回复评论集合[辅助属性]
    private List<Comment> replyList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIs_sub() {
		return is_sub;
	}

	public void setIs_sub(int is_sub) {
		this.is_sub = is_sub;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getIsshow() {
		return isshow;
	}

	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Comment> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Comment> replyList) {
		this.replyList = replyList;
	}
	
}
