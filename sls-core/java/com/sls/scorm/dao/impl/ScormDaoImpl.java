package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.dao.ScormDao;
import com.sls.scorm.entity.Sco;
import com.sls.scorm.entity.Scorm;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("scormDao")
public class ScormDaoImpl extends PageDao implements ScormDao {

    @Override
    public int addScorm(Scorm scorm) {
        String sql = "INSERT INTO ss_scorm(scorm_name,register_sum,score,recommend_level,total_time,pass_date,img_path,description,upload_user_id,upload_date,in_use,complete_way) " +
                "VALUES(:scormName,:registerSum,:score,:recommendLevel,:totalTime,:passDate,:imgPath,:description,:uploadUserId,:uploadDate,:inUse,:completeWay)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(scorm), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addVisitSum(int scormId) {
        String sql = "UPDATE ss_scorm SET register_sum=register_sum+1 WHERE scorm_id=?";
        getJdbcTemplate().update(sql, scormId);
    }

    @Override
    public Scorm findScormInfoByScormId(int scormId) {
        String sql = "SELECT * FROM ss_scorm WHERE scorm_id = ?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), scormId);
    }

    @Override
    public boolean checkNotHasRegister(int scormId, int userId) {
        String sql = "SELECT * FROM luss_scorm_sco WHERE scorm_id = ? AND user_id = ? ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Sco>(Sco.class), scormId, userId).isEmpty();
    }

    @Override
    public void changeTotalTimeByScormId(int scormId, String totalTime) {
        String sql = "UPDATE ss_scorm SET total_time=? WHERE scorm_id=?";
        getJdbcTemplate().update(sql, totalTime, scormId);
    }

    @Override
    public Page<Scorm> listNotAuditScormPageList(PageParameter pageParameter, Scorm scorm) {
        StringBuilder sql = new StringBuilder("SELECT ss_scorm.*,us_user_info.user_name AS showUploadUserId FROM ss_scorm ,us_user_info WHERE us_user_info.user_id=ss_scorm.upload_user_id AND ss_scorm.in_use=" + DictConstant.NO_USE);
        if (scorm.getScormName() != null && !scorm.getScormName().equals("")) {
            sql.append(" AND ss_scorm.scorm_name like '%").append(scorm.getScormName()).append("%'");
        }
        if (scorm.getShowUploadUserId() != null && !scorm.getShowUploadUserId().equals("")) {
            sql.append(" AND us_user_info.user_name like '%").append(scorm.getShowUploadUserId()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(scorm), new BeanPropertyRowMapper<Scorm>(Scorm.class));
    }

    @Override
    public Page<Scorm> listAuditScormPageList(PageParameter pageParameter, Scorm scorm) {
        StringBuilder sql = new StringBuilder("SELECT * FROM ss_scorm WHERE in_use =" + DictConstant.IN_USE);
        if (scorm.getScormName() != null && !scorm.getScormName().equals("")) {
            sql.append(" AND scorm_name like '%").append(scorm.getScormName()).append("%'");
        }
        return queryForPage(pageParameter, sql.toString(), new BeanPropertySqlParameterSource(scorm), new BeanPropertyRowMapper<Scorm>(Scorm.class));
    }

    @Override
    public void changeScormInUse(int scormId, int isUse, String date) {
        String sql = "UPDATE ss_scorm SET in_use=?, pass_date=?  WHERE scorm_id=?";
        getJdbcTemplate().update(sql, isUse, date, scormId);
    }

    @Override
    public void changeScormRecommend(int scormId, int recommend) {
        String sql = "UPDATE ss_scorm SET recommend_level=? WHERE scorm_id=?";
        getJdbcTemplate().update(sql, recommend, scormId);
    }

    @Override
    public void changScormCompleteWayByScormId(int scormId, int completeWay) {
        String sql = "UPDATE ss_scorm SET complete_way=? WHERE scorm_id=?";
        getJdbcTemplate().update(sql, completeWay, scormId);
    }

    @Override
    public void updateScormScoreByScormId(int scormId) {
        String sql = "UPDATE ss_scorm SET score=(SELECT AVG(score) FROM luss_scorm_summarize WHERE score!=0 AND scorm_id=?) WHERE scorm_id=?";
        getJdbcTemplate().update(sql, scormId, scormId);
    }

    @Override
    public List<Scorm> getAllRegisterScormInfoByUserId(int userId) {
        String sql = "SELECT b.* , a.`complete_date` FROM luss_scorm_summarize a, ss_scorm b WHERE a.`scorm_id`=b.`scorm_id` AND a.`user_id` = ? AND b.in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> getAllCollectScormInfoByUserId(int userId) {
        String sql = "SELECT b.* , a.`collect_date` FROM luss_user_collect a, ss_scorm b WHERE a.`scorm_id`=b.`scorm_id` AND a.`user_id` = ? AND b.in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> getAllUpScormInfoByUserId(int userId) {
        String sql = "SELECT * FROM ss_scorm WHERE upload_user_id = ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId);
    }

    @Override
    public List<Scorm> getInUseUpScormInfoByUserId(int userId) {
        String sql = "SELECT * FROM ss_scorm WHERE upload_user_id = ? AND in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> indexFindTopScormByFieldName(String fieldName, int num) {
        String sql = "SELECT * FROM ss_scorm WHERE in_use = ? ORDER BY " + fieldName + " DESC LIMIT  ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE, num);
    }

    @Override
    public List<Scorm> findRecommendScormByUserLabel(int userId) {
        String sql = "SELECT * FROM ss_scorm WHERE scorm_id IN (SELECT DISTINCT scorm_id  FROM ss_scorm_label WHERE label_id IN " +
                "(SELECT label_id FROM us_user_label WHERE user_id=?)) AND in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> findRegisterScormByUserId(int userId) {
        String sql = "SELECT * FROM ss_scorm a,luss_scorm_summarize b WHERE a.scorm_id=b.scorm_id AND b.user_id=? AND a.in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), userId, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> queryScormByFieldName(String info, String fieldName) {
        String sql = "SELECT a.*,COUNT(TYPE) AS chapterNum FROM ss_scorm a,luss_scorm_sco b WHERE a.scorm_id=b.scorm_id AND b.user_id=? AND b.type='item' " +
                "AND a.in_use=? AND a." + fieldName + " LIKE '%" + info.trim() + "%' GROUP BY a.scorm_id";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.VOID_VALUE, DictConstant.IN_USE);
    }

    @Override
    public String findCompleteRateByScormId(int scormId) {
        String sql = "SELECT (SELECT COUNT(*) FROM luss_scorm_summarize WHERE scorm_id=? AND user_id!=? AND complete_date!='')/COUNT(*) " +
                "FROM luss_scorm_summarize WHERE scorm_id=? AND user_id!=?";
        return getJdbcTemplate().queryForObject(sql, (String.class), scormId, DictConstant.VOID_VALUE, scormId, DictConstant.VOID_VALUE);
    }

    @Override
    public List<Scorm> findLatestScormsByNum(int i) {
        String sql = "SELECT * FROM ss_scorm WHERE in_use=? ORDER BY upload_date DESC LIMIT ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE, i);
    }

    @Override
    public List<Scorm> findRecommendIndexScorms() {
        String sql = "SELECT * FROM ss_scorm WHERE in_use=? ORDER BY recommend_level DESC";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE);
    }

    @Override
    public int getAllScormNum() {
        String sql = "SELECT COUNT(*) FROM ss_scorm";
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public int getUseScormNum() {
        String sql = "SELECT COUNT(*) FROM ss_scorm WHERE in_use=?";
        return getJdbcTemplate().queryForObject(sql, Integer.class, DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> sortScormByLabelName(int labelId) {
        StringBuilder sql = new StringBuilder("SELECT a.*,COUNT(b.type) AS chapterNum FROM (SELECT * FROM ss_scorm WHERE in_use=? AND scorm_id IN (SELECT scorm_id FROM ss_scorm_label ");
        if (labelId != 0) {
            sql.append(" WHERE label_id = ").append(labelId);
        }
        sql.append(")) a ,luss_scorm_sco b WHERE a.scorm_id=b.scorm_id GROUP BY a.scorm_id");
        return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE);
    }

    @Override
    public List<Scorm> getAllScormByInUse(int inUse) {
        String sql = "SELECT * FROM ss_scorm WHERE in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE);
    }
}
