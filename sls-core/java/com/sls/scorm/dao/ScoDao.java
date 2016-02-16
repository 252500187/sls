package com.sls.scorm.dao;

import com.sls.scorm.entity.Sco;
import com.sls.scorm.entity.ScoInfo;

import java.util.List;

public interface ScoDao {
    int addSco(Sco sco);

    void addScoInfo(ScoInfo scoInfo);

    List<Sco> findScosByScormIdAndUserId(int scormId, int userId);

    void changeStudyStateByScoId(int scoId, int studyState);

    List<ScoInfo> getScoApiInfoByScoId(int scoId);

    void changeScoInfoByScoId(ScoInfo scoInfo);

    void setLastVisitScoByScoId(Sco sco);

    Boolean isAllScoClick(int scormId, int userId);

    List<ScoInfo> findUrlScosByCreditAndScormIdAndUserId(String credit, int scormId, int userId);
}
