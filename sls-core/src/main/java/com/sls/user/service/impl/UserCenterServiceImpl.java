package com.sls.user.service.impl;

import com.sls.scorm.dao.ScormDao;
import com.sls.scorm.dao.ScormRecordDao;
import com.sls.scorm.entity.Scorm;
import com.sls.system.service.DictService;
import com.sls.user.dao.UserAttentionDao;
import com.sls.user.dao.UserDao;
import com.sls.user.dao.UserQuestionDao;
import com.sls.user.entity.User;
import com.sls.user.entity.UserAttention;
import com.sls.user.entity.UserQuestion;
import com.sls.user.service.UserCenterService;
import com.sls.util.DictConstant;
import com.sls.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Transactional
@Service("userCenterService")
public class UserCenterServiceImpl implements UserCenterService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ScormDao scormDao;

    @Autowired
    private DictService dictService;

    @Autowired
    private UserQuestionDao userQuestionDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private ScormRecordDao scormRecordDao;

    public void toUserCenter(HttpServletRequest request) {
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        user.setLevelName(userDao.findUserLevelNameByScore(user.getScore()).getLevelName());
        request.setAttribute("user", user);
    }

    public void selectModule(HttpServletRequest request, int module) {
        String centerUrl = new String();
        switch (module) {
            //统计信息
            case 1:
                centerUrl = "user/center/userCenterInfo";
                break;
            //个人资料
            case 2:
                centerUrl = "user/center/userInfoDo";
                break;
            //注册的课件
            case 3:
                centerUrl = "user/center/registerScormDo";
                break;
            //收藏的课件
            case 4:
                centerUrl = "user/center/collectScormDo";
                break;
            //上传的课件
            case 5:
                centerUrl = "user/center/myUpScormsDo";
                break;
            //上传课件
            case 6:
                centerUrl = "user/center/upScormDo";
                break;
            //评价课件
            case 7:
                centerUrl = "user/center/evaluateScormDo";
                break;
            //用户关注列表
            case 8:
                centerUrl = "user/center/userAttentionDo";
                break;
            //提的问题
            case 9:
                centerUrl = "user/center/askQuestionsDo";
                break;
            //提问我的问题
            case 10:
                centerUrl = "user/center/askUserQuestionsDo";
                break;
            //添加笔记
            case 11:
                centerUrl = "user/center/addNoteDo";
                break;
            //笔记本
            case 12:
                centerUrl = "user/center/scormNotesDo";
                break;
            //提交问题
            case 13:
                centerUrl = "user/center/addQuestionDo";
                //日程
            case 14:
                centerUrl = "user/center/calendarDo";
        }
        request.setAttribute("centerUrl", centerUrl);
    }

    public void toUserInfo(HttpServletRequest request) {
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        user.setLevelName(userDao.findUserLevelNameByScore(user.getScore()).getLevelName());
        request.setAttribute("user", user);
    }

    public void getAllRegisterScormInfo(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        List<Scorm> scormList = scormDao.getAllRegisterScormInfoByUserId(userId);
        request.setAttribute("allScorm", scormList);
    }

    public void getAllCollectScormInfo(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        List<Scorm> scormList = scormDao.getAllCollectScormInfoByUserId(userId);
        request.setAttribute("allScorm", scormList);
    }

    public void getAllUpScormInfo(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        List<Scorm> scormList = scormDao.getInUseUpScormInfoByUserId(userId);
        for (Scorm oneScorm : scormList) {
            oneScorm.setShowInUse(dictService.changeDictCodeToValue(oneScorm.getInUse(), DictConstant.SCORM_STATE));
        }
        request.setAttribute("allScorm", scormList);
    }

    public void getAskQuestions(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        request.setAttribute("questions", userQuestionDao.getAskQuestionsByAskUserId(userId));
    }

    public void getUserQuestions(HttpServletRequest request) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        request.setAttribute("questions", userQuestionDao.getUserQuestionsByAskUserId(userId));
    }

    public void getQuestionInfoAsk(HttpServletRequest request, HttpSession session, int questionId) {
        UserQuestion userQuestion = userQuestionDao.getQuestionInfoByQuestionId(questionId);
        User user = userDao.findUserAllInfoById(userQuestion.getAnswerUserId());
        userQuestion.setUserName(user.getUserName());
        request.setAttribute("question", userQuestion);
        //取消新的回答及更新新的回答数目
//        userQuestionDao.cancelNewAnswerByQuestionId(questionId);
        session.setAttribute("answerNum", userQuestionDao.getNewAnswerNumByUserId(userQuestion.getAskUserId()) - 1);
    }

    public void changeQuestionAskContent(UserQuestion userQuestion) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        UserQuestion oldUserQuestion = userQuestionDao.getQuestionInfoByQuestionId(userQuestion.getQuestionId());
        if (oldUserQuestion.getAskUserId() == userId) {
            userQuestionDao.changeQuestionAskContentByQuestionId(userQuestion);
            userQuestionDao.setNewAskByQuestionId(userQuestion.getQuestionId());
        }
    }

    public void getQuestionInfoAnswer(HttpServletRequest request, HttpSession session, int questionId) {
        UserQuestion userQuestion = userQuestionDao.getQuestionInfoByQuestionId(questionId);
        User user = userDao.findUserAllInfoById(userQuestion.getAskUserId());
        userQuestion.setUserName(user.getUserName());
        request.setAttribute("question", userQuestion);
        //取消新的提问及更新新的提问数目
//        userQuestionDao.cancelNewAskByQuestionId(questionId);
        session.setAttribute("questionNum", userQuestionDao.getNewQuestionNumByUserId(userQuestion.getAnswerUserId()) - 1);
    }


    public void changeQuestionAnswerContent(UserQuestion userQuestion) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        UserQuestion oldUserQuestion = userQuestionDao.getQuestionInfoByQuestionId(userQuestion.getQuestionId());
        if (oldUserQuestion.getAnswerUserId() == userId) {
            userQuestionDao.changeQuestionAnswerContentByQuestionId(userQuestion);
            userQuestionDao.setNewAnswerByQuestionId(userQuestion.getQuestionId());
        }
    }

    public void cancelNewAnswerByQuestionId(int questionId) {
        userQuestionDao.cancelNewAnswerByQuestionId(questionId);
    }

    public void cancelNewAskByQuestionId(int questionId) {
        userQuestionDao.cancelNewAskByQuestionId(questionId);
    }

    public List<UserAttention> getAttentionUsers() {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        return userAttentionDao.getAttentionUsersByUserId(userId);
    }

    public void getRecordInfo(HttpServletRequest request, int scormId) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        request.setAttribute("records", scormRecordDao.getRecordListByUserIdAndScormId(userId, scormId));
    }
}
