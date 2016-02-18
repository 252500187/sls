package com.sls.user.entity;

public class UserQuestion {
    private int questionId;
    private int answerUserId;
    private int askUserId;
    private String askDate;
    private String askContent;
    private String answerContent;
    private int newAsk;
    private int newAnswer;

    private String userName;
    private String imgUrl;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerUserId() {
        return answerUserId;
    }

    public void setAnswerUserId(int answerUserId) {
        this.answerUserId = answerUserId;
    }

    public int getAskUserId() {
        return askUserId;
    }

    public void setAskUserId(int askUserId) {
        this.askUserId = askUserId;
    }

    public String getAskDate() {
        return askDate;
    }

    public void setAskDate(String askDate) {
        this.askDate = askDate;
    }

    public String getAskContent() {
        return askContent;
    }

    public void setAskContent(String askContent) {
        this.askContent = askContent;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public int getNewAsk() {
        return newAsk;
    }

    public void setNewAsk(int newAsk) {
        this.newAsk = newAsk;
    }

    public int getNewAnswer() {
        return newAnswer;
    }

    public void setNewAnswer(int newAnswer) {
        this.newAnswer = newAnswer;
    }

    public UserQuestion() {

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
}
