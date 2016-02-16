package com.sls.scorm.entity;

public class ScormRecord {

    private int recordId;
    private int userId;
    private int scormId;
    private int scoId;
    private String studyTime;

    private String title;

    public ScormRecord(int userId, int scormId, int scoId, String studyTime) {
        this.userId = userId;
        this.scormId = scormId;
        this.scoId = scoId;
        this.studyTime = studyTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
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

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }

    public ScormRecord() {
    }

    public int getScoId() {
        return scoId;
    }

    public void setScoId(int scoId) {
        this.scoId = scoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
