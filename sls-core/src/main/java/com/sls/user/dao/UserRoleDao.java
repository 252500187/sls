package com.sls.user.dao;

import com.sls.user.entity.UserRole;

public interface UserRoleDao {

    int addUserRole(UserRole userRole);

    int editRoleByUserId(UserRole userRole);

    int deleteUserRoleByUserId(String userId);
}
