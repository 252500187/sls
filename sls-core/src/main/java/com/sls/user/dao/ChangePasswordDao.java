package com.sls.user.dao;

import com.sls.user.entity.ChangePassword;

import java.util.List;

public interface ChangePasswordDao {

    void addChangePassword(ChangePassword changePassword);

    List<ChangePassword> findInUseItemByUserIdAndKey(int userId, String key);

    void changeToNoUseStateByUserId(int userId);
}
