package com.sls.system.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.entity.Label;

import java.util.List;

public interface LabelDao {
    List<Label> getAllUserCanSelectLabels(int userId);

    void delAllUserLabelsByUserId(int userId);

    void addUserLabel(Label label);

    List<Label> getLabelsByUserId(int userId);

    List<Label> getAllLabel();

    void addScormLabel(Label label);

    List<Label> getLabelByScormId(int scormId);

    Page<Label> getAllLabelPageList(PageParameter pageParameter, Label label);

    boolean checkRepeatLabelName(String labelName);

    int addLabel(Label label);

    Label findLabelById(int labelId);

    void editLabel(Label label);

    void delLabelByLabelId(String labelId);

    int getAllScormLabelNumByLableId(int labelId);
}
