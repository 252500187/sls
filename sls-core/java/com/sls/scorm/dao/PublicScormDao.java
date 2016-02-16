package com.sls.scorm.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.PublicScorm;
import com.sls.scorm.entity.Scorm;

import java.util.List;

public interface PublicScormDao {

    Page<PublicScorm> listAllPublicScormPageList(PageParameter pageParameter, PublicScorm publicScorm);

    void delPublicScormByPublicId(int publicId);

    void addPublicScorm(PublicScorm publicScorm);

    List<Scorm> getPublicScorm(int num);

    List<PublicScorm> getInTimePublicScormByScormId(int scormId);

    Boolean isInTimeByPublicId(int publicId);
}
