package com.sls.scorm.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.user.entity.User;

import java.util.List;

public interface SummarizeDao {

    void addScormSummarize(ScormSummarize scormSummarize);

    boolean isCompleteScorm(int scormId, int userId);

    void changeSummarizeScoreByUserIdAndScormId(ScormSummarize scormSummarize);

    void changeCompleteInfoByScormIdAndUserId(ScormSummarize scormSummarize);

    List<ScormSummarize> findScormSummarizeByUserIdAndScormId(int userId, int scormId);

    List<ScormSummarize> getAllCommentsByScormId(int scormId);

    void discussScorm(ScormSummarize scormSummarize);

    void changeLastVisitTimeByScormIdAndUserId(ScormSummarize scormSummarize);

    void changeTotalTimeByScormIdAndUserId(int userId, int scormId, String totalTime);

    Page<ScormSummarize> findDiscussPageList(PageParameter pageParameter, ScormSummarize scormSummarize);

    void shieldDiscuss(int userId, int scormId);

    List<User> getAllRegisterUsersByScormId(int scormId);
}
