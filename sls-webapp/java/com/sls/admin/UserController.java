package com.sls.admin;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.PublicDiscusses;
import com.sls.scorm.entity.ScormSummarize;
import com.sls.user.entity.BackAnnouncement;
import com.sls.user.entity.BackMessage;
import com.sls.user.entity.User;
import com.sls.user.entity.UserQuestion;
import com.sls.user.service.UserService;
import com.sls.util.DictConstant;
import com.sls.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
@RequestMapping("/admin/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "adminIndexStatisticDo", method = {RequestMethod.GET})
    public String adminIndexStatisticDo(HttpServletRequest request) {
        userService.adminIndexStatisticInfo(request);
        return "scormadmin/statisticData";
    }

    @RequestMapping(value = "listAllUserDo", method = {RequestMethod.GET})
    public String listAllUserDo(HttpServletRequest request) {
        request.setAttribute("myLoginId", userService.getUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId());
        request.setAttribute("shield", DictConstant.NO_USE);
        return "scormadmin/user/listAllUserDo";
    }

    @RequestMapping(value = "shieldUser", method = RequestMethod.POST)
    @ResponseBody
    public void shieldUser(@RequestParam("id") int id) {
        userService.shieldUser(id);
    }

    @RequestMapping(value = "listAllUser", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllUser(PageParameter pageParameter, User user) {
        return userService.listUserPageList(pageParameter, user);
    }

    @RequestMapping(value = "addUserDo", method = {RequestMethod.GET})
    public String addUserDo() {
        return "scormadmin/user/addUserDo";
    }

    @RequestMapping(value = "addUser", method = {RequestMethod.POST})
    @ResponseBody
    public void addUser(User user) {
        userService.addUser(user);
    }

    @RequestMapping(value = "checkRepeatLoginName", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkRepeatPermissionToken(@RequestParam("loginName") String loginName, @RequestParam("oldName") String oldName) {
        return userService.checkRepeatLoginName(loginName, oldName);
    }

    @RequestMapping(value = "checkRepeatUserName", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkRepeatUserName(@RequestParam("userName") String userName, @RequestParam("oldName") String oldName) {
        return userService.checkRepeatUserName(userName, oldName);
    }

    @RequestMapping(value = "editUserDo", method = {RequestMethod.GET})
    public String editUserDo(HttpServletRequest request, @RequestParam("id") int id) {
        User user = userService.getUserAllInfoById(id);
        request.setAttribute("user", user);
        return "scormadmin/user/editUserDo";
    }

    @RequestMapping(value = "changePassword", method = {RequestMethod.POST})
    @ResponseBody
    public void editUserPassword(@RequestParam("password") String password) {
        userService.changePassword(password);
    }

    @RequestMapping(value = "checkOldPassword", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkOldPassword(@RequestParam("oldPassword") String password) {
        return userService.checkOldPassword(password);
    }

    @RequestMapping(value = "checkNewPassword", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkNewPassword(@RequestParam("newPassword") String newPassword) {
        return !userService.checkOldPassword(newPassword);
    }

    @RequestMapping(value = "listAllDiscussDo", method = {RequestMethod.GET})
    public String listAllDiscussDo() {
        return "scormadmin/check/listAllDiscussDo";
    }

    @RequestMapping(value = "listAllAnnouncementDo", method = {RequestMethod.GET})
    public String listAllAnnouncementDo() {
        return "scormadmin/user/listAllAnnouncementDo";
    }

    @RequestMapping(value = "changePasswordDo", method = {RequestMethod.GET})
    public String changePasswordDo() {
        return "scormadmin/changePassword";
    }

    @RequestMapping(value = "listAllDiscuss", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllDiscuss(PageParameter pageParameter, ScormSummarize scormSummarize) {
        return userService.getDiscussPageList(pageParameter, scormSummarize);
    }

    @RequestMapping(value = "listAllAnnouncement", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllAnnouncement(PageParameter pageParameter, BackAnnouncement backAnnouncement) {
        return userService.getAnnouncementPageList(pageParameter, backAnnouncement);
    }

    @RequestMapping(value = "shieldDiscuss", method = RequestMethod.POST)
    @ResponseBody
    public void shieldDiscuss(ScormSummarize scormSummarize) {
        userService.shieldDiscuss(scormSummarize.getUserId(), scormSummarize.getScormId());
    }

    @RequestMapping(value = "sendUserMessageDo", method = {RequestMethod.GET})
    public String sendUserMessageDo(HttpServletRequest request, @RequestParam("userId") int userId) {
        request.setAttribute("userId", userId);
        return "scormadmin/user/sendUserMessageDo";
    }

    @RequestMapping(value = "addAnnouncementDo", method = {RequestMethod.GET})
    public String addAnnouncementDo() {
        return "scormadmin/user/addAnnouncementDo";
    }

    @RequestMapping(value = "sendUserMessage", method = {RequestMethod.POST})
    @ResponseBody
    public void sendUserMessage(BackMessage backMessage) {
        userService.sendUserMessage(backMessage);
    }

    @RequestMapping(value = "addAnnouncement", method = {RequestMethod.POST})
    @ResponseBody
    public void addAnnouncement(BackAnnouncement backAnnouncement) {
        userService.addAnnouncement(backAnnouncement);
    }

    @RequestMapping(value = "listAllMessageDo", method = {RequestMethod.GET})
    public String listAllMessageDo() {
        return "scormadmin/user/listAllMessageDo";
    }

    @RequestMapping(value = "listAllMessage", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllDiscuss(PageParameter pageParameter, BackMessage backMessage) {
        return userService.getMessagePageList(pageParameter, backMessage);
    }

    @RequestMapping(value = "sendMessageDo", method = {RequestMethod.GET})
    public String sendMessageDo(HttpServletRequest request) {
        request.setAttribute("users", userService.getAllInUseUsers());
        return "scormadmin/user/sendMessageDo";
    }

    @RequestMapping(value = "sendMessage", method = {RequestMethod.POST})
    @ResponseBody
    public void sendMessage(@RequestParam("content") String content, @RequestParam("userIds") String userIds) {
        userService.sendMessage(content, userIds);
    }

    @RequestMapping(value = "delMessage", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delMessage(@RequestParam("messageId") int messageId) {
        userService.delMessage(messageId);
    }

    @RequestMapping(value = "delAnnouncement", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delAnnouncement(@RequestParam("announcementId") int announcementId) {
        userService.delAnnouncement(announcementId);
    }

    @RequestMapping(value = "cancelSendAnnouncement", method = {RequestMethod.POST})
       @ResponseBody
       public void cancelSendAnnouncement(@RequestParam("announcementId") int announcementId) {
        userService.cancelSendAnnouncement(announcementId);
    }

    @RequestMapping(value = "sendAnnouncement", method = {RequestMethod.POST})
    @ResponseBody
    public void sendAnnouncement(@RequestParam("announcementId") int announcementId) {
        userService.sendAnnouncement(announcementId);
    }

    @RequestMapping(value = "editAnnouncement", method = {RequestMethod.POST})
    @ResponseBody
    public void editAnnouncement(BackAnnouncement backAnnouncement) {
        userService.editAnnouncement(backAnnouncement);
    }

    @RequestMapping(value = "lookMessageDo", method = {RequestMethod.GET})
    public String lookMessageDo(HttpServletRequest request, @RequestParam("messageId") int messageId) {
        request.setAttribute("message", userService.getMessageInfo(messageId));
        return "scormadmin/user/oneMessageInfoDo";
    }

    @RequestMapping(value = "editAnnouncementDo", method = {RequestMethod.GET})
    public String editAnnouncementDo(HttpServletRequest request, @RequestParam("announcementId") int announcementId) {
        BackAnnouncement backAnnouncement = userService.getAnnouncementInfo(announcementId);
        request.setAttribute("announcement", backAnnouncement);
        return "scormadmin/user/editAnnouncementDo";
    }

    @RequestMapping(value = "transMessageDo", method = {RequestMethod.GET})
    public String transMessageDo(HttpServletRequest request, @RequestParam("messageId") int messageId) {
        request.setAttribute("users", userService.getAllInUseUsers());
        request.setAttribute("message", userService.getMessageInfo(messageId));
        return "scormadmin/user/transMessageDo";
    }

    @RequestMapping(value = "transMessage", method = {RequestMethod.POST})
    @ResponseBody
    public void transMessage(@RequestParam("messageId") int messageId, @RequestParam("userIds") String userIds) {
        userService.transMessage(messageId, userIds);
    }

    @RequestMapping(value = "listAllPublicDiscussDo", method = {RequestMethod.GET})
    public String listAllPublicDiscussDo() {
        return "scormadmin/check/listAllPublicDiscussDo";
    }

    @RequestMapping(value = "listAllPublicDiscuss", method = {RequestMethod.POST})
    @ResponseBody
    public Page listAllPublicDiscuss(PageParameter pageParameter, PublicDiscusses publicDiscusses) {
        return userService.listAllPublicDiscuss(pageParameter, publicDiscusses);
    }

    @RequestMapping(value = "delDiscuss", method = {RequestMethod.POST})
    @ResponseBody
    public void delDiscuss(@RequestParam("discussId") int discussId) {
        userService.delDiscuss(discussId);
    }

    @RequestMapping(value = "listAllQuestionDo", method = {RequestMethod.GET})
    public String listAllQuestionDo() {
        return "scormadmin/check/listAllQuestionDo";
    }

    @RequestMapping(value = "listAllQuestion", method = {RequestMethod.POST})
    @ResponseBody
    public Page listAllQuestion(PageParameter pageParameter, UserQuestion userQuestion) {
        return userService.listAllQuestion(pageParameter, userQuestion);
    }

    @RequestMapping(value = "delQuestion", method = {RequestMethod.POST})
    @ResponseBody
    public void listAllQuestion(@RequestParam("questionId") int questionId) {
        userService.delQuestion(questionId);
    }

    @RequestMapping(value = "lookQuestion", method = {RequestMethod.GET})
    public String lookQuestion(HttpServletRequest request, @RequestParam("questionId") int questionId) {
        userService.lookQuestionInfo(request, questionId);
        return "scormadmin/check/questionInfo";
    }

    @RequestMapping(value = "userInfo", method = {RequestMethod.GET})
    public String userInfo(HttpServletRequest request, @RequestParam("userId") int userId) {
        userService.getUserAdminInfo(request, userId);
        return "scormadmin/user/userInfo";
    }
}
