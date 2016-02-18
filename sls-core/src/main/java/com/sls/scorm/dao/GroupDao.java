package com.sls.scorm.dao;

import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.ScormGroup;

import java.util.List;

public interface GroupDao {
    List<Scorm> getGroupScormsByScormId(int scormId);

    List<ScormGroup> getUpScormGroupsByUserId(int userId);

    void addDefaultScormGroup(int scormId);

    void addScormGroup(int scormId, int groupId);

    List<Scorm> getGroupScormsByGroupId(int groupId);

    double getGroupScoreByGroupId(int groupId);

    List<Scorm> findGroupScormsByNum(int num);

    Boolean notHaveInUseScormCheckByGroupId(int groupId);
}
