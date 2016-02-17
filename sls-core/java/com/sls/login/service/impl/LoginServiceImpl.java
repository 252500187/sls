package com.sls.login.service.impl;

import com.sls.scorm.dao.GroupDao;
import com.sls.scorm.dao.PublicScormDao;
import com.sls.scorm.dao.ScormDao;
import com.sls.scorm.entity.Scorm;
import com.sls.system.dao.LabelDao;
import com.sls.user.dao.*;
import com.sls.user.entity.*;
import com.sls.login.service.LoginService;
import com.sls.util.DateUtil;
import com.sls.util.DictConstant;
import com.sls.util.LoginUserUtil;
import com.sls.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ChangePasswordDao changePasswordDao;

    @Autowired
    private ScormDao scormDao;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private UserAttentionDao userAttentionDao;

    @Autowired
    private UserQuestionDao userQuestionDao;

    @Autowired
    public BackMessageDao backMessageDao;

    @Autowired
    public BackAnnouncementDao backAnnouncementDao;

    @Autowired
    public PublicScormDao publicScormDao;

    @Autowired
    public GroupDao groupDao;

    public String toIndex(HttpServletRequest request, HttpSession session) {
        String loginName = LoginUserUtil.getLoginName();
        List<User> user = userDao.findInUseUserByLoginName(loginName);
        if (user.size() > 0) {
            session.setAttribute("userId", user.get(0).getUserId());
            session.setAttribute("userName", user.get(0).getUserName());
            session.setAttribute("userImg", user.get(0).getImgUrl());
            if (user.get(0).getRoleId() == roleDao.findRoleByAuthority(DictConstant.ROLE_AUTHORITY_ADMIN).getRoleId()) {
                return "/scormadmin/index";
            }
            setLoginIndexInfo(session, user.get(0).getUserId());
        }
        setIndexInfo(request, session);
        return "/scormfront/index";
    }

    public ModelAndView loginResult(HttpServletRequest request, User user, HttpSession session) {
        ModelAndView modelView = new ModelAndView();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), user.getPassword());
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (Exception e) {
            request.setAttribute("loginResult", "false");
            modelView.addObject("message", "密码错误！");
            modelView.setViewName("/scormfront/login");
        }
        if (currentUser.isAuthenticated()) {
            List<User> userList = userDao.findInUseUserByLoginName(LoginUserUtil.getLoginName());
            session.setAttribute("userId", userList.get(0).getUserId());
            session.setAttribute("userName", userList.get(0).getUserName());
            session.setAttribute("userImg", userList.get(0).getImgUrl());
            modelView.setViewName("/scormadmin/index");
            if (userList.get(0).getRoleId() == roleDao.findRoleByAuthority(DictConstant.ROLE_AUTHORITY_USER).getRoleId()) {
                setLoginIndexInfo(session, userList.get(0).getUserId());
                setIndexInfo(request, session);
                modelView.setViewName("/scormfront/index");
            }
        } else {
            request.setAttribute("loginResult", "false");
            modelView.addObject("message", "密码错误！");
            modelView.setViewName("/scormfront/login");
        }
        return modelView;
    }

    public void setIndexInfo(HttpServletRequest request, HttpSession session) {
        session.setAttribute("labels", labelDao.getAllLabel());
        request.setAttribute("scormSum", scormDao.indexFindTopScormByFieldName("register_sum", 4));
        request.setAttribute("scormScore", scormDao.indexFindTopScormByFieldName("score", 4));
        request.setAttribute("scormTime", scormDao.indexFindTopScormByFieldName("total_time", 4));
        request.setAttribute("scormLevel", scormDao.indexFindTopScormByFieldName("recommend_level", 4));
        request.setAttribute("latestScorms", scormDao.findLatestScormsByNum(10));
        request.setAttribute("recommendIndexScorms", scormDao.findRecommendIndexScorms());
        request.setAttribute("recommendUsers", userDao.getUsersOrderByScoreAndNum(6));
        request.setAttribute("publicScorms", publicScormDao.getPublicScorm(4));
        request.setAttribute("announcements", backAnnouncementDao.getInUseAnnouncement());
        List<Scorm> groups = groupDao.findGroupScormsByNum(10);
        List<Scorm> useGroups = new ArrayList<Scorm>();
        for (Scorm group : groups) {
            if (!groupDao.notHaveInUseScormCheckByGroupId(group.getGroupId())) {
                group.setGroupScore(groupDao.getGroupScoreByGroupId(group.getGroupId()));
                useGroups.add(group);
            }
        }
        request.setAttribute("groupScorms", useGroups);
    }

    public void setLoginIndexInfo(HttpSession session, int userId) {
        List<UserAttention> userAttentionList = userAttentionDao.getAttentionUsersByUserId(userId);
        int messageNum = 0;
        for (UserAttention userAttention : userAttentionList) {
            messageNum += userAttention.getNewMessage();
        }
        session.setAttribute("attentionUsers", userAttentionList);
        session.setAttribute("attentionMessageNum", messageNum);
        //获取新的提问和回答数
        session.setAttribute("questionNum", userQuestionDao.getNewQuestionNumByUserId(userId));
        session.setAttribute("answerNum", userQuestionDao.getNewAnswerNumByUserId(userId));
        //获取后台传送的消息
        session.setAttribute("messages", backMessageDao.getNewMessageByUserId(userId));
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        session.setAttribute("calendarEvents", userDao.getPromptCalendarEvents(userId, dateFormat.format(date)));
    }

    public Boolean sendPasswordEmail(String email, String basePath) {
        List<User> userList = userDao.findInUseUserByEmail(email);
        if (userList.size() < 1) {
            return false;
        }
        String key = StringUtil.generateUUID();
        sendEmail(email, key, basePath, userList.get(0).getUserId());
        addChangePassword(userList.get(0).getUserId(), key);
        return true;
    }

    void sendEmail(String email, String key, String basePath, int userId) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.qq.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("252500187@qq.com");
        mailInfo.setPassword("bblllmukk");
        mailInfo.setFromAddress("252500187@qq.com");//发送方邮箱
        mailInfo.setToAddress(email);//接收方邮箱
        mailInfo.setSubject("SLS学习平台重置密码");
        mailInfo.setContent("亲爱的用户：您好！<br>您收到这封电子邮件是因为您 (也可能是某人冒充您的名义) 需要申请一个新密码。假如这不是您本人所申请，请不用理会这封电子邮件。<br>" +
                "要使用新的密码, 请点击重置密码进行密码重置：" +
                "<a href='" + basePath + "emailChangePassword?userId=" + userId + "&key=" + key + "'>重置密码</a><br/>" +
                "若无法点击，可访问以下链接:<br/>" +
                basePath + "emailChangePassword?userId=" + userId + "&key=" + key);
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendHtmlMail(mailInfo);
    }

    void addChangePassword(int userId, String key) {
        ChangePassword changePassword = new ChangePassword();
        changePassword.setUserId(userId);
        changePassword.setChangeKey(key);
        changePassword.setSendDate(DateUtil.getCurrentTimestamp().toString().substring(0, 19));
        changePassword.setState(DictConstant.IN_USE);
        changePasswordDao.addChangePassword(changePassword);
    }
}
