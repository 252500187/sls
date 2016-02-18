package com.sls.login.service;

import com.sls.user.entity.User;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {
    String toIndex(HttpServletRequest request, HttpSession session);

    ModelAndView loginResult(HttpServletRequest request, User user, HttpSession session);

    Boolean sendPasswordEmail(String email, String basePath);
}