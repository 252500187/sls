package com.sls.system.service;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.entity.Label;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LabelService {
    void getAllUserLabel(HttpServletRequest request);

    void editUserLabelList(String myLabelList);

    void getLabelsByLoginUserId(HttpServletRequest request);

    List<Label> getLabelsByUserId(int userId);

    void getAllLabel(HttpServletRequest request);

    void editScormLabelList(String scormLabelList,int scormId);

    Page<Label> getAllLabelPageList(PageParameter pageParameter, Label label);

    boolean checkRepeatLabelName(String labelName, String oldLabelName);

    void addLabel(Label label);

    void findLabelById(int labelId, HttpServletRequest request);

    void editLabel(Label label);

    void delLabel(String labelId);
}
