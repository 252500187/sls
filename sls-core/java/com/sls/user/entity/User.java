package com.sls.user.entity;

import com.sls.util.DictConstant;

public class User {

    private int userId;
    private String loginName;
    private String password;
    private String showInUse;
    private int inUse;

    private String userName;
    private String email;
    private int score;
    private String levelName;
    private int sex;
    private String showSex;
    private String imgUrl;
    private int upLoadScormNum;

    private int finalScore;

    private String registerDate;

    private int roleId;
    private String roleName;
    private String authority;

    public User() {
        this.sex = DictConstant.VOID_SEX;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }

    public String getShowInUse() {
        return showInUse;
    }

    public void setShowInUse(String showInUse) {
        this.showInUse = showInUse;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getShowSex() {
        return showSex;
    }

    public void setShowSex(String showSex) {
        this.showSex = showSex;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getUpLoadScormNum() {
        return upLoadScormNum;
    }

    public void setUpLoadScormNum(int upLoadScormNum) {
        this.upLoadScormNum = upLoadScormNum;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }
}
