package com.sls.user.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.dao.BackMessageDao;
import com.sls.user.entity.BackMessage;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("backMessageDao")
public class BackMessageDaoImpl extends PageDao implements BackMessageDao {

    public void addBackMessage(BackMessage backMessage) {
        String sql = "INSERT INTO us_back_message(admin_id, user_id,date,content,state) VALUES( :adminId, :userId,:date,:content,:state)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(backMessage));
    }

    public List<BackMessage> getNewMessageByUserId(int userId) {
        String sql = "SELECT * FROM us_back_message WHERE user_id=? AND state=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<BackMessage>(BackMessage.class), userId, DictConstant.IN_USE);
    }

    public void cancelMessageByMessageId(int messageId) {
        String sql = "UPDATE us_back_message SET state=? WHERE message_id=?";
        getJdbcTemplate().update(sql, DictConstant.NO_USE, messageId);
    }

    public Page<BackMessage> getMessagePageList(PageParameter pageParameter, BackMessage backMessage) {
        StringBuilder sql = new StringBuilder("SELECT * FROM us_back_message WHERE 1=1 ");
        if (!("").equals(backMessage.getDate())) {
            sql.append(" AND date like '%").append(backMessage.getDate().trim()).append("%'");
        }
        if (!("").equals(backMessage.getContent())) {
            sql.append(" AND content like '%").append(backMessage.getContent().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(backMessage), new BeanPropertyRowMapper<BackMessage>(BackMessage.class));
    }

    public void delMessageByMessageId(int messageId) {
        String sql = "DELETE FROM us_back_message WHERE message_id=?";
        getJdbcTemplate().update(sql, messageId);
    }

    public BackMessage getMessageInfoByMessageId(int messageId) {
        String sql = "SELECT * FROM us_back_message WHERE message_id=?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<BackMessage>(BackMessage.class), messageId);
    }
}
