package com.sls.user.service;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.PublicDiscusses;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.system.entity.Label;
import com.sls.user.entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public interface UserService {

    Page<User> listUserPageList(PageParameter pageParameter, User user);

    User getUserAllInfoById(int id);

    List<User> getUserByLoginName(String loginName);

    Boolean checkRepeatLoginName(String loginName, String oldName);

    Boolean checkRepeatUserName(String userName, String oldName);

    void addUser(User user);

    void editUser(HttpSession session, HttpServletRequest request, User user);

    void upHeadImg(HttpSession session, HttpServletRequest request, String upImg) throws ServletException, IOException;

    Boolean checkRepeatLoginName(String loginName);

    Boolean checkRepeatEmail(String email);

    void shieldUser(int userId);

    void getUserNextLevelNameByScore(HttpServletRequest request);

    List<Label> getPieCharts(int userId);

    Page<ScormSummarize> getDiscussPageList(PageParameter pageParameter, ScormSummarize scormSummarize);

    void shieldDiscuss(int userId, int scormId);

    void adminIndexStatisticInfo(HttpServletRequest request);

    void getUserOperate(int userInfoId, HttpServletRequest request);

    List<User> getAttentionUserUsersByUserId(int userId);

    void userAttentionDeal(int userAttentionId);

    void clearAllNewMessage(int attentionUserId, HttpSession session);

    List<UserQuestion> getUserAnsWerQuestionsByUserId(int userId);

    Boolean addUserQuestion(int answerUserId, String questionDescribe);

    List<User> getNumRecommendUsers(int num);

    void sendUserMessage(BackMessage backMessage);

    void sendMessage(String content, String userIds);

    void cancelMessageByMessageId(int messageId);

    Page<BackMessage> getMessagePageList(PageParameter pageParameter, BackMessage backMessage);

    List<User> getAllInUseUsers();

    void delMessage(int messageId);

    BackMessage getMessageInfo(int messageId);

    void transMessage(int messageId, String userIds);

    void changePassword(String password);

    boolean checkOldPassword(String password);

    void sendDiscuss(PublicDiscusses publicDiscusses);

    List<PublicDiscusses> getPublicDiscusses(PublicDiscusses publicDiscusses);

    Page<PublicDiscusses> listAllPublicDiscuss(PageParameter pageParameter, PublicDiscusses publicDiscusses);

    void delDiscuss(int discussId);

    Page<UserQuestion> listAllQuestion(PageParameter pageParameter, UserQuestion userQuestion);

    void delQuestion(int questionId);

    void lookQuestionInfo(HttpServletRequest request, int questionId);

    void getUserAdminInfo(HttpServletRequest request, int userId);

    Page getAnnouncementPageList(PageParameter pageParameter, BackAnnouncement backAnnouncement);

    void addAnnouncement(BackAnnouncement backAnnouncement);

    void delAnnouncement(int announcementId);

    BackAnnouncement getAnnouncementInfo(int announcementId);

    void editAnnouncement(BackAnnouncement backAnnouncement);

    Boolean forgetChangePassword(int userId, String password, String key);

    void cancelSendAnnouncement(int announcementId);

    void sendAnnouncement(int announcementId);

    int addCalendarEvent(int userId, CalendarEvent calendarEvent);

    List<CalendarEvent> getAllCalendarEventsByUserId(int userId);

    void delCalendarEventByCalendarId(int calendarId);
}
