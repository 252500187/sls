package com.sls.scorm.entity;

public class PublicScorm {
    private int publicId;
    private int scormId;
    private String startTime;
    private String endTime;
    private String description;

    private String scormName;

    public PublicScorm() {
    }

    public int getPublicId() {
        return publicId;
    }

    public void setPublicId(int publicId) {
        this.publicId = publicId;
    }

    public int getScormId() {
        return scormId;
    }

    public void setScormId(int scormId) {
        this.scormId = scormId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScormName() {
        return scormName;
    }

    public void setScormName(String scormName) {
        this.scormName = scormName;
    }
}
