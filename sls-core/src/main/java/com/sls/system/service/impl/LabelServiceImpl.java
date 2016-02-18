package com.sls.system.service.impl;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.dao.LabelDao;
import com.sls.system.entity.Label;
import com.sls.system.service.DictService;
import com.sls.system.service.LabelService;
import com.sls.user.dao.UserDao;
import com.sls.util.DictConstant;
import com.sls.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Service("tagService")
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private UserDao userDao;

    public void getAllUserLabel(HttpServletRequest request) {
        request.setAttribute("labelList", labelDao.getAllUserCanSelectLabels(getUserId()));
    }

    private int getUserId() {
        return userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
    }

    public void editUserLabelList(String myLabelList) {
        int userId = getUserId();
        Label label = new Label();
        label.setUserId(userId);
        labelDao.delAllUserLabelsByUserId(userId);
        if (("").equals(myLabelList)) {
            return;
        }
        String labelIdList[] = myLabelList.split(",");
        for (String labelId : labelIdList) {
            label.setLabelId(Integer.parseInt(labelId));
            labelDao.addUserLabel(label);
        }
    }

    public void getLabelsByLoginUserId(HttpServletRequest request) {
        List<Label> myLabelList = labelDao.getLabelsByUserId(getUserId());
        request.setAttribute("myLabelList", myLabelList.size() == 0 ? new LinkedList<Label>() : myLabelList);
    }

    public List<Label> getLabelsByUserId(int userId) {
        return labelDao.getLabelsByUserId(userId);
    }

    public void getAllLabel(HttpServletRequest request) {
        request.setAttribute("labelList", labelDao.getAllLabel());
    }

    public void editScormLabelList(String scormLabelList, int scormId) {
        if (scormId == DictConstant.VOID_VALUE || scormLabelList.equals("")) {
            return;
        }
        Label label = new Label();
        label.setScormId(scormId);
        String labelIdList[] = scormLabelList.split(",");
        for (String labelId : labelIdList) {
            label.setLabelId(Integer.parseInt(labelId));
            labelDao.addScormLabel(label);
        }
    }

    public Page<Label> getAllLabelPageList(PageParameter pageParameter, Label label) {
        return labelDao.getAllLabelPageList(pageParameter, label);
    }

    public boolean checkRepeatLabelName(String labelName, String oldLabelName) {
        Boolean hasLabelName = labelDao.checkRepeatLabelName(labelName);
        return !(hasLabelName && !oldLabelName.equals(labelName));
    }

    public void addLabel(Label label) {
        labelDao.addLabel(label);
    }

    public void findLabelById(int labelId, HttpServletRequest request) {
        request.setAttribute("label", labelDao.findLabelById(labelId));
    }

    public void editLabel(Label label) {
        labelDao.editLabel(label);
    }

    public void delLabel(String labelId) {
        labelDao.delLabelByLabelId(labelId);
    }
}
