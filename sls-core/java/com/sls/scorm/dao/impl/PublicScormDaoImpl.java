package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.dao.PublicScormDao;
import com.sls.scorm.entity.PublicScorm;
import com.sls.scorm.entity.Scorm;
import com.sls.util.DateUtil;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("publicScormDao")
public class PublicScormDaoImpl extends PageDao implements PublicScormDao {

    @Override
    public Page<PublicScorm> listAllPublicScormPageList(PageParameter pageParameter, PublicScorm publicScorm) {
        StringBuilder sql = new StringBuilder("SELECT a.*,b.scorm_name FROM ss_public_scorm a,ss_scorm b WHERE a.scorm_id=b.scorm_id");
        if (!("").equals(publicScorm.getStartTime())) {
            sql.append(" AND a.start_time >= " + publicScorm.getStartTime());
        }
        if (!("").equals(publicScorm.getEndTime())) {
            sql.append(" AND a.end_time <= " + publicScorm.getEndTime());
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(publicScorm), new BeanPropertyRowMapper<PublicScorm>(PublicScorm.class));
    }

    @Override
    public void delPublicScormByPublicId(int publicId) {
        String sql = "DELETE FROM ss_public_scorm WHERE public_id=?";
        getJdbcTemplate().update(sql, publicId);
    }

    @Override
    public void addPublicScorm(PublicScorm publicScorm) {
        String sql = "INSERT INTO ss_public_scorm(scorm_id,start_time,end_time,description)VALUES (:scormId,:startTime,:endTime,:description)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(publicScorm));
    }

    @Override
    public List<Scorm> getPublicScorm(int num) {
        String nowTime = DateUtil.getCurrentTimestamp().toString();
        String sql = "SELECT a.*,b.start_time,b.end_time,b.description AS publicDescription FROM" +
                "(SELECT * FROM ss_scorm WHERE scorm_id IN(SELECT scorm_id FROM ss_public_scorm WHERE start_time<=? AND end_time>=?) AND in_use=? LIMIT ?) a ,ss_public_scorm b WHERE a.scorm_id=b.scorm_id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), nowTime, nowTime, DictConstant.IN_USE, num);
    }

    @Override
    public List<PublicScorm> getInTimePublicScormByScormId(int scormId) {
        String nowTime = DateUtil.getCurrentTimestamp().toString();
        String sql = "SELECT * FROM ss_public_scorm WHERE scorm_id=? AND start_time<=? AND end_time>=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PublicScorm>(PublicScorm.class), scormId, nowTime, nowTime);
    }

    @Override
    public Boolean isInTimeByPublicId(int publicId) {
        String nowTime = DateUtil.getCurrentTimestamp().toString();
        String sql = "SELECT * FROM ss_public_scorm WHERE public_id=? AND start_time<=? AND end_time>=?";
        return !getJdbcTemplate().query(sql, new BeanPropertyRowMapper<PublicScorm>(PublicScorm.class), publicId, nowTime, nowTime).isEmpty();
    }
}
