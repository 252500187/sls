package com.sls.scorm.entity;

import com.sls.util.DictConstant;

public class ScoInfo {
    private int scoId;
    private String coreStudentId;
    private String coreStudentName;
    private String coreLessonLocation;
    private String coreCredit;
    private String coreLessonStatus;
    private String coreEntry;
    private String coreScoreRaw;
    private String coreTotalTime;
    private String coreExit;
    private String coreSessionTime;
    private String coreLessonMode;
    private String suspendData;
    private String launchData;

    private String passRaw;

    public ScoInfo() {
        coreStudentId = "";
        coreStudentName = "";
        coreLessonLocation = "";
        coreCredit = "";
        coreLessonStatus = DictConstant.LESSON_STATUS_NOT_ATTEMPTED;
        coreEntry = DictConstant.ENTRY_INI;
        coreScoreRaw = "";
        coreTotalTime = "0000:00:00.00";
        coreExit = "";
        coreSessionTime = "";
        coreLessonMode = DictConstant.LESSON_MODE_NORMAL;
        suspendData = "";
        launchData = "";
        passRaw = "";
    }

    public String getCoreStudentId() {
        return coreStudentId;
    }

    public void setCoreStudentId(String coreStudentId) {
        this.coreStudentId = coreStudentId;
    }

    public String getCoreStudentName() {
        return coreStudentName;
    }

    public void setCoreStudentName(String coreStudentName) {
        this.coreStudentName = coreStudentName;
    }

    public String getCoreLessonLocation() {
        return coreLessonLocation;
    }

    public void setCoreLessonLocation(String coreLessonLocation) {
        this.coreLessonLocation = coreLessonLocation;
    }

    public String getCoreCredit() {
        return coreCredit;
    }

    public void setCoreCredit(String coreCredit) {
        this.coreCredit = coreCredit;
    }

    public String getCoreLessonStatus() {
        return coreLessonStatus;
    }

    public void setCoreLessonStatus(String coreLessonStatus) {
        this.coreLessonStatus = coreLessonStatus;
    }

    public String getCoreEntry() {
        return coreEntry;
    }

    public void setCoreEntry(String coreEntry) {
        this.coreEntry = coreEntry;
    }

    public String getCoreScoreRaw() {
        return coreScoreRaw;
    }

    public void setCoreScoreRaw(String coreScoreRaw) {
        this.coreScoreRaw = coreScoreRaw;
    }

    public String getCoreTotalTime() {
        return coreTotalTime;
    }

    public void setCoreTotalTime(String coreTotalTime) {
        this.coreTotalTime = coreTotalTime;
    }

    public String getCoreExit() {
        return coreExit;
    }

    public void setCoreExit(String coreExit) {
        this.coreExit = coreExit;
    }

    public String getCoreSessionTime() {
        return coreSessionTime;
    }

    public void setCoreSessionTime(String coreSessionTime) {
        this.coreSessionTime = coreSessionTime;
    }

    public String getSuspendData() {
        return suspendData;
    }

    public void setSuspendData(String suspendData) {
        this.suspendData = suspendData;
    }

    public int getScoId() {
        return scoId;
    }

    public void setScoId(int scoId) {
        this.scoId = scoId;
    }

    public String getLaunchData() {
        return launchData;
    }

    public void setLaunchData(String launchData) {
        this.launchData = launchData;
    }

    public String getPassRaw() {
        return passRaw;
    }

    public void setPassRaw(String passRaw) {
        this.passRaw = passRaw;
    }

    public String getCoreLessonMode() {
        return coreLessonMode;
    }

    public void setCoreLessonMode(String coreLessonMode) {
        this.coreLessonMode = coreLessonMode;
    }
}
