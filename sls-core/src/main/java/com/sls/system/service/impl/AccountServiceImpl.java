package com.sls.system.service.impl;

import com.sls.user.dao.UserDao;
import com.sls.user.entity.User;
import com.sls.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDao userDao;

    public List<User> findUserByLoginName(String loginName) {
        return userDao.findInUseUserByLoginName(loginName);
    }

    public String getRoleAuthorityByLoginName(String loginName) {
        return userDao.findInUseUserByLoginName(loginName).get(0).getAuthority();
    }
}
