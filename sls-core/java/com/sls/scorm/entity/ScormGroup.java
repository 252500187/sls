package com.sls.scorm.entity;

public class ScormGroup {
    private int groupId;
    private int scormId;

    private String scormName;

    public ScormGroup() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getScormId() {
        return scormId;
    }

    public void setScormId(int scormId) {
        this.scormId = scormId;
    }

    public String getScormName() {
        return scormName;
    }

    public void setScormName(String scormName) {
        this.scormName = scormName;
    }
}
