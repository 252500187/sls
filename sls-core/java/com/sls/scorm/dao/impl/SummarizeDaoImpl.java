package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.dao.SummarizeDao;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.user.entity.User;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("summarizeDao")
public class SummarizeDaoImpl extends PageDao implements SummarizeDao {

    public void addScormSummarize(ScormSummarize scormSummarize) {
        String sql = "INSERT INTO luss_scorm_summarize(user_id,scorm_id,register_date, score,discuss,grade,discuss_date,complete_date,total_time,last_visit_time) " +
                "VALUES(:userId, :scormId, :registerDate, :score, :discuss, :grade, :discussDate, :completeDate,:totalTime,:lastVisitTime)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(scormSummarize));
    }

    public boolean isCompleteScorm(int scormId, int userId) {
        String sql = "SELECT * FROM luss_scorm_summarize WHERE complete_date != '' AND scorm_id = ? AND user_id = ? ";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), scormId, userId).isEmpty();
    }

    public void changeSummarizeScoreByUserIdAndScormId(ScormSummarize scormSummarize) {
        String sql = "UPDATE luss_scorm_summarize SET score=? WHERE user_id=? AND scorm_id=?";
        getJdbcTemplate().update(sql, scormSummarize.getScore(), scormSummarize.getUserId(), scormSummarize.getScormId());
    }

    public void changeCompleteInfoByScormIdAndUserId(ScormSummarize scormSummarize) {
        String sql = "UPDATE luss_scorm_summarize SET grade=?, complete_date=? WHERE scorm_id=? AND user_id=?";
        getJdbcTemplate().update(sql, scormSummarize.getGrade(), scormSummarize.getCompleteDate(), scormSummarize.getScormId(), scormSummarize.getUserId());
    }

    public List<ScormSummarize> findScormSummarizeByUserIdAndScormId(int userId, int scormId) {
        String sql = "SELECT * FROM luss_scorm_summarize WHERE user_id=? AND scorm_id=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ScormSummarize>(ScormSummarize.class), userId, scormId);
    }

    public List<ScormSummarize> getAllCommentsByScormId(int scormId) {
        String sql = "SELECT summarize.*, userinfo.* FROM luss_scorm_summarize summarize , us_user_info userinfo "
                + "WHERE summarize.user_id = userinfo.user_id AND scorm_id = ? AND summarize.discuss!='' ORDER BY discuss_date DESC";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ScormSummarize>(ScormSummarize.class), scormId);
    }

    public void discussScorm(ScormSummarize scormSummarize) {
        String sql = "UPDATE luss_scorm_summarize SET discuss=?, discuss_date=? WHERE scorm_id=? AND user_id=?";
        getJdbcTemplate().update(sql, scormSummarize.getDiscuss(), scormSummarize.getDiscussDate(), scormSummarize.getScormId(), scormSummarize.getUserId());
    }

    public void changeLastVisitTimeByScormIdAndUserId(ScormSummarize scormSummarize) {
        String sql = "UPDATE luss_scorm_summarize SET last_visit_time=? WHERE scorm_id=? AND user_id=?";
        getJdbcTemplate().update(sql, scormSummarize.getLastVisitTime(), scormSummarize.getScormId(), scormSummarize.getUserId());
    }

    public void changeTotalTimeByScormIdAndUserId(int userId, int scormId, String totalTime) {
        String sql = "UPDATE luss_scorm_summarize SET total_time=? WHERE scorm_id=? AND user_id=?";
        getJdbcTemplate().update(sql, totalTime, scormId, userId);
    }

    public Page<ScormSummarize> findDiscussPageList(PageParameter pageParameter, ScormSummarize scormSummarize) {
        StringBuilder sql = new StringBuilder("SELECT a.*,b.scorm_name,c.login_name FROM luss_scorm_summarize a, ss_scorm b, us_user c WHERE a.scorm_id=b.scorm_id AND a.user_id=c.user_id AND a.discuss!=''");
        if (!("").equals(scormSummarize.getDiscuss())) {
            sql.append(" AND a.discuss like '%").append(scormSummarize.getDiscuss().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(scormSummarize), new BeanPropertyRowMapper<ScormSummarize>(ScormSummarize.class));
    }

    public void shieldDiscuss(int userId, int scormId) {
        String sql = "UPDATE luss_scorm_summarize SET discuss='' WHERE scorm_id=? AND user_id=?";
        getJdbcTemplate().update(sql, scormId, userId);
    }

    public List<User> getAllRegisterUsersByScormId(int scormId) {
        String sql = "SELECT * FROM us_user_info WHERE user_id IN(SELECT user_id FROM us_user WHERE user_id IN (" +
                "SELECT user_id FROM luss_scorm_summarize WHERE scorm_id=?) AND in_use=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), scormId, DictConstant.IN_USE);
    }
}
