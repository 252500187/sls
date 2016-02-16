package com.sls.scorm.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.PublicDiscusses;

import java.util.List;

public interface PublicDiscussesDao {

    void addPublicDiscusses(PublicDiscusses publicDiscusses);

    List<PublicDiscusses> getInlineDiscussesByPublicIdAndDiscussId(PublicDiscusses publicDiscusses);

    List<PublicDiscusses> getPublicDiscussesByPublicIdAndNowTime(int publicId, String nowTime);

    Page<PublicDiscusses> listAllPublicDiscuss(PageParameter pageParameter, PublicDiscusses publicDiscusses);

    void delDiscussByDiscussId(int discussId);
}
