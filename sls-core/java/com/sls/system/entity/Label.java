package com.sls.system.entity;

public class Label {
    private int labelId;
    private String labelName;
    private int userId;
    private int scormId;
    private String userName;
    private String scormName;
    private int number;

    public Label() {}

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScormName() {
        return scormName;
    }

    public void setScormName(String scormName) {
        this.scormName = scormName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
