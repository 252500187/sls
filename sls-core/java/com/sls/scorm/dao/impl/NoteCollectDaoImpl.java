package com.sls.scorm.dao.impl;

import com.core.page.dao.PageDao;
import com.sls.scorm.dao.NoteCollectDao;
import com.sls.scorm.entity.Collect;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.StudyNote;
import com.sls.util.DictConstant;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("noteCollectDao")
public class NoteCollectDaoImpl extends PageDao implements NoteCollectDao {
    @Override
    public boolean checkNotHasCollected(int scormId, int userId) {
        String sql = "SELECT * FROM luss_user_collect WHERE scorm_id = ? AND user_id = ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), scormId, userId).isEmpty();
    }

    @Override
    public void addCollectScorm(Collect collect) {
        String sql = "INSERT INTO luss_user_collect (user_id, scorm_id, collect_date)VALUES (:userId, :scormId, :collectDate)";
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(collect));
    }

    @Override
    public List<Scorm> findCollectScormByScormIdAndUserId(int scormId, int userId) {
        String sql = "SELECT * FROM luss_user_collect WHERE user_id = " + userId + " AND scorm_id = ?";
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<Scorm>(Scorm.class), scormId);
    }


    @Override
    public int addStudyNote(StudyNote studyNote) {
        String sql = "INSERT INTO luss_study_note(user_id,scorm_id,sco_id, `date`, note, note_type, img_path) " +
                "VALUES(:userId, :scormId, :scoId,:date, :note, :noteType, :imgPath)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(studyNote), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<StudyNote> getAllStudyNotesByScormIdAndUserId(StudyNote studyNote) {
        StringBuilder sql = new StringBuilder("SELECT lsn.*,ss.`scorm_name` FROM luss_study_note lsn JOIN ss_scorm ss ON lsn.`scorm_id` = ss.`scorm_id` WHERE user_id = ? ");
        if (studyNote.getScormId() != DictConstant.VOID_VALUE) {
            sql.append(" AND lsn.scorm_id = " + studyNote.getScormId());
        }
        if (studyNote.getScormId() == 0) {
            sql.append(" AND lsn.scorm_id = ''");
        }
        sql.append(" ORDER BY note_id DESC ");
        return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<StudyNote>(StudyNote.class), studyNote.getUserId());
    }

    @Override
    public void delNoteByNoteId(int noteId) {
        String sql = "DELETE FROM luss_study_note WHERE note_id=?";
        getJdbcTemplate().update(sql, noteId);
    }
}
