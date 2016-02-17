package com.sls.user.dao.impl;

import com.core.page.dao.PageDao;
import com.sls.user.dao.ChangePasswordDao;
import com.sls.user.entity.ChangePassword;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("changePasswordDao")
public class ChangePasswordDaoImpl extends PageDao implements ChangePasswordDao {

    public void addChangePassword(ChangePassword changePassword) {
        String sql = "INSERT INTO us_change_password(user_id, change_key,send_date,state) VALUES(:userId,:changeKey,:sendDate,:state)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(changePassword));
    }

    public List<ChangePassword> findInUseItemByUserIdAndKey(int userId, String key) {
        String sql = "SELECT * FROM us_change_password WHERE user_id=? AND change_key=? AND state=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ChangePassword>(ChangePassword.class), userId, key, DictConstant.IN_USE);
    }

    public void changeToNoUseStateByUserId(int userId) {
        String sql = "UPDATE us_change_password SET state=? WHERE user_id=?";
        getJdbcTemplate().update(sql, DictConstant.NO_USE, userId);
    }
}
