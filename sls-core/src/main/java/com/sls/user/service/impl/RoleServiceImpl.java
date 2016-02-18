package com.sls.user.service.impl;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.dao.RoleDao;
import com.sls.user.entity.Role;
import com.sls.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Page<Role> listRolePageList(PageParameter pageParameter, Role role) {
        return roleDao.findRolePageList(pageParameter, role);
    }

}
