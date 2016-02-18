package com.sls.admin;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.entity.Role;
import com.sls.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Transactional
@RequestMapping("/admin/role/")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "listAllRoleDo", method = {RequestMethod.GET, RequestMethod.POST})
    public String listAllRoleDo() {
        return "scormadmin/role/listAllRoleDo";
    }

    @RequestMapping(value = "listAllRole", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Page<Role> listAllRole(PageParameter pageParameter, Role role) {
        return roleService.listRolePageList(pageParameter, role);
    }
}
