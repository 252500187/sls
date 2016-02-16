package com.sls.user.entity;

public class UserAttention {
    private int userId;
    private int userAttentionId;
    private int newMessage;
    private int state;

    private String userName;
    private String imgUrl;

    public UserAttention() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserAttentionId() {
        return userAttentionId;
    }

    public void setUserAttentionId(int userAttentionId) {
        this.userAttentionId = userAttentionId;
    }

    public int getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(int newMessage) {
        this.newMessage = newMessage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
