/*
* BaseDao.java
* Created on  2013-10-9 上午10:08
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-9       gaoxinyu    初始版本
*
*/
package com.core.page.dao;

import com.core.dao.BaseDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import java.util.List;
import java.util.Map;

/**
 * PageDao
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class PageDao extends BaseDao {

    protected String getSortSql(String sql, PageParameter pageParameter) {
        if (pageParameter.checkSortIsNotEmpty()) {
            StringBuilder sb = new StringBuilder();
            return sb.append(sql)
                    .append(" ORDER BY ")
                    .append(pageParameter.getSortColumnAfterReplace())
                    .append(" ")
                    .append(pageParameter.getSortDirection())
                    .toString();
        }
        return sql;
    }

    protected String getPageSql(String sql, PageParameter pageParameter) {
        StringBuilder sb = new StringBuilder();
        return sb.append(getSortSql(sql, pageParameter))
                .append(" limit ")
                .append(pageParameter.getPageNumber() * pageParameter.getPageSize())
                .append(",")
                .append(pageParameter.getPageSize())
                .toString();
    }

    private String getTotalPageSql(String sql) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("SELECT COUNT(1) FROM (")
                .append(sql)
                .append(")a");
        return sqlStringBuilder.toString();
    }

    public <T> Page<T> queryForPage(PageParameter parameter, String sql, Map<String, ?> map, RowMapper<T> rowMapper) {
        List<T> queryResultList = getNamedParameterJdbcTemplate().query(getPageSql(sql, parameter), map, rowMapper);
        Integer totalCount = getNamedParameterJdbcTemplate().queryForObject(getTotalPageSql(sql), map, Integer.class);
        return new Page<T>(parameter, totalCount, queryResultList);
    }

    public <T> Page<T> queryForPage(PageParameter parameter, String sql,
                                    BeanPropertySqlParameterSource beanPropertySqlParameterSource, RowMapper<T> rowMapper) {
        List<T> queryResultList = getNamedParameterJdbcTemplate().query(getPageSql(sql, parameter),
                beanPropertySqlParameterSource, rowMapper);
        Integer totalCount = getNamedParameterJdbcTemplate().queryForObject(getTotalPageSql(sql), beanPropertySqlParameterSource, Integer.class);
        return new Page<T>(parameter, totalCount, queryResultList);
    }

}
