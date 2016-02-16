package com.sls.user.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.system.entity.Label;
import com.sls.user.entity.CalendarEvent;
import com.sls.user.entity.User;
import com.sls.user.entity.UserLevel;

import java.util.List;

public interface UserDao {

    Page<User> findUserPageList(PageParameter pageParameter, User user);

    User findUserAllInfoById(int id);

    List<User> findInUseUserByLoginName(String loginName);

    Boolean checkRepeatLoginName(String loginName);

    Boolean checkRepeatEmail(String email);

    Boolean checkRepeatUserName(String userName);

    int addUser(User user);

    void editUser(User user);

    void upUserPhoto(User user);

    void addUserInfo(User user);

    UserLevel findUserLevelNameByScore(int score);

    int findUploadScormNumByUserId(int userId);

    void editUseState(User user);

    void cancelCollectByUserIdAndScormId(int userId, int scormId);

    List<UserLevel> findUserNextLevelByScore(int score);

    UserLevel findUserNowLevelByScore(int score);

    List<Label> getPieChartsByUserId(int userId);

    void addScore(int score, int userId);

    List<User> getRegisterUsers(int scormId);

    int getAllUserNum();

    int getUseUserNum();

    List<User> getUsersOrderByScoreAndNum(int num);

    List<User> getAllUsersByInUse(int inUse);

    void changePassword(int userId, String password);

    List<User> findInUseUserByEmail(String email);

    int addCalendarEvent(CalendarEvent calendarEvent);

    List<CalendarEvent> getAllCalendarEventsByUserId(int userId);

    void delCalendarEventByCalendarId(int calendarId);

    List<CalendarEvent> getPromptCalendarEvents(int userId, String format);

    List<User> queryUsersByName(String userName);
}
