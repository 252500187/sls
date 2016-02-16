package com.sls.system.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.dao.DictDao;
import com.sls.system.entity.DictDefine;
import com.sls.system.entity.DictValues;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dictDao")
public class DictDaoImpl extends PageDao implements DictDao {

    @Override
    public Page<DictDefine> findDictDefinePageList(PageParameter pageParameter, DictDefine dictDefine) {
        StringBuilder sql = new StringBuilder("SELECT * FROM dict_define where 1=1 ");
        if (dictDefine.getDictName() != null && !dictDefine.getDictName().equals("")) {
            sql.append(" AND dict_name like '%").append(dictDefine.getDictName().trim()).append("%'");
        }
        if (dictDefine.getDictDescription() != null && !dictDefine.getDictDescription().equals("")) {
            sql.append(" AND dict_description like '%").append(dictDefine.getDictDescription().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(dictDefine), new BeanPropertyRowMapper<DictDefine>(DictDefine.class));
    }

    @Override
    public void addDictDefine(DictDefine dictDefine) {
        String sql = "INSERT INTO dict_define(dict_name, dict_description) " +
                "VALUES(:dictName, :dictDescription)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(dictDefine));
    }

    @Override
    public void delDictDefine(String dictName) {
        String sql = "DELETE FROM dict_define WHERE dict_name = ?";
        getJdbcTemplate().update(sql, dictName);
    }

    @Override
    public DictDefine findDictDefineByDictName(String dictName) {
        String sql = "SELECT * FROM dict_define where dict_name=?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<DictDefine>(DictDefine.class), dictName);
    }

    @Override
    public Boolean checkRepeatDictDefineName(String dictName) {
        String sql = "select * from dict_define d where d.dict_name =?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<DictDefine>(DictDefine.class), dictName).isEmpty();
    }

    @Override
    public Page<DictValues> findDictValuesPageList(PageParameter pageParameter, DictValues dictValues) {
        StringBuilder sql = new StringBuilder("select * from dict_values where dict_name=:dictName");
        if (dictValues.getDictValue() != null && !dictValues.getDictValue().equals("")) {
            sql.append(" AND dict_value like '%").append(dictValues.getDictValue().trim()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(dictValues), new BeanPropertyRowMapper<DictValues>(DictValues.class));
    }

    @Override
    public void addDictValues(DictValues dictValues) {
        String sql = "INSERT INTO dict_values(dict_name, dict_code, dict_value) " +
                "VALUES(:dictName, :dictCode, :dictValue)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(dictValues));
    }

    @Override
    public void editDictValues(DictValues dictValues) {
        String sql = "UPDATE dict_values SET dict_value = :dictValue WHERE dict_name = :dictName and dict_code = :dictCode";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(dictValues));
    }

    @Override
    public void delDictValues(DictValues dictValues) {
        String sql = "DELETE FROM dict_values WHERE dict_name =:dictName and dict_code=:dictCode";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(dictValues));
    }

    @Override
    public DictValues findDictValuesByNameAndCode(DictValues dictValues) {
        String sql = "select * from dict_values where dict_name=:dictName and dict_code=:dictCode";
        return getNamedParameterJdbcTemplate().queryForObject(sql, new BeanPropertySqlParameterSource(dictValues), new BeanPropertyRowMapper<DictValues>(DictValues.class));
    }

    @Override
    public DictValues findDictValuesByNameAndValue(DictValues dictValues) {
        String sql = "select * from dict_values where dict_name=:dictName and dict_value=:dictValue";
        return getNamedParameterJdbcTemplate().queryForObject(sql, new BeanPropertySqlParameterSource(dictValues), new BeanPropertyRowMapper<DictValues>(DictValues.class));
    }

    @Override
    public Boolean checkRepeatDictValuesCode(DictValues dictValues) {
        String sql = "select * from dict_values d where d.dict_name=:dictName and d.dict_code =:dictCode";
        return getNamedParameterJdbcTemplate().query(sql, new BeanPropertySqlParameterSource(dictValues), new BeanPropertyRowMapper<DictValues>(DictValues.class)).isEmpty();
    }

    @Override
    public List<DictDefine> findAllDictDefine() {
        String sql = "select * from dict_define";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<DictDefine>(DictDefine.class));
    }

    @Override
    public List<DictValues> findAllDictValues() {
        String sql = "SELECT * FROM dict_values";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<DictValues>(DictValues.class));
    }

    @Override
    public List<DictValues> findDictValuesByDictName(String dictName) {
        String sql = "select * from dict_values where dict_name=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<DictValues>(DictValues.class), dictName);
    }

    @Override
    public void changeDictNameOfDictValues(DictDefine dictDefine) {
        String sql = "UPDATE dict_values SET dict_name=:dictName WHERE dict_name=:oldDictName";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(dictDefine));
    }
}