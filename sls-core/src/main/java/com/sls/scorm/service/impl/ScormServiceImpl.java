package com.sls.scorm.service.impl;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.dao.*;
import com.sls.scorm.entity.*;
import com.sls.scorm.service.ScormService;
import com.sls.system.dao.LabelDao;
import com.sls.system.entity.Label;
import com.sls.system.service.DictService;
import com.sls.user.dao.UserAttentionDao;
import com.sls.user.dao.UserDao;
import com.sls.user.entity.User;
import com.sls.util.DateUtil;
import com.sls.util.DictConstant;
import com.sls.util.FileUp;
import com.sls.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service("scormService")
public class ScormServiceImpl implements ScormService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ScormDao scormDao;

    @Autowired
    private ScoDao scoDao;

    @Autowired
    private SummarizeDao summarizeDao;

    @Autowired
    private NoteCollectDao noteCollectDao;

    @Autowired
    private DictService dictService;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private PublicScormDao publicScormDao;

    @Autowired
    private PublicDiscussesDao publicDiscussesDao;

    @Autowired
    private ScormRecordDao scormRecordDao;

    public void getUpScormGroupsByUserId(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        request.setAttribute("groupsScorm", groupDao.getUpScormGroupsByUserId(userId));
    }

    public int upScorm(HttpServletRequest request, String upFile, String upImg, Scorm scorm, int groupId) throws ServletException, IOException, ParserConfigurationException, SAXException,
            XPathExpressionException {
        try {
            FileUp fileUp = new FileUp();
            Date date = new Date();
            int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
            String fileName = date.getTime() + userId + "";
//            scorm.setScormName(BaseUtil.iso2utf(scorm.getScormName()));
            scorm.setImgPath(fileUp.upImg(request, DictConstant.TOP_SCORM_FILE_NAME, "/" + fileName, DictConstant.SCORM_IMG, upImg));
            scorm.setUploadUserId(userId);
            List<Sco> scoNodes = fileUp.analyzeXml(fileUp.upScorm(request, fileName, upFile) + DictConstant.IMSMANIFEST);
            int scormId = scormDao.addScorm(scorm);
            if (groupId != -1) {
                groupDao.addScormGroup(scormId, groupId);
            } else {
                groupDao.addDefaultScormGroup(scormId);
            }
            scoNodes.add(new Sco(scorm.getScormName(), "root", "0", "1", "", ""));
            ScoInfo scoInfo = new ScoInfo();
            for (Sco scoNode : scoNodes) {
                scoNode.setScormId(scormId);
                scoInfo.setScoId(scoDao.addSco(scoNode));
                scoInfo.setLaunchData(scoNode.getLaunchData());
                scoInfo.setPassRaw(scoNode.getPassRaw());
                scoInfo.setCoreCredit(scoNode.getCoreCredit());
                scoDao.addScoInfo(scoInfo);
            }
            //添加用户经验值
            userDao.addScore(DictConstant.EXP_SCORE, userId);
            //添加消息提醒
            userAttentionDao.countNewMessageByAttentionUserId(userId);
            request.setAttribute("result", "上传成功");
            return scormId;
        } catch (Exception e) {
            request.setAttribute("result", "上传失败");
            return DictConstant.VOID_VALUE;
        }
    }

    public void registerScorm(int scormId, HttpServletRequest request) {
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        //校验防止重复注册和注册不可使用课件
        int scormState = scormDao.findScormInfoByScormId(scormId).getInUse();
        if (scormState == DictConstant.NO_USE) {
            return;
        }
        if (scoDao.findScosByScormIdAndUserId(scormId, user.getUserId()).size() > 0) {
            return;
        }
        //添加课件相关数据
        List<Sco> scoList = scoDao.findScosByScormIdAndUserId(scormId, DictConstant.VOID_VALUE);
        ScoInfo scoInfo;
        for (Sco sco : scoList) {
            sco.setUserId(user.getUserId());
            scoInfo = scoDao.getScoApiInfoByScoId(sco.getScoId()).get(0);
            scoInfo.setScoId(scoDao.addSco(sco));
            scoInfo.setCoreStudentName(userDao.findUserLevelNameByScore(user.getScore()).getLevelName() + "、" + user.getUserName());
            scoInfo.setCoreStudentId(user.getUserId() + "");
            scoDao.addScoInfo(scoInfo);
        }
        ScormSummarize scormSummarize = new ScormSummarize();
        scormSummarize.setUserId(user.getUserId());
        scormSummarize.setScormId(scormId);
        scormSummarize.setRegisterDate(DateUtil.getSystemDate("yyyy-MM-dd"));
        summarizeDao.addScormSummarize(scormSummarize);
        //添加课件注册数
        scormDao.addVisitSum(scormId);
        //添加用户经验值
        userDao.addScore(DictConstant.EXP_SCORE, user.getUserId());
        //添加消息提醒
        userAttentionDao.countNewMessageByAttentionUserId(user.getUserId());
    }

    public void collectDealScorm(int scormId, HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        int scormState = scormDao.findScormInfoByScormId(scormId).getInUse();
        if (scormState == DictConstant.NO_USE) {
            return;
        }
        if (noteCollectDao.findCollectScormByScormIdAndUserId(scormId, userId).size() > 0) {
            userDao.cancelCollectByUserIdAndScormId(userId, scormId);
            return;
        }
        Collect collect = new Collect();
        collect.setScormId(scormId);
        collect.setUserId(userId);
        collect.setCollectDate(DateUtil.getSystemDate("yyyy-MM-dd"));
        noteCollectDao.addCollectScorm(collect);
    }

    public void addStudyNote(StudyNote studyNote) {
        if (("").equals(studyNote.getNote())) {
            return;
        }
        studyNote.setNoteType(DictConstant.TEXT_TYPE);
        studyNote.setImgPath("");
        studyNote.setDate(DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss"));
        studyNote.setUserId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        noteCollectDao.addStudyNote(studyNote);
    }

    public void getAllStudyNotesByScormIdAndUserId(int scormId, HttpServletRequest request) {
        if ("".equals(LoginUserUtil.getLoginName())) {
            return;
        }
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        StudyNote studyNote = new StudyNote();
        studyNote.setScormId(scormId);
        studyNote.setUserId(user.getUserId());
        List<StudyNote> studyNoteList = noteCollectDao.getAllStudyNotesByScormIdAndUserId(studyNote);
        request.setAttribute("noteList", studyNoteList);
        request.setAttribute("userName", user.getUserName());
        request.setAttribute("text", DictConstant.TEXT_TYPE);
        request.setAttribute("scormId", scormId);
    }

    public void delNote(int noteId) {
        noteCollectDao.delNoteByNoteId(noteId);
    }

    public void upStudyImg(HttpServletRequest request, String upImg, StudyNote studyNote) throws ServletException, IOException {
        FileUp fileUp = new FileUp();
        Date date = new Date();
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        studyNote.setNoteType(DictConstant.IMG);
        studyNote.setUserId(userId);
        studyNote.setDate(DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss"));
        studyNote.setImgPath(fileUp.upImg(request, DictConstant.STUDY_IMG, "/" + userId, studyNote.getScormId() + date.getTime() + DictConstant.PHOTO_FORM, upImg));
        noteCollectDao.addStudyNote(studyNote);
    }

    public void studyScorm(int scormId, HttpServletRequest request) {
        Scorm scorm = scormDao.findScormInfoByScormId(scormId);
        if (scorm.getInUse() == DictConstant.NO_USE) {
            return;
        }
        scorm.setShowRecommendLevel(dictService.changeDictCodeToValue(scorm.getRecommendLevel(), DictConstant.RECOMMEND));
        request.setAttribute("scorm", scorm);
        request.setAttribute("publicScorm", !publicScormDao.getInTimePublicScormByScormId(scormId).isEmpty());
    }

    public void setScormSummarizeInfo(int scormId) {
        ScormSummarize scormSummarize = new ScormSummarize();
        scormSummarize.setLastVisitTime(DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss"));
        scormSummarize.setUserId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        scormSummarize.setScormId(scormId);
        summarizeDao.changeLastVisitTimeByScormIdAndUserId(scormSummarize);
    }

    public void studyScormZtree(int scormId, HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        List<Sco> scoList = scoDao.findScosByScormIdAndUserId(scormId, userId);
        for (Sco sco : scoList) {
            sco.setShowStudyState(dictService.changeDictCodeToValue(sco.getStudyState(), DictConstant.STUDY_STATE));
        }
        request.setAttribute("scoList", scoList);
        request.setAttribute("isLast", DictConstant.LAST_VISIT);
    }

    public void changeScoState(int scormId, int scoId) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        //改变最后访问SCO
        Sco oneSco = new Sco();
        oneSco.setUserId(userId);
        oneSco.setScormId(scormId);
        oneSco.setScoId(scoId);
        scoDao.setLastVisitScoByScoId(oneSco);
        //将所有父节点变为已看
        List<Sco> scoList = scoDao.findScosByScormIdAndUserId(scormId, userId);
        for (Sco sco : scoList) {
            if (sco.getScoId() == scoId) {
                changeState(sco, scoList);
                break;
            }
        }
        ScormRecord scormRecord = new ScormRecord(userId, scormId, scoId, DateUtil.getCurrentTimestamp().toString().substring(0, 19));
        scormRecordDao.addStudyRecord(scormRecord);
    }

    public void changeState(Sco sco, List<Sco> scoList) {
        if (sco.getStudyState() != DictConstant.STUDY_STATE_1) {
            scoDao.changeStudyStateByScoId(sco.getScoId(), DictConstant.STUDY_STATE_1);
            for (Sco onesco : scoList) {
                if (onesco.getTreeId().equals(sco.getTreeParentId())) {
                    changeState(onesco, scoList);
                    break;
                }
            }
        }
    }

    public List<ScoInfo> getScoApiInfo(int scoId) {
        return scoDao.getScoApiInfoByScoId(scoId);
    }

    public void commitScoApiInfoByScoId(ScoInfo scoInfo, int scormId) {
        //获取本次学习时间，分别加入课件总学习时间和这个SCO总学习时间
        String sessionTime = scoInfo.getCoreSessionTime();
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        if (!("".equals(sessionTime))) {
            scoInfo.setCoreTotalTime(DateUtil.getTotalTime(sessionTime, scoDao.getScoApiInfoByScoId(scoInfo.getScoId()).get(0).getCoreTotalTime()));
            summarizeDao.changeTotalTimeByScormIdAndUserId(userId, scormId, DateUtil.getTotalTime(sessionTime, summarizeDao.findScormSummarizeByUserIdAndScormId(userId, scormId).get(0).getTotalTime()));
            scormDao.changeTotalTimeByScormId(scormId, DateUtil.getTotalTime(sessionTime, scormDao.findScormInfoByScormId(scormId).getTotalTime()));
        }
        //处理SCO的学习状态,测试和非测试
        if (scoInfo.getCoreCredit().equals(DictConstant.CREDIT_IM)) {
            if (("").equals(scoInfo.getCoreLessonStatus())) {
                scoInfo.setCoreLessonStatus(DictConstant.LESSON_STATUS_FAILED);
                int score = 0;
                if (!("").equals(scoInfo.getCoreScoreRaw())) {
                    score = Integer.parseInt(scoInfo.getCoreScoreRaw());
                }
                if (score >= Integer.parseInt(scoInfo.getPassRaw())) {
                    scoInfo.setCoreLessonStatus(DictConstant.LESSON_STATUS_PASS);
                }
            }
        } else {
            if (("").equals(scoInfo.getCoreLessonStatus())) {
                scoInfo.setCoreLessonStatus(DictConstant.LESSON_STATUS_COMPLETED);
            }
        }
        scoInfo.setCoreEntry(DictConstant.ENTRY_RE);
        scoInfo.setCoreLessonMode(DictConstant.LESSON_MODE_REVIEW);
        if (scoInfo.getCoreLessonStatus().equals(DictConstant.LESSON_STATUS_COMPLETED) || scoInfo.getCoreLessonStatus().equals(DictConstant.LESSON_STATUS_PASS)) {
            scoInfo.setCoreEntry("");
            scoInfo.setCoreLessonMode(DictConstant.LESSON_MODE_BROWSE);
        }
        scoDao.changeScoInfoByScoId(scoInfo);
        //判断是否通过整个课程，并更新成绩
        checkAllSco(scormId, userId);
    }

    public void checkAllSco(int scormId, int userId) {
        ScormSummarize scormSummarize = summarizeDao.findScormSummarizeByUserIdAndScormId(userId, scormId).get(0);
        if (scoDao.isAllScoClick(scormId, userId)) {
            //课件完成方式为浏览即可完成时,判断通过并处理
            if (scormDao.findScormInfoByScormId(scormId).getCompleteWay() != DictConstant.VOID_VALUE) {
                if (scormSummarize.getCompleteDate().equals("")) {
                    changeSummarize(scormSummarize, "", userId);
                    return;
                }
            }
            //课件完成方式为默认时，判断通过并处理
            checkIsPassAllSco(scormSummarize, scormId, userId);
        }
    }

    public void checkIsPassAllSco(ScormSummarize scormSummarize, int scormId, int userId) {
        //1不带测试的课件
        List<ScoInfo> scoInfoList = scoDao.findUrlScosByCreditAndScormIdAndUserId(DictConstant.CREDIT_NO, scormId, userId);
        for (ScoInfo oneScoInfo : scoInfoList) {
            if (!oneScoInfo.getCoreLessonStatus().equals(DictConstant.LESSON_STATUS_COMPLETED)) {
                return;
            }
        }
        //2带测试的课件
        scoInfoList = scoDao.findUrlScosByCreditAndScormIdAndUserId(DictConstant.CREDIT_IM, scormId, userId);
        int i = 0, sum = 0, flag = 0;
        for (ScoInfo oneScoInfo : scoInfoList) {
            if (!oneScoInfo.getCoreLessonStatus().equals(DictConstant.LESSON_STATUS_PASS)) {
                return;
            }
            if (!oneScoInfo.getCoreScoreRaw().equals("")) {
                i++;
                sum += Integer.parseInt(oneScoInfo.getCoreScoreRaw());
                flag = 1;
            }
        }
        defaultPassDeal(scormSummarize, flag, sum, i, userId);
    }

    public void defaultPassDeal(ScormSummarize scormSummarize, int flag, int sum, int i, int userId) {
        if (flag == 1) {
            //1.1带测试时，若原来有成绩，则比较成绩，只有大于原有成绩才更新
            if (scormSummarize.getGrade().equals("")) {
                changeSummarize(scormSummarize, sum / i + "", userId);
            } else {
                if ((sum / i) > Integer.parseInt(scormSummarize.getGrade())) {
                    changeSummarize(scormSummarize, sum / i + "", userId);
                }
            }
        } else {
            //1.2不带测试时，若原来没有成绩，则更新成绩
            if (scormSummarize.getCompleteDate().equals("")) {
                changeSummarize(scormSummarize, "", userId);
            }
        }
    }

    public void changeSummarize(ScormSummarize scormSummarize, String grade, int userId) {
        scormSummarize.setGrade(grade);
        scormSummarize.setCompleteDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        userDao.addScore(DictConstant.EXP_SCORE, userId);
        summarizeDao.changeCompleteInfoByScormIdAndUserId(scormSummarize);
    }

    public List<Scorm> changeScormListRecommendLevel(List<Scorm> scorms) {
        for (Scorm scorm : scorms) {
            scorm.setShowRecommendLevel(dictService.changeDictCodeToValue(scorm.getRecommendLevel(), DictConstant.RECOMMEND));
        }
        return scorms;
    }

    public void getAllAboutScormInfo(int scormId, HttpServletRequest request) {
        Scorm scormInfo = scormDao.findScormInfoByScormId(scormId);
        if (scormInfo.getInUse() == DictConstant.NO_USE) {
            return;
        }
        scormInfo.setShowRecommendLevel(dictService.changeDictCodeToValue(scormInfo.getRecommendLevel(), DictConstant.RECOMMEND));
        scormInfo.setShowUploadUserId(userDao.findUserAllInfoById(scormInfo.getUploadUserId()).getUserName());
        request.setAttribute("scormInfo", scormInfo);
        request.setAttribute("labels", labelDao.getLabelByScormId(scormId));
        request.setAttribute("allComments", summarizeDao.getAllCommentsByScormId(scormId));
        if (!("").equals(LoginUserUtil.getLoginName())) {
            request.setAttribute("userId", userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        }
        //找到同系列课件
        List<Scorm> groupScorms = groupDao.getGroupScormsByScormId(scormId);
        request.setAttribute("groupScorms", changeScormListRecommendLevel(groupScorms));
        //找到用户上传的其他课件
        List<Scorm> otherScorms = scormDao.getInUseUpScormInfoByUserId(scormInfo.getUploadUserId());
        request.setAttribute("otherScorms", changeScormListRecommendLevel(otherScorms));
        //找到章节结构
        getScoList(request, scormId);
    }

    public void getScoList(HttpServletRequest request, int scormId) {
        int userId = DictConstant.VOID_VALUE;
        if (!("").equals(LoginUserUtil.getLoginName())) {
            userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        }
        List<Sco> scoList = scoDao.findScosByScormIdAndUserId(scormId, userId);
        if (scoList.size() == 0) {
            scoList = scoDao.findScosByScormIdAndUserId(scormId, DictConstant.VOID_VALUE);
        }
        if (userId != DictConstant.VOID_VALUE) {
            StringBuilder title = new StringBuilder();
            for (Sco sco : scoList) {
                title.append("(").append(dictService.changeDictCodeToValue(sco.getStudyState(), DictConstant.STUDY_STATE)).append(")");
                title.append(sco.getTitle());
                if (sco.getLastVisit() == DictConstant.LAST_VISIT) {
                    title.append("(上次学到这里)");
                }
                sco.setTitle(title.toString());
                title.setLength(0);
            }
        }
        request.setAttribute("scoList", scoList);
    }

    public void getScormOperate(int scormId, HttpServletRequest request) {
        boolean showCollect = false;
        boolean showDiscussInput = false;
        boolean collect = false;
        boolean register = false;
        boolean study = false;
        boolean complete = false;
        if (!"".equals(LoginUserUtil.getLoginName())) {
            showCollect = true;
            if (scormDao.findScormInfoByScormId(scormId).getInUse() == DictConstant.IN_USE) {
                int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
                if (noteCollectDao.checkNotHasCollected(scormId, userId)) {
                    collect = true;
                }
                if (scormDao.checkNotHasRegister(scormId, userId)) {
                    register = true;
                }
                if (!register) {
                    study = true;
                    showDiscussInput = true;
                }
                if (summarizeDao.isCompleteScorm(scormId, userId)) {
                    complete = true;
                }
            }
        }
        request.setAttribute("showCollect", showCollect);
        request.setAttribute("showDiscussInput", showDiscussInput);
        request.setAttribute("collect", collect);
        request.setAttribute("register", register);
        request.setAttribute("study", study);
        request.setAttribute("complete", complete);
        request.setAttribute("publicScorm", !publicScormDao.getInTimePublicScormByScormId(scormId).isEmpty());
    }

    public Page<Scorm> listNotAuditScormPageList(PageParameter pageParameter, Scorm scorm) {
        return scormDao.listNotAuditScormPageList(pageParameter, scorm);
    }


    public Page<Scorm> listAuditScormPageList(PageParameter pageParameter, Scorm scorm) {
        Page<Scorm> scormPage = scormDao.listAuditScormPageList(pageParameter, scorm);
        String totalTime;
        int[] splitTime;
        for (Scorm oneScorm : scormPage.getResultList()) {
            totalTime = oneScorm.getTotalTime();
            if (("").equals(totalTime)) {
                oneScorm.setTotalTime(DictConstant.NO_LOG);
            } else {
                splitTime = DateUtil.splitScormTime(totalTime);
                oneScorm.setTotalTime(splitTime[0] + "小时" + splitTime[1] + "分钟" + splitTime[2] + "秒" + splitTime[3] + "毫秒");
            }
        }
        return scormPage;
    }

    public void checkScormInfo(HttpServletRequest request, int scormId) {
        Scorm scorm = scormDao.findScormInfoByScormId(scormId);
        scorm.setShowRecommendLevel(dictService.changeDictCodeToValue(scorm.getRecommendLevel(), DictConstant.RECOMMEND));
        scorm.setShowUploadUserId(userDao.findUserAllInfoById(scorm.getUploadUserId()).getUserName());
        List<Sco> scoList = scoDao.findScosByScormIdAndUserId(scormId, DictConstant.VOID_VALUE);
        List<User> registerUsers = userDao.getRegisterUsers(scormId);
        request.setAttribute("completeRate", scormDao.findCompleteRateByScormId(scormId));
        request.setAttribute("scorm", scorm);
        request.setAttribute("scoList", scoList);
        request.setAttribute("registerUsers", registerUsers);
        request.setAttribute("inUse", DictConstant.IN_USE);
    }

    public void changeScormInUse(int scormId, int isUse) {
        String date = DateUtil.getSystemDate("yyyy-MM-dd");
        if (isUse == DictConstant.IN_USE) {
            if (!scormDao.findScormInfoByScormId(scormId).getPassDate().equals("")) {
                userDao.addScore(DictConstant.EXP_SCORE, scormDao.findScormInfoByScormId(scormId).getUploadUserId());
            }
        } else {
            date = "";
        }
        scormDao.changeScormInUse(scormId, isUse, date);
    }

    public String changeScormRecommend(int scormId, int recommend) {
        scormDao.changeScormRecommend(scormId, recommend);
        return dictService.changeDictCodeToValue(recommend, DictConstant.RECOMMEND);
    }

    public void changScormCompleteWay(int scormId, int completeWay) {
        scormDao.changScormCompleteWayByScormId(scormId, completeWay);
    }

    public Boolean evaluateScorm(ScormSummarize scormSummarize) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        scormSummarize.setUserId(userId);
        ScormSummarize oldScormSummarize = summarizeDao.findScormSummarizeByUserIdAndScormId(userId, scormSummarize.getScormId()).get(0);
        if (("").equals(oldScormSummarize.getCompleteDate())) {
            return false;
        }
        if (0 == oldScormSummarize.getScore()) {
            userDao.addScore(DictConstant.EXP_SCORE, userId);
        }
        summarizeDao.changeSummarizeScoreByUserIdAndScormId(scormSummarize);
        scormDao.updateScormScoreByScormId(scormSummarize.getScormId());
        return true;
    }

    public void getComments(int scormId, HttpServletRequest request) {
        request.setAttribute("allComments", summarizeDao.getAllCommentsByScormId(scormId));
        if (!("").equals(LoginUserUtil.getLoginName())) {
            request.setAttribute("user", userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0));
        }
    }

    public void discussScorm(ScormSummarize scormSummarize) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        if (("").equals(summarizeDao.findScormSummarizeByUserIdAndScormId(userId, scormSummarize.getScormId()).get(0).getDiscussDate())) {
            userDao.addScore(DictConstant.EXP_SCORE, userId);
        }
        scormSummarize.setUserId(userId);
        scormSummarize.setDiscussDate(DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss"));
        summarizeDao.discussScorm(scormSummarize);
    }

    public void getSummarizeInfo(int scormId, HttpServletRequest request) {
        if ("".equals(LoginUserUtil.getLoginName())) {
            return;
        }
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        List<ScormSummarize> scormSummarizes = summarizeDao.findScormSummarizeByUserIdAndScormId(userId, scormId);
        if (scormSummarizes.size() > 0) {
            if (!("").equals(scormSummarizes.get(0).getTotalTime())) {
                int[] splitTime = DateUtil.splitScormTime(scormSummarizes.get(0).getTotalTime());
                scormSummarizes.get(0).setTotalTime(splitTime[0] + "小时" + splitTime[1] + "分钟");
            }
            request.setAttribute("summarize", scormSummarizes.get(0));
        }
        request.setAttribute("records", scormRecordDao.getRecordListByUserIdAndScormId(userId, scormId));
    }

    public void findResult(String info, HttpServletRequest request) {
        request.setAttribute("info", info);
        request.setAttribute("findNameScorm", scormDao.queryScormByFieldName(info, "scorm_name"));
        request.setAttribute("findScoreScorm", scormDao.queryScormByFieldName(info, "score"));
        request.setAttribute("findUsers", userDao.queryUsersByName(info));
    }

    public void findRecommendScorm(HttpServletRequest request) {
        List<Label> labelList;
        StringBuilder labelName = new StringBuilder();
        List<Scorm> scormList;
        if (!"".equals(LoginUserUtil.getLoginName())) {
            int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
            scormList = scormDao.findRecommendScormByUserLabel(userId);
        } else {
            scormList = scormDao.indexFindTopScormByFieldName("score", 5);
        }
        for (Scorm scorm : scormList) {
            scorm.setShowRecommendLevel(dictService.changeDictCodeToValue(scorm.getRecommendLevel(), DictConstant.RECOMMEND));
            labelList = labelDao.getLabelByScormId(scorm.getScormId());
            for (Label label : labelList) {
                labelName.append(label.getLabelName()).append("、");
            }
            scorm.setLabelName(labelName.toString());
        }
        request.setAttribute("recommendScorm", scormList);
    }

    public void findRegisterScorm(HttpServletRequest request) {
        if (!"".equals(LoginUserUtil.getLoginName())) {
            int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
            request.setAttribute("registerScorm", scormDao.findRegisterScormByUserId(userId));
        }
    }

    public void findReviewsByScormId(int scormId, HttpServletRequest request) {
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        int myEvaluateScore = summarizeDao.findScormSummarizeByUserIdAndScormId(user.getUserId(), scormId).get(0).getScore();
        request.setAttribute("completeDate", summarizeDao.findScormSummarizeByUserIdAndScormId(user.getUserId(), scormId).get(0).getCompleteDate());
        request.setAttribute("AllComments", summarizeDao.getAllCommentsByScormId(scormId));
        request.setAttribute("nowUser", user);
        request.setAttribute("myEvaluateScore", myEvaluateScore);
    }

    public void getAllRegisterUsers(int scormId, HttpServletRequest request) {
        request.setAttribute("registerUsers", summarizeDao.getAllRegisterUsersByScormId(scormId));
    }

    public void getUserUpScormsByScormId(int scormId, HttpServletRequest request) {
        int userId = scormDao.findScormInfoByScormId(scormId).getUploadUserId();
        List<Scorm> scorms = scormDao.getInUseUpScormInfoByUserId(userId);
        request.setAttribute("otherScorms", changeScormListRecommendLevel(scorms));
    }

    public List<Scorm> getRegisterScormsByUserId(int userId) {
        List<Scorm> scorms = scormDao.findRegisterScormByUserId(userId);
        return changeScormListRecommendLevel(scorms);
    }

    public List<Scorm> getUpScormsByUserId(int userId) {
        List<Scorm> scorms = scormDao.getInUseUpScormInfoByUserId(userId);
        return changeScormListRecommendLevel(scorms);
    }

    public void sortScorm(int labelId, HttpServletRequest request) {
        if (labelId == 0) {
            request.setAttribute("sortName", "全部课件");
        } else {
            request.setAttribute("sortName", labelDao.findLabelById(labelId).getLabelName());
        }
        List<Scorm> scormList = scormDao.sortScormByLabelName(labelId);
        request.setAttribute("sortLabelScorm", changeScormListRecommendLevel(scormList));
    }

    public Page<PublicScorm> listAllPublicScormPageList(PageParameter pageParameter, PublicScorm publicScorm) {
        return publicScormDao.listAllPublicScormPageList(pageParameter, publicScorm);
    }

    public void delPublicScorm(int publicId) {
        publicScormDao.delPublicScormByPublicId(publicId);
    }

    public List<Scorm> getAllInUseScorm() {
        return scormDao.getAllScormByInUse(DictConstant.IN_USE);
    }

    public void addPublicScorm(PublicScorm publicScorm) {
        publicScormDao.addPublicScorm(publicScorm);
    }

    public void getPublicScormInfo(int scormId, HttpServletRequest request) {
        PublicScorm publicScorm = publicScormDao.getInTimePublicScormByScormId(scormId).get(0);
        String nowTime = DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss");
        request.setAttribute("publicScorm", publicScorm);
        request.setAttribute("scorm", scormDao.findScormInfoByScormId(scormId));
        request.setAttribute("userId", userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        List<PublicDiscusses> publicDiscussesList = publicDiscussesDao.getPublicDiscussesByPublicIdAndNowTime(publicScorm.getPublicId(), nowTime);
        request.setAttribute("discussId", 0);
        if (!publicDiscussesList.isEmpty()) {
            request.setAttribute("discussId", publicDiscussesList.get(0).getDiscussId());
        }
    }

    public void getScormGroupsByScormId(int scormId, HttpServletRequest request) {
        List<Scorm> scormList = groupDao.getGroupScormsByScormId(scormId);
        request.setAttribute("groupScorms", changeScormListRecommendLevel(scormList));
    }

    public List<Scorm> findGroupsScorm() {
        List<Scorm> groups = groupDao.findGroupScormsByNum(10);
        List<Scorm> useGroups = new ArrayList<Scorm>();
        for (Scorm group : groups) {
            if (!groupDao.notHaveInUseScormCheckByGroupId(group.getGroupId())) {
                group.setGroupScore(groupDao.getGroupScoreByGroupId(group.getGroupId()));
                useGroups.add(group);
            }
        }
        return useGroups;
    }

    public List<Scorm> findLatestScormsByNum(int num) {
        return scormDao.findLatestScormsByNum(num);
    }

    public List<Scorm> getGroupScorms(int groupId) {
        return groupDao.getGroupScormsByGroupId(groupId);
    }
}
