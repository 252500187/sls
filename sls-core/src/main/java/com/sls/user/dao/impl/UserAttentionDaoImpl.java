package com.sls.user.dao.impl;

import com.core.page.dao.PageDao;
import com.sls.user.dao.UserAttentionDao;
import com.sls.user.entity.User;
import com.sls.user.entity.UserAttention;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userAttentionDao")
public class UserAttentionDaoImpl extends PageDao implements UserAttentionDao {

    public List<UserAttention> findAttention(int userId, int userAttentionId) {
        String sql = "SELECT * FROM us_user_attention WHERE user_id=? AND user_attention_id=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserAttention>(UserAttention.class), userId, userAttentionId);
    }

    public List<UserAttention> findInUseAttention(int userId, int userAttentionId) {
        String sql = "SELECT * FROM us_user_attention WHERE user_id=? AND user_attention_id=? AND state=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserAttention>(UserAttention.class), userId, userAttentionId, DictConstant.IN_USE);
    }

    public void addUserAttention(UserAttention userAttention) {
        String sql = "INSERT INTO us_user_attention(user_id,user_attention_id,new_message,state) VALUES(:userId,:userAttentionId,:newMessage,:state)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(userAttention));
    }

    public void changeUserAttentionState(UserAttention userAttention) {
        String sql = "UPDATE us_user_attention SET state=?, new_message=? WHERE user_id=? AND user_attention_id=?";
        getJdbcTemplate().update(sql, userAttention.getState(), userAttention.getNewMessage(), userAttention.getUserId(), userAttention.getUserAttentionId());
    }

    public List<UserAttention> getAttentionUsersByUserId(int userId) {
        String sql = "SELECT * FROM us_user_attention a LEFT JOIN us_user_info b ON a.user_attention_id=b.user_id WHERE a.user_id=? AND a.state=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<UserAttention>(UserAttention.class), userId, DictConstant.IN_USE);
    }

    public void clearAllNewMessageByUserIdAndAttentionUserId(int userId, int attentionUserId) {
        String sql = "UPDATE us_user_attention SET new_message=0 WHERE user_id=? AND user_attention_id=?";
        getJdbcTemplate().update(sql, userId, attentionUserId);
    }

    public void countNewMessageByAttentionUserId(int attentionUserId) {
        String sql = "UPDATE us_user_attention SET new_message=new_message+1 WHERE user_attention_id=? AND state=?";
        getJdbcTemplate().update(sql, attentionUserId, DictConstant.IN_USE);
    }

    public List<User> getAttentionUserUsersByUserId(int attentionUserId) {
        String sql = "SELECT a.* FROM us_user_info a,us_user b WHERE a.user_id=b.user_id AND b.in_use=? " +
                "AND a.user_id IN (SELECT user_id FROM us_user_attention WHERE user_attention_id=? AND state=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<User>(User.class), DictConstant.IN_USE, attentionUserId, DictConstant.IN_USE);
    }
}
