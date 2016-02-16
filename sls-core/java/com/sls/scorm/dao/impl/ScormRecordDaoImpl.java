package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.sls.scorm.dao.ScormRecordDao;
import com.sls.scorm.entity.ScormRecord;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("scormRecordDao")
public class ScormRecordDaoImpl extends PageDao implements ScormRecordDao {

    @Override
    public List<ScormRecord> getRecordListByUserIdAndScormId(int userId, int scormId) {
        String sql = "SELECT a.*,b.title FROM (SELECT * FROM us_scorm_record WHERE user_id=? AND scorm_id=? " +
                " ORDER BY study_time DESC) a,luss_scorm_sco b WHERE a.sco_id=b.sco_id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ScormRecord>(ScormRecord.class), userId, scormId);
    }

    @Override
    public void addStudyRecord(ScormRecord scormRecord) {
        String sql = "INSERT INTO us_scorm_record (user_id, scorm_id, sco_id, study_time)VALUES (:userId, :scormId, :scoId,:studyTime)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(scormRecord));
    }
}
