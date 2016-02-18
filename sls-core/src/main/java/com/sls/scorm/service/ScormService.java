package com.sls.scorm.service;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.*;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public interface ScormService {
    void getUpScormGroupsByUserId(HttpServletRequest request);

    int upScorm(HttpServletRequest request, String upFile, String upImg, Scorm scorm, int groupId) throws ServletException, IOException, ParserConfigurationException, SAXException,
            XPathExpressionException;

    void registerScorm(int scormId, HttpServletRequest request);

    void studyScorm(int scormId, HttpServletRequest request);

    void setScormSummarizeInfo(int scormId);

    void studyScormZtree(int scormId, HttpServletRequest request);

    void changeScoState(int scormId, int scoId);

    List<ScoInfo> getScoApiInfo(int scoId);

    void commitScoApiInfoByScoId(ScoInfo scoInfo, int scormId);

    void getAllAboutScormInfo(int scormId, HttpServletRequest request);

    void getScormOperate(int scormId, HttpServletRequest request);

    void collectDealScorm(int scormId, HttpServletRequest request);

    void addStudyNote(StudyNote studyNote);

    void getAllStudyNotesByScormIdAndUserId(int scormId, HttpServletRequest request);

    void delNote(int noteId);

    void upStudyImg(HttpServletRequest request, String upImg, StudyNote studyNote) throws ServletException, IOException;

    Page<Scorm> listNotAuditScormPageList(PageParameter pageParameter, Scorm scorm);

    Page<Scorm> listAuditScormPageList(PageParameter pageParameter, Scorm scorm);

    void checkScormInfo(HttpServletRequest request, int scormId);

    void changeScormInUse(int scormId, int isUse);

    String changeScormRecommend(int scormId, int recommend);

    void changScormCompleteWay(int scormId, int completeWay);

    Boolean evaluateScorm(ScormSummarize scormSummarize);

    void getComments(int scormId, HttpServletRequest request);

    void discussScorm(ScormSummarize scormSummarize);

    void getSummarizeInfo(int scormId, HttpServletRequest request);

    void findResult(String info, HttpServletRequest request);

    void findRecommendScorm(HttpServletRequest request);

    void findRegisterScorm(HttpServletRequest request);

    void findReviewsByScormId(int scormId, HttpServletRequest request);

    void getAllRegisterUsers(int scormId, HttpServletRequest request);

    void getUserUpScormsByScormId(int scormId, HttpServletRequest request);

    List<Scorm> getUpScormsByUserId(int userId);

    List<Scorm> getRegisterScormsByUserId(int userId);

    void sortScorm(int labelId, HttpServletRequest request);

    Page<PublicScorm> listAllPublicScormPageList(PageParameter pageParameter, PublicScorm publicScorm);

    void delPublicScorm(int publicId);

    List<Scorm> getAllInUseScorm();

    void addPublicScorm(PublicScorm publicScorm);

    void getPublicScormInfo(int scormId, HttpServletRequest request);

    void getScormGroupsByScormId(int scormId, HttpServletRequest request);

    List<Scorm> findGroupsScorm();

    List<Scorm> findLatestScormsByNum(int num);

    List<Scorm> getGroupScorms(int groupId);
}
