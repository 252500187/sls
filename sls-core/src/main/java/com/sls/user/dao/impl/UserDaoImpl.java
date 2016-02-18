package com.sls.user.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.entity.Label;
import com.sls.user.dao.UserDao;
import com.sls.user.entity.CalendarEvent;
import com.sls.user.entity.User;
import com.sls.user.entity.UserLevel;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends PageDao implements UserDao {

    private StringBuilder getUserSql() {
        String sql = "SELECT a.*, c.*,d.*, c.role_name show_role_id FROM us_user a,us_user_role b,us_role c,us_user_info d WHERE a.user_id=b.user_id AND b.role_id=c.role_id AND d.user_id=a.user_id ";
        return new StringBuilder(sql);
    }

    private StringBuilder getUserManageSql() {
        String sql = "SELECT   a.user_id,a.login_name,d.user_name,c.role_name,d.register_date,a.in_use, d.email,d.score FROM us_user a, us_user_role b, us_role c, us_user_info d " +
                "WHERE a.user_id = b.user_id AND b.role_id = c.role_id  AND d.user_id = a.user_id";
        return new StringBuilder(sql);
    }

    public Page<User> findUserPageList(PageParameter pageParameter, User user) {
        StringBuilder sql = getUserManageSql();
        sql.append(" AND b.role_id!=1");
        if (!("").equals(user.getLoginName())) {
            sql.append(" AND a.login_name like '%").append(user.getLoginName().trim()).append("%'");
        }
        if (!("").equals(user.getUserName())) {
            sql.append(" AND d.user_name like '%").append(user.getUserName().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(user), new BeanPropertyRowMapper<User>(User.class));
    }

    public User findUserAllInfoById(int id) {
        StringBuilder sql = getUserSql();
        sql.append(" AND a.user_id=?");
        return getJdbcTemplate().queryForObject(sql.toString(), new BeanPropertyRowMapper<User>(User.class), id);
    }

    public List<User> findInUseUserByLoginName(String loginName) {
        StringBuilder sql = getUserSql();
        sql.append(" AND a.in_use = ? AND a.login_name=? ");
        return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<User>(User.class), DictConstant.IN_USE, loginName);
    }

    public Boolean checkRepeatLoginName(String loginName) {
        String sql = "SELECT * FROM us_user WHERE login_name =?";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), loginName).isEmpty();
    }

    public Boolean checkRepeatEmail(String email) {
        String sql = "SELECT * FROM us_user_info WHERE email =?";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), email).isEmpty();
    }

    public Boolean checkRepeatUserName(String userName) {
        String sql = "SELECT * FROM sys_user WHERE user_name =?";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), userName).isEmpty();
    }

    public int addUser(User user) {
        String sql = "INSERT INTO us_user(login_name, password, in_use) VALUES(:loginName, :password, :inUse)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user), keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void editUser(User user) {
        String sql = "UPDATE us_user_info SET user_name = :userName, sex = :sex WHERE user_id = :userId";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user));
    }

    public void upUserPhoto(User user) {
        String sql = "UPDATE us_user_info SET img_url = :imgUrl WHERE user_id = :userId";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user));
    }

    public void addUserInfo(User user) {
        String sql = "INSERT INTO us_user_info(user_id, user_name, register_date, email, score,img_url, sex ) " +
                "VALUES(:userId, :userName, :registerDate, :email, :score,:imgUrl, :sex)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user));
    }

    public UserLevel findUserLevelNameByScore(int score) {
        String sql = "SELECT * FROM us_level WHERE score = (SELECT MAX(score)  FROM us_level WHERE score<=?)";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<UserLevel>(UserLevel.class), score);
    }

    public List<UserLevel> findUserNextLevelByScore(int score) {
        String sql = "SELECT * FROM us_level WHERE score = (SELECT MIN(score)  FROM us_level WHERE score>?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserLevel>(UserLevel.class), score);
    }

    public UserLevel findUserNowLevelByScore(int score) {
        String sql = "SELECT * FROM us_level WHERE score = (SELECT MAX(score)  FROM us_level WHERE score<=?)";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<UserLevel>(UserLevel.class), score);
    }

    public List<Label> getPieChartsByUserId(int userId) {
        String sql = "SELECT a.*, ul.label_name FROM (SELECT COUNT(label_id) AS number,label_id " +
                " FROM  ss_scorm_label WHERE scorm_id IN (SELECT DISTINCT  scorm_id " +
                " FROM luss_scorm_sco WHERE user_id = ?) GROUP BY label_id)a JOIN us_label ul" +
                " ON a.label_id = ul.label_id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class), userId);
    }

    public int findUploadScormNumByUserId(int userId) {
        String sql = "SELECT COUNT(*) AS a FROM ss_scorm WHERE upload_user_id = ? ";
        return getJdbcTemplate().queryForObject(sql, Integer.class, userId);
    }

    public void editUseState(User user) {
        String sql = "UPDATE us_user SET in_use = :inUse WHERE user_id = :userId";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(user));

    }

    public void cancelCollectByUserIdAndScormId(int userId, int scormId) {
        String sql = "DELETE FROM luss_user_collect WHERE user_id = ? AND scorm_id = ?";
        getJdbcTemplate().update(sql, userId, scormId);
    }

    public void addScore(int score, int userId) {
        String sql = "UPDATE us_user_info SET score=score+? WHERE user_id=?";
        getJdbcTemplate().update(sql, score, userId);
    }

    public List<User> getRegisterUsers(int scormId) {
        String sql = "SELECT * FROM us_user_info WHERE user_id IN (SELECT user_id FROM luss_scorm_summarize WHERE scorm_id=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), scormId);
    }

    public int getAllUserNum() {
        String sql = "SELECT COUNT(*) FROM us_user";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    public int getUseUserNum() {
        String sql = "SELECT COUNT(*) FROM us_user WHERE in_use=?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, DictConstant.IN_USE);
    }

    public List<User> getUsersOrderByScoreAndNum(int num) {
        String sql = "SELECT * FROM us_user_info ORDER BY score DESC LIMIT ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), num);
    }

    public List<User> getAllUsersByInUse(int inUse) {
        String sql = "SELECT * FROM us_user_info a LEFT JOIN us_user_role b ON a.user_id=b.user_id " +
                "WHERE b.role_id = (SELECT role_id FROM us_role WHERE authority=?) AND a.user_id IN (SELECT user_id FROM us_user WHERE in_use=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), DictConstant.ROLE_AUTHORITY_USER, inUse);
    }

    public void changePassword(int userId, String password) {
        String sql = "UPDATE us_user SET password = ? WHERE user_id = ?";
        getJdbcTemplate().update(sql, password, userId);
    }

    public List<User> findInUseUserByEmail(String email) {
        String sql = "SELECT a.* FROM us_user_info a,us_user b WHERE a.user_id=b.user_id AND a.email=? AND b.in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), email, DictConstant.IN_USE);
    }

    public int addCalendarEvent(CalendarEvent calendarEvent) {
        String sql = "INSERT INTO us_calendar_events(scorm_id,start_date,end_date, user_id)VALUES(:scormId, :startDate, :endDate, :userId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(calendarEvent), keyHolder);
        return keyHolder.getKey().intValue();
    }

    public List<CalendarEvent> getAllCalendarEventsByUserId(int userId) {
        String sql = "SELECT uce.*,ss.scorm_name FROM us_calendar_events uce JOIN ss_scorm ss ON uce.scorm_id = ss.scorm_id WHERE uce.user_id=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<CalendarEvent>(CalendarEvent.class), userId);
    }

    public void delCalendarEventByCalendarId(int calendarId) {
        String sql = "DELETE FROM us_calendar_events WHERE calendar_id=?";
        getJdbcTemplate().update(sql, calendarId);
    }

    public List<CalendarEvent> getPromptCalendarEvents(int userId, String formatDate) {
        String sql = "SELECT uce.*,ss.scorm_name FROM us_calendar_events uce JOIN ss_scorm ss ON uce.scorm_id = ss.scorm_id WHERE user_id =? AND ? BETWEEN start_date AND end_date\n";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<CalendarEvent>(CalendarEvent.class), userId, formatDate);
    }

    public List<User> queryUsersByName(String userName) {
        String sql = "SELECT * FROM us_user_info WHERE user_name LIKE '%"+userName+"%'";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class));
    }
}