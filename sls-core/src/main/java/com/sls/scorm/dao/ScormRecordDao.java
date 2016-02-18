package com.sls.scorm.dao;

import com.sls.scorm.entity.ScormRecord;

import java.util.List;

public interface ScormRecordDao {

    List<ScormRecord> getRecordListByUserIdAndScormId(int userId, int scormId);

    void addStudyRecord(ScormRecord scormRecord);
}
