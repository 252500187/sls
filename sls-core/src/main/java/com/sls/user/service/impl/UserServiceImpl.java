package com.sls.user.service.impl;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.dao.PublicDiscussesDao;
import com.sls.scorm.dao.PublicScormDao;
import com.sls.scorm.dao.ScormDao;
import com.sls.scorm.dao.SummarizeDao;
import com.sls.scorm.entity.PublicDiscusses;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.system.dao.LabelDao;
import com.sls.system.entity.Label;
import com.sls.system.service.DictService;
import com.sls.user.dao.*;
import com.sls.user.entity.*;
import com.sls.user.service.UserService;
import com.sls.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private DictService dictService;

    @Autowired
    private SummarizeDao summarizeDao;

    @Autowired
    private ScormDao scormDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private UserQuestionDao userQuestionDao;

    @Autowired
    private BackMessageDao backMessageDao;

    @Autowired
    private BackAnnouncementDao backAnnouncementDao;

    @Autowired
    private PublicScormDao publicScormDao;

    @Autowired
    private PublicDiscussesDao publicDiscussesDao;

    @Autowired
    private ChangePasswordDao changePasswordDao;

    public Page<User> listUserPageList(PageParameter pageParameter, User user) {
        Page<User> userPage = userDao.findUserPageList(pageParameter, user);
        for (User user1 : userPage.getResultList()) {
            user1.setShowInUse(dictService.changeDictCodeToValue(user1.getInUse(), DictConstant.STATE));
            user1.setUpLoadScormNum(userDao.findUploadScormNumByUserId(user1.getUserId()));
        }
        return userPage;
    }


    public User getUserAllInfoById(int id) {
        User user = userDao.findUserAllInfoById(id);
        user.setLevelName(userDao.findUserLevelNameByScore(user.getScore()).getLevelName());
        List<UserLevel> nextUserLevel = userDao.findUserNextLevelByScore(user.getScore());
        UserLevel userNowLevel = userDao.findUserNowLevelByScore(user.getScore());
        if (nextUserLevel.size() == 0) {
            user.setFinalScore(100);
        } else {
            user.setFinalScore((user.getScore() - userNowLevel.getScore()) * 100 / (nextUserLevel.get(0).getScore() - userNowLevel.getScore()));
        }
        return user;
    }


    public List<User> getUserByLoginName(String loginName) {
        return userDao.findInUseUserByLoginName(loginName);
    }


    public Boolean checkRepeatLoginName(String loginName, String oldName) {
        Boolean hasLoginName = userDao.checkRepeatLoginName(loginName);
        return !(hasLoginName && !oldName.equals(loginName));
    }


    public Boolean checkRepeatUserName(String userName, String oldName) {
        Boolean hasLoginName = userDao.checkRepeatUserName(userName);
        return !(hasLoginName && !oldName.equals(userName));
    }


    public void addUser(User user) {
        user.setInUse(DictConstant.IN_USE);
        user.setRoleId(roleDao.findRoleByAuthority(DictConstant.ROLE_AUTHORITY_USER).getRoleId());
        user.setRegisterDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        user.setUserName(DictConstant.DEFAULT_USER_NAME);
        user.setScore(0);
        int id = userDao.addUser(user);
        user.setUserId(id);
        user.setImgUrl(DictConstant.DEFAULT_USER_PHOTO);
        userDao.addUserInfo(user);
        UserRole userRole = new UserRole();
        userRole.setRoleId(user.getRoleId());
        userRole.setUserId(id);
        userRoleDao.addUserRole(userRole);
    }


    public void editUser(HttpSession session, HttpServletRequest request, User user) {
        User oldUser = userDao.findUserAllInfoById(user.getUserId());
        if (oldUser.getImgUrl().equals(DictConstant.DEFAULT_USER_PHOTO) && oldUser.getUserName().equals(DictConstant.DEFAULT_USER_NAME)) {
            userDao.addScore(DictConstant.EXP_SCORE, user.getUserId());
        }
        user.setUserName(BaseUtil.iso2utf(user.getUserName()));
        userDao.editUser(user);
        request.setAttribute("userName", user.getUserName());
        session.setAttribute("userName", user.getUserName());
    }


    public void upHeadImg(HttpSession session, HttpServletRequest request, String upImg) throws ServletException, IOException {
        if (request.getParameter("haveImg").equals("")) {
            return;
        }
        FileUp fileUp = new FileUp();
        User user = new User();
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        user.setUserId(userId);
        Date date = new Date();
        user.setImgUrl(fileUp.upImg(request, DictConstant.USER_PHOTO_NAME, "", userId + "time" + date.getTime() + DictConstant.PHOTO_FORM, upImg));
        userDao.upUserPhoto(user);
        request.setAttribute("imgUrl", user.getImgUrl());
        session.setAttribute("userImg", user.getImgUrl());
    }


    public Boolean checkRepeatLoginName(String loginName) {
        return !userDao.checkRepeatLoginName(loginName);
    }


    public Boolean checkRepeatEmail(String email) {
        return !userDao.checkRepeatEmail(email);
    }


    public void shieldUser(int userId) {
        User user = userDao.findUserAllInfoById(userId);
        if (user.getInUse() == DictConstant.NO_USE) {
            user.setInUse(DictConstant.IN_USE);
        } else {
            user.setInUse(DictConstant.NO_USE);
        }
        userDao.editUseState(user);
    }


    public void getUserNextLevelNameByScore(HttpServletRequest request) {
        User user = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0);
        request.setAttribute("user", user);
        List<UserLevel> nextUserLevel = userDao.findUserNextLevelByScore(user.getScore());
        UserLevel userNowLevel = userDao.findUserNowLevelByScore(user.getScore());
        if (nextUserLevel.size() == 0) {
            request.setAttribute("finalScore", "100");
        } else {
            request.setAttribute("nextLevel", nextUserLevel.get(0).getLevelName());
            request.setAttribute("finalScore", (user.getScore() - userNowLevel.getScore()) * 100 / (nextUserLevel.get(0).getScore() - userNowLevel.getScore()));
        }
    }


    public List<Label> getPieCharts(int userId) {
        if (userId == -1) {
            userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        }
        return userDao.getPieChartsByUserId(userId);
    }


    public Page<ScormSummarize> getDiscussPageList(PageParameter pageParameter, ScormSummarize scormSummarize) {
        return summarizeDao.findDiscussPageList(pageParameter, scormSummarize);
    }


    public void shieldDiscuss(int userId, int scormId) {
        summarizeDao.shieldDiscuss(userId, scormId);
    }


    public void adminIndexStatisticInfo(HttpServletRequest request) {
        //获取所有课件标签饼状图
        List<Label> labels = labelDao.getAllLabel();
        for (Label label : labels) {
            label.setLabelId(labelDao.getAllScormLabelNumByLableId(label.getLabelId()));
        }
        request.setAttribute("labels", labels);
        //获取比例信息
        request.setAttribute("userNum", userDao.getAllUserNum());
        request.setAttribute("useUserNum", userDao.getUseUserNum());
        request.setAttribute("scormNum", scormDao.getAllScormNum());
        request.setAttribute("useScormNum", scormDao.getUseScormNum());
        //获取课件排行
        request.setAttribute("scormSum", scormDao.indexFindTopScormByFieldName("register_sum", 10));
        List<Scorm> scormTimeList = scormDao.indexFindTopScormByFieldName("total_time", 10);
        int[] splitTime;
        for (Scorm scorm : scormTimeList) {
            splitTime = DateUtil.splitScormTime(scorm.getTotalTime());
            scorm.setTotalTime(splitTime[0] + "小时" + splitTime[1] + "分钟");
        }
        request.setAttribute("scormTime", scormTimeList);
        request.setAttribute("scormScore", scormDao.indexFindTopScormByFieldName("score", 10));
        request.setAttribute("scormLevel", scormDao.indexFindTopScormByFieldName("recommend_level", 10));
    }


    public void getUserOperate(int userInfoId, HttpServletRequest request) {
        Boolean isAttention = true;
        Boolean showAttention = true;
        Boolean showQuestion = true;
        if (LoginUserUtil.getLoginName().equals("")) {
            showAttention = false;
        } else {
            int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
            if (userId == userInfoId) {
                showAttention = false;
            }
            if (userAttentionDao.findInUseAttention(userId, userInfoId).size() > 0) {
                isAttention = false;
            }
            if (userQuestionDao.findNoAnswerQuestions(userInfoId, userId).size() > 0) {
                showQuestion = false;
            }
        }
        request.setAttribute("isAttention", isAttention);
        request.setAttribute("showAttention", showAttention);
        request.setAttribute("showQuestion", showQuestion);
    }


    public List<User> getAttentionUserUsersByUserId(int userId) {
        return userAttentionDao.getAttentionUserUsersByUserId(userId);
    }


    public void userAttentionDeal(int userAttentionId) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        UserAttention userAttention = new UserAttention();
        userAttention.setUserAttentionId(userAttentionId);
        userAttention.setUserId(userId);
        userAttention.setNewMessage(0);
        userAttention.setState(DictConstant.IN_USE);
        List<UserAttention> userAttentions = userAttentionDao.findAttention(userId, userAttentionId);
        //之前未加好友的处理
        if (userAttentions.size() < 1) {
            userAttentionDao.addUserAttention(userAttention);
            //增加被关注者的经验值
            userDao.addScore(DictConstant.EXP_SCORE, userAttentionId);
        } else {
            //之前已加好友的处理
            if (userAttentions.get(0).getState() == DictConstant.IN_USE) {
                userAttention.setState(DictConstant.NO_USE);
                userAttentionDao.changeUserAttentionState(userAttention);
            } else {
                userAttentionDao.changeUserAttentionState(userAttention);
            }
        }
    }


    public void clearAllNewMessage(int attentionUserId, HttpSession session) {
        if (!("").equals(LoginUserUtil.getLoginName())) {
            //清空新消息提示
            int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
            userAttentionDao.clearAllNewMessageByUserIdAndAttentionUserId(userId, attentionUserId);
            //重设好友列表的数据
            List<UserAttention> userAttentionList = userAttentionDao.getAttentionUsersByUserId(userId);
            int messageNum = 0;
            for (UserAttention userAttention : userAttentionList) {
                messageNum += userAttention.getNewMessage();
            }
            session.setAttribute("attentionUsers", userAttentionList);
            session.setAttribute("messageNum", messageNum);
        }
    }


    public List<UserQuestion> getUserAnsWerQuestionsByUserId(int userId) {
        return userQuestionDao.getUserAnsWerQuestionsByAnswerUserId(userId);
    }


    public Boolean addUserQuestion(int answerUserId, String questionDescribe) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        //若未关注或有未回答问题则失败
        if (userAttentionDao.findAttention(userId, answerUserId).size() < 1
                || userQuestionDao.findNoAnswerQuestions(answerUserId, userId).size() > 0) {
            return false;
        }
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setAnswerUserId(answerUserId);
        userQuestion.setAskUserId(userId);
        userQuestion.setAskDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        userQuestion.setAskContent(questionDescribe);
        userQuestion.setAnswerContent("");
        userQuestion.setNewAsk(DictConstant.IN_USE);
        userQuestion.setNewAnswer(DictConstant.NO_USE);
        userQuestionDao.addUserQuestion(userQuestion);
        return true;
    }


    public List<User> getNumRecommendUsers(int num) {
        return userDao.getUsersOrderByScoreAndNum(num);
    }


    public void sendUserMessage(BackMessage backMessage) {
        backMessage.setAdminId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        backMessage.setDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        backMessage.setState(DictConstant.IN_USE);
        backMessageDao.addBackMessage(backMessage);
    }


    public void sendMessage(String content, String userIds) {
        BackMessage backMessage = new BackMessage();
        backMessage.setAdminId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        backMessage.setDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        backMessage.setState(DictConstant.IN_USE);
        backMessage.setContent(content);
        for (String userId : userIds.split(",")) {
            backMessage.setUserId(Integer.parseInt(userId));
            backMessageDao.addBackMessage(backMessage);
        }
    }


    public void cancelMessageByMessageId(int messageId) {
        backMessageDao.cancelMessageByMessageId(messageId);
    }


    public Page<BackMessage> getMessagePageList(PageParameter pageParameter, BackMessage backMessage) {
        Page<BackMessage> backMessagePage = backMessageDao.getMessagePageList(pageParameter, backMessage);
        for (BackMessage oneBackMessage : backMessagePage.getResultList()) {
            oneBackMessage.setUserName(userDao.findUserAllInfoById(oneBackMessage.getUserId()).getUserName());
        }
        return backMessagePage;
    }


    public List<User> getAllInUseUsers() {
        return userDao.getAllUsersByInUse(DictConstant.IN_USE);
    }


    public void delMessage(int messageId) {
        backMessageDao.delMessageByMessageId(messageId);
    }


    public BackMessage getMessageInfo(int messageId) {
        BackMessage backMessage = backMessageDao.getMessageInfoByMessageId(messageId);
        backMessage.setUserName(userDao.findUserAllInfoById(backMessage.getUserId()).getUserName());
        return backMessage;
    }


    public void transMessage(int messageId, String userIds) {
        BackMessage backMessage = backMessageDao.getMessageInfoByMessageId(messageId);
        backMessage.setAdminId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        backMessage.setDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        backMessage.setState(DictConstant.IN_USE);
        for (String userId : userIds.split(",")) {
            backMessage.setUserId(Integer.parseInt(userId));
            backMessageDao.addBackMessage(backMessage);
        }
    }


    public void changePassword(String password) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        userDao.changePassword(userId, password);
    }


    public boolean checkOldPassword(String password) {
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        return userDao.findUserAllInfoById(userId).getPassword().equals(password);
    }



    public void sendDiscuss(PublicDiscusses publicDiscusses) {
        if (!publicScormDao.isInTimeByPublicId(publicDiscusses.getPublicId())) {
            return;
        }
        int userId = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        publicDiscusses.setUserId(userId);
        publicDiscusses.setSendTime(DateUtil.getSystemDate("yyyy-MM-dd HH:mm:ss"));
        publicDiscussesDao.addPublicDiscusses(publicDiscusses);
    }


    public List<PublicDiscusses> getPublicDiscusses(PublicDiscusses publicDiscusses) {
        if (publicScormDao.isInTimeByPublicId(publicDiscusses.getPublicId())) {
            return publicDiscussesDao.getInlineDiscussesByPublicIdAndDiscussId(publicDiscusses);
        }
        return new ArrayList<PublicDiscusses>();
    }


    public Page<PublicDiscusses> listAllPublicDiscuss(PageParameter pageParameter, PublicDiscusses publicDiscusses) {
        return publicDiscussesDao.listAllPublicDiscuss(pageParameter, publicDiscusses);
    }


    public void delDiscuss(int discussId) {
        publicDiscussesDao.delDiscussByDiscussId(discussId);
    }


    public Page<UserQuestion> listAllQuestion(PageParameter pageParameter, UserQuestion userQuestion) {
        return userQuestionDao.listAllQuestion(pageParameter, userQuestion);
    }


    public void delQuestion(int questionId) {
        userQuestionDao.delQuestionByQuestionId(questionId);
    }


    public void lookQuestionInfo(HttpServletRequest request, int questionId) {
        UserQuestion userQuestion = userQuestionDao.getQuestionInfoByQuestionId(questionId);
        User askUser = userDao.findUserAllInfoById(userQuestion.getAskUserId());
        User answerUser = userDao.findUserAllInfoById(userQuestion.getAnswerUserId());
        request.setAttribute("question", userQuestion);
        request.setAttribute("askUser", askUser);
        request.setAttribute("answerUser", answerUser);
    }


    public void getUserAdminInfo(HttpServletRequest request, int userId) {
        request.setAttribute("user", userDao.findUserAllInfoById(userId));
        request.setAttribute("labels", labelDao.getLabelsByUserId(userId));
        request.setAttribute("upScorms", scormDao.getAllUpScormInfoByUserId(userId));
        request.setAttribute("registerScorms", scormDao.findRegisterScormByUserId(userId));
        request.setAttribute("answerQuestion", userQuestionDao.getUserAnsWerQuestionsByAnswerUserId(userId));
        request.setAttribute("askQuestion", userQuestionDao.getUserAskQuestionsByAnswerUserId(userId));
    }


    public Page getAnnouncementPageList(PageParameter pageParameter, BackAnnouncement backAnnouncement) {
        Page<BackAnnouncement> backAnnouncementPage = backAnnouncementDao.getAnnouncementPageList(pageParameter, backAnnouncement);
        return backAnnouncementPage;
    }


    public void addAnnouncement(BackAnnouncement backAnnouncement) {
        backAnnouncement.setAdminId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        backAnnouncement.setDate(DateUtil.getCurrentTimestamp().toString().substring(0, 16));
        backAnnouncement.setState(DictConstant.NO_USE);
        backAnnouncementDao.addBackAnnouncement(backAnnouncement);
    }


    public void delAnnouncement(int announcementId) {
        backAnnouncementDao.delAnnouncementById(announcementId);
    }


    public BackAnnouncement getAnnouncementInfo(int announcementId) {
        BackAnnouncement backAnnouncement = backAnnouncementDao.getAnnouncementById(announcementId);
        return backAnnouncement;
    }


    public void editAnnouncement(BackAnnouncement backAnnouncement) {
        backAnnouncement.setAdminId(userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        backAnnouncementDao.editAnnouncement(backAnnouncement);
    }


    public Boolean forgetChangePassword(int userId, String password, String key) {
        if (changePasswordDao.findInUseItemByUserIdAndKey(userId, key).size() < 1) {
            return false;
        }
        userDao.changePassword(userId, password);
        changePasswordDao.changeToNoUseStateByUserId(userId);
        return true;
    }


    public void cancelSendAnnouncement(int announcementId) {
        backAnnouncementDao.cancelSendAnnouncement(announcementId);
    }


    public void sendAnnouncement(int announcementId) {
        backAnnouncementDao.setOtherAnnouncementNoUse();
        backAnnouncementDao.sendAnnouncement(announcementId);
    }


    public int addCalendarEvent(int userId, CalendarEvent calendarEvent) {
        calendarEvent.setUserId(userId);
        return userDao.addCalendarEvent(calendarEvent);
    }


    public List<CalendarEvent> getAllCalendarEventsByUserId(int userId) {
        return userDao.getAllCalendarEventsByUserId(userId);
    }


    public void delCalendarEventByCalendarId(int calendarId) {
        userDao.delCalendarEventByCalendarId(calendarId);
    }
}