package com.sls.system.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.dao.LabelDao;
import com.sls.system.entity.Label;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tagDao")
public class LabelDaoImpl extends PageDao implements LabelDao {
    public StringBuilder getAllLabelSql() {
        String sql = "SELECT  *  FROM  us_label ";
        return new StringBuilder(sql);
    }

    @Override
    public List<Label> getAllUserCanSelectLabels(int userId) {
        String sql = "SELECT * FROM us_label WHERE label_id NOT IN " +
                " (SELECT label_id FROM us_user_label WHERE user_id=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class), userId);
    }

    @Override
    public void delAllUserLabelsByUserId(int userId) {
        String sql = "DELETE FROM us_user_label WHERE user_id = ?";
        getJdbcTemplate().update(sql, userId);
    }

    @Override
    public void addUserLabel(Label label) {
        String sql = "INSERT INTO us_user_label(user_id, label_id)" +
                "VALUES(:userId, :labelId)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(label));
    }

    @Override
    public List<Label> getLabelsByUserId(int userId) {
        String sql = "SELECT * FROM us_label WHERE label_id IN (SELECT label_id FROM us_user_label WHERE user_id=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class), userId);
    }

    @Override
    public List<Label> getAllLabel() {
        String sql = "SELECT * FROM us_label ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class));
    }

    @Override
    public void addScormLabel(Label label) {
        String sql = "INSERT INTO ss_scorm_label(scorm_id, label_id)" +
                "VALUES(:scormId, :labelId)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(label));
    }

    @Override
    public List<Label> getLabelByScormId(int scormId) {
        String sql = "SELECT * FROM us_label WHERE label_id IN (SELECT label_id FROM ss_scorm_label WHERE scorm_id=?)";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class), scormId);
    }

    @Override
    public Page<Label> getAllLabelPageList(PageParameter pageParameter, Label label) {
        StringBuilder sql = getAllLabelSql();
        if (!(0 == label.getLabelId())) {
            sql.append(" AND label_id like '%").append(label.getLabelId()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(label), new BeanPropertyRowMapper<Label>(Label.class));
    }

    @Override
    public boolean checkRepeatLabelName(String labelName) {
        String sql = "select * from us_label where label_name =?";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Label>(Label.class), labelName).isEmpty();
    }

    @Override
    public int addLabel(Label label) {
        String sql = "INSERT INTO us_label(label_name)" +
                "VALUES(:labelName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(label), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public Label findLabelById(int labelId) {
        String sql = "select * from us_label where label_id =?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<Label>(Label.class), labelId);
    }

    @Override
    public void editLabel(Label label) {
        String sql = "UPDATE us_label SET label_name = :labelName WHERE label_id = :labelId";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(label));
    }

    @Override
    public void delLabelByLabelId(String labelId) {
        String sql = "DELETE FROM us_label WHERE label_id = ?";
        getJdbcTemplate().update(sql, labelId);
    }

    @Override
    public int getAllScormLabelNumByLableId(int labelId) {
        String sql = "SELECT COUNT(*) FROM ss_scorm_label WHERE label_id=?";
        return getJdbcTemplate().queryForObject(sql,Integer.class,labelId);
    }
}
