package com.core.autocomplete.dao;

import com.core.autocomplete.entity.AutoCompleteVO;
import com.core.dao.BaseDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * AutoCompleteBaseDao
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
@Repository("autoCompleteBaseDao")
public class AutoCompleteBaseDao extends BaseDao {

    public List<AutoCompleteVO> findAutoCompleteList(String sql) {
        return getNamedParameterJdbcTemplate().query(sql, new BeanPropertyRowMapper<AutoCompleteVO>(AutoCompleteVO.class));
    }

    public List<AutoCompleteVO> findAutoCompleteList(String sql, BeanPropertySqlParameterSource beanPropertySqlParameterSource) {
        return getNamedParameterJdbcTemplate().query(sql,
                beanPropertySqlParameterSource, new BeanPropertyRowMapper<AutoCompleteVO>(AutoCompleteVO.class));
    }

    public List<AutoCompleteVO> findAutoCompleteList(String sql, Map<String, ?> map) {
        return getNamedParameterJdbcTemplate().query(sql, map, new BeanPropertyRowMapper<AutoCompleteVO>(AutoCompleteVO.class));
    }
}
