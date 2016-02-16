package com.sls.user.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.dao.UserQuestionDao;
import com.sls.user.entity.UserQuestion;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userQuestionDao")
public class UserQuestionDaoImpl extends PageDao implements UserQuestionDao {

    @Override
    public List<UserQuestion> getUserAnsWerQuestionsByAnswerUserId(int answerUserId) {
        String sql = "SELECT * FROM us_user_question WHERE answer_user_id=? AND new_ask=? AND new_answer=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), answerUserId, DictConstant.NO_USE, DictConstant.NO_USE);
    }

    @Override
    public List<UserQuestion> getUserAskQuestionsByAnswerUserId(int answerUserId) {
        String sql = "SELECT * FROM us_user_question WHERE ask_user_id=? AND new_ask=? AND new_answer=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), answerUserId, DictConstant.NO_USE, DictConstant.NO_USE);
    }

    @Override
    public List<UserQuestion> findNoAnswerQuestions(int answerUserId, int askUserId) {
        String sql = "SELECT * FROM us_user_question WHERE answer_user_id=? AND ask_user_id=? AND answer_content=''";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), answerUserId, askUserId);
    }

    @Override
    public void addUserQuestion(UserQuestion userQuestion) {
        String sql = "INSERT INTO us_user_question(question_id, answer_user_id, ask_user_id,ask_date,ask_content,answer_content,new_ask,new_answer) VALUES(:questionId, :answerUserId, :askUserId,:askDate,:askContent,:answerContent,:newAsk,:newAnswer)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(userQuestion));
    }

    @Override
    public int getNewQuestionNumByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM us_user_question WHERE answer_user_id=? AND new_ask=?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, userId, DictConstant.IN_USE);
    }

    @Override
    public int getNewAnswerNumByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM us_user_question WHERE ask_user_id=? AND new_answer=?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, userId, DictConstant.IN_USE);
    }

    @Override
    public List<UserQuestion> getAskQuestionsByAskUserId(int askUserId) {
        String sql = "SELECT * FROM (SELECT * FROM us_user_question WHERE ask_user_id=?) a LEFT JOIN us_user_info b ON a.answer_user_id=b.user_id ORDER BY ask_date";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), askUserId);
    }

    @Override
    public List<UserQuestion> getUserQuestionsByAskUserId(int answerUserId) {
        String sql = "SELECT * FROM (SELECT * FROM us_user_question WHERE answer_user_id=?) a LEFT JOIN us_user_info b ON a.ask_user_id=b.user_id ORDER BY ask_date";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), answerUserId);
    }

    @Override
    public UserQuestion getQuestionInfoByQuestionId(int questionId) {
        String sql = "SELECT * FROM us_user_question WHERE question_id=?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class), questionId);
    }

    @Override
    public void cancelNewAnswerByQuestionId(int questionId) {
        String sql = "UPDATE us_user_question SET new_answer=? WHERE question_id=?";
        getJdbcTemplate().update(sql, DictConstant.NO_USE, questionId);
    }

    @Override
    public void changeQuestionAskContentByQuestionId(UserQuestion userQuestion) {
        String sql = "UPDATE us_user_question SET ask_content=? WHERE question_id=?";
        getJdbcTemplate().update(sql, userQuestion.getAskContent(), userQuestion.getQuestionId());
    }

    @Override
    public void setNewAskByQuestionId(int questionId) {
        String sql = "UPDATE us_user_question SET new_ask=? WHERE question_id=?";
        getJdbcTemplate().update(sql, DictConstant.IN_USE, questionId);
    }

    @Override
    public void cancelNewAskByQuestionId(int questionId) {
        String sql = "UPDATE us_user_question SET new_ask=? WHERE question_id=?";
        getJdbcTemplate().update(sql, DictConstant.NO_USE, questionId);
    }


    @Override
    public void changeQuestionAnswerContentByQuestionId(UserQuestion userQuestion) {
        String sql = "UPDATE us_user_question SET answer_content=? WHERE question_id=?";
        getJdbcTemplate().update(sql, userQuestion.getAnswerContent(), userQuestion.getQuestionId());
    }

    @Override
    public void setNewAnswerByQuestionId(int questionId) {
        String sql = "UPDATE us_user_question SET new_answer=? WHERE question_id=?";
        getJdbcTemplate().update(sql, DictConstant.IN_USE, questionId);
    }

    @Override
    public Page<UserQuestion> listAllQuestion(PageParameter pageParameter, UserQuestion userQuestion) {
        StringBuilder sql = new StringBuilder("SELECT * FROM us_user_question WHERE 1=1 ");
        if (!("").equals(userQuestion.getAskContent())) {
            sql.append(" AND ask_content like '%").append(userQuestion.getAskContent().trim()).append("%'");
        }
        if (!("").equals(userQuestion.getAnswerContent())) {
            sql.append(" AND answer_content like '%").append(userQuestion.getAnswerContent().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(userQuestion), new BeanPropertyRowMapper<UserQuestion>(UserQuestion.class));
    }

    @Override
    public void delQuestionByQuestionId(int questionId) {
        String sql = "DELETE FROM us_user_question WHERE question_id=?";
        getJdbcTemplate().update(sql, questionId);
    }
}
