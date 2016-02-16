package com.sls.user.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.entity.Role;

public interface RoleDao {

    Page<Role> findRolePageList(PageParameter pageParameter, Role role);

    Role findRoleByAuthority(String authority);
}
