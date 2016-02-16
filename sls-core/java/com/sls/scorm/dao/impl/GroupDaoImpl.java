package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.sls.scorm.dao.GroupDao;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.ScormGroup;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("groupDao")
public class GroupDaoImpl extends PageDao implements GroupDao {

    @Override
    public List<Scorm> getGroupScormsByScormId(int scormId) {
        String sql = "SELECT a.* FROM ss_scorm a, ss_scorm_group b WHERE a.scorm_id=b.scorm_id AND a.in_use=? " +
                "AND b.group_id=(SELECT group_id FROM ss_scorm_group WHERE scorm_id=?) AND a.scorm_id!=? ";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), DictConstant.IN_USE, scormId, scormId);
    }

    @Override
    public List<ScormGroup> getUpScormGroupsByUserId(int userId) {
        String sql = "SELECT b.*,a.scorm_name FROM ss_scorm a, ss_scorm_group b WHERE a.scorm_id=b.scorm_id AND a.in_use=? AND a.upload_user_id=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<ScormGroup>(ScormGroup.class), DictConstant.IN_USE, userId);
    }

    @Override
    public void addDefaultScormGroup(int scormId) {
        String sql = "INSERT INTO ss_scorm_group (scorm_id) VALUES(?)";
        getJdbcTemplate().update(sql, scormId);
    }

    @Override
    public void addScormGroup(int scormId, int groupId) {
        String sql = "INSERT INTO ss_scorm_group (group_id,scorm_id) VALUES(?,?)";
        getJdbcTemplate().update(sql, groupId, scormId);
    }

    @Override
    public List<Scorm> getGroupScormsByGroupId(int groupId) {
        String sql = "SELECT * FROM ss_scorm WHERE scorm_id IN (SELECT scorm_id FROM ss_scorm_group WHERE group_id=?) AND in_use=?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), groupId, DictConstant.IN_USE);
    }

    @Override
    public double getGroupScoreByGroupId(int groupId) {
        String sql = "SELECT AVG(score) FROM ss_scorm WHERE scorm_id IN (SELECT scorm_id FROM ss_scorm_group WHERE group_id=?)";
        return getJdbcTemplate().queryForObject(sql, Double.class, groupId);
    }

    @Override
    public List<Scorm> findGroupScormsByNum(int num) {
        StringBuilder sql = new StringBuilder("SELECT groups.group_id,groups.groupNum,a.* FROM (SELECT *,COUNT(*) groupNum " +
                " FROM ss_scorm_group GROUP BY group_id) groups,ss_scorm a WHERE groups.scorm_id=a.scorm_id AND groups.groupNum!=1");
        if (num != -1) {
            sql.append(" LIMIT ?");
        }
        return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<Scorm>(Scorm.class), num);
    }

    @Override
    public Boolean notHaveInUseScormCheckByGroupId(int groupId) {
        String sql = "SELECT(SELECT COUNT(*) FROM ss_scorm_group WHERE group_id=?)=" +
                "(SELECT COUNT(*) FROM ss_scorm WHERE scorm_id IN (SELECT scorm_id  FROM ss_scorm_group WHERE group_id=?) AND in_use=?)";
        return getJdbcTemplate().queryForObject(sql, Integer.class, groupId, groupId, DictConstant.IN_USE) == 0;
    }
}
