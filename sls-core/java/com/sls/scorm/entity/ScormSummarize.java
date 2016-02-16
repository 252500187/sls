package com.sls.scorm.entity;

public class ScormSummarize {
    private int userId;
    private int scormId;
    private int score;
    private String registerDate;
    private String discuss;
    private String discussDate;
    private String grade;
    private String completeDate;
    private String totalTime;
    private String lastVisitTime;

    private String userName;
    private String imgUrl;
    private String scormName;
    private String loginName;

    public ScormSummarize() {
        score = 0;
        discuss = "";
        grade = "";
        discussDate = "";
        completeDate = "";
        totalTime = "";
        lastVisitTime = "";
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScormId() {
        return scormId;
    }

    public void setScormId(int scormId) {
        this.scormId = scormId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public String getDiscussDate() {
        return discussDate;
    }

    public void setDiscussDate(String discussDate) {
        this.discussDate = discussDate;
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

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(String lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getScormName() {
        return scormName;
    }

    public void setScormName(String scormName) {
        this.scormName = scormName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
