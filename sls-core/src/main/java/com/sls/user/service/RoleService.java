package com.sls.user.service;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.entity.Role;

public interface RoleService {
    Page<Role> listRolePageList(PageParameter pageParameter, Role role);
}
