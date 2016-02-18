package com.sls.scorm.dao;

import com.sls.scorm.entity.Collect;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.entity.StudyNote;

import java.util.List;

public interface NoteCollectDao {
    boolean checkNotHasCollected(int scormId, int userId);

    void addCollectScorm(Collect collect);

    List<Scorm> findCollectScormByScormIdAndUserId(int scormId, int userId);


    int addStudyNote(StudyNote studyNote);

    List<StudyNote> getAllStudyNotesByScormIdAndUserId(StudyNote studyNote);

    void delNoteByNoteId(int noteId);
}
