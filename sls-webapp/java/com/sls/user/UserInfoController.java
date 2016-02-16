package com.sls.user;

import com.sls.user.entity.User;
import com.sls.user.service.UserService;
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
@RequestMapping("/user/info/")
public class UserInfoController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "userAttentionDeal", method = {RequestMethod.GET})
    @ResponseBody
    public void userAttentionDeal(@RequestParam("userAttentionId") int userAttentionId) {
        userService.userAttentionDeal(userAttentionId);
    }

    @RequestMapping(value = "addUserQuestion", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean addUserQuestion(@RequestParam("questionDescribe") String questionDescribe, @RequestParam("answerUserId") int answerUserId) {
        return userService.addUserQuestion(answerUserId, questionDescribe);
    }

    @RequestMapping(value = "cancelMessage", method = {RequestMethod.POST})
    @ResponseBody
    public void cancelMessage(@RequestParam("messageId") int messageId) {
        userService.cancelMessageByMessageId(messageId);
    }

    @RequestMapping(value = "changePasswordDo", method = {RequestMethod.GET})
    public String changePassword() {
        return "scormfront/changePassword";
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
}
