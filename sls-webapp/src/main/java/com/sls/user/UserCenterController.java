package com.sls.user;

import com.sls.scorm.entity.Scorm;
import com.sls.scorm.service.ScormService;
import com.sls.system.service.LabelService;
import com.sls.user.entity.CalendarEvent;
import com.sls.user.entity.User;
import com.sls.user.entity.UserQuestion;
import com.sls.user.service.UserCenterService;
import com.sls.user.service.UserService;
import com.sls.util.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
@RequestMapping("/user/center/")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScormService scormService;

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "userCenterDo", method = {RequestMethod.GET})
    public String userCenter(HttpServletRequest request, @RequestParam("module") int module) {
        userCenterService.toUserCenter(request);
        userCenterService.selectModule(request, module);
        return "scormfront/usercenter/userCenter";
    }

    //个人中心  首页
    @RequestMapping(value = "userCenterInfo", method = {RequestMethod.GET})
    public String userCenterInfo(HttpServletRequest request) {
        userService.getUserNextLevelNameByScore(request);
        scormService.findRecommendScorm(request);
        scormService.findRegisterScorm(request);
        request.setAttribute("userPeiCharts", userService.getPieCharts(-1));
        return "scormfront/usercenter/userCenterInfo";
    }

    //个人中心  上传课件
    @RequestMapping(value = "upScormDo", method = {RequestMethod.GET})
    public String upScormDo(HttpServletRequest request) {
        labelService.getAllLabel(request);
        scormService.getUpScormGroupsByUserId(request);
        return "scormfront/usercenter/upScorm";
    }

    @RequestMapping(value = "upScorm", method = {RequestMethod.POST})
    public String upScorm(HttpServletRequest request, Scorm scorm, @RequestParam("scormLabelList") String scormLabelList, @RequestParam("groupId") String groupId) throws ServletException, IOException, ParserConfigurationException, SAXException,
            XPathExpressionException {
        int scormId = scormService.upScorm(request, "upScorm", "upImg", scorm, Integer.parseInt(groupId));
        labelService.editScormLabelList(scormLabelList.trim(), scormId);
        labelService.getAllLabel(request);
        return "scormfront/usercenter/upScorm";
    }

    //个人中心  个人资料
    @RequestMapping(value = "userInfoDo", method = {RequestMethod.GET})
    public String userInfoDo(HttpServletRequest request) {
        getUserInfo(request);
        return "scormfront/usercenter/userInfo";
    }

    @RequestMapping(value = "editUserInfo", method = {RequestMethod.POST})
    public String upHeadImg(HttpSession session, HttpServletRequest request, User user, @RequestParam("myLabelList") String myLabelList) throws ServletException, IOException, ParserConfigurationException, SAXException,
            XPathExpressionException {
        userService.editUser(session, request, user);
        labelService.editUserLabelList(myLabelList.trim());
        userService.upHeadImg(session, request, "upImg");
        request.setAttribute("result", "修改成功");
        getUserInfo(request);
        return "scormfront/usercenter/userInfo";
    }

    public void getUserInfo(HttpServletRequest request) {
        userCenterService.toUserInfo(request);
        labelService.getAllUserLabel(request);
        labelService.getLabelsByLoginUserId(request);
    }

    //个人中心  已注册课件
    @RequestMapping(value = "registerScormDo", method = {RequestMethod.GET})
    public String registerScormDo(HttpServletRequest request) {
        userCenterService.getAllRegisterScormInfo(request);
        return "scormfront/usercenter/myscorm/registerScorm";
    }

    //个人中心  已收藏课件
    @RequestMapping(value = "collectScormDo", method = {RequestMethod.GET})
    public String collectScormDo(HttpServletRequest request) {
        userCenterService.getAllCollectScormInfo(request);
        return "scormfront/usercenter/myscorm/collectScorm";
    }

    //个人中心  我的上传
    @RequestMapping(value = "myUpScormsDo", method = {RequestMethod.GET})
    public String myUpScormsDo(HttpServletRequest request) {
        userCenterService.getAllUpScormInfo(request);
        return "scormfront/usercenter/myscorm/myUpScorms";
    }

    //个人中心  笔记本
    @RequestMapping(value = "scormNotesDo", method = {RequestMethod.GET})
    public String scormNotesDo(HttpServletRequest request) {
        userCenterService.getAllRegisterScormInfo(request);
        return "scormfront/usercenter/note/scormNotes";
    }

    //个人中心  关注列表
    @RequestMapping(value = "userAttentionDo", method = {RequestMethod.GET})
    public String userAttentionDo(HttpServletRequest request) {
        request.setAttribute("attentionUsers", userCenterService.getAttentionUsers());
        request.setAttribute("recommendUsers", userService.getNumRecommendUsers(10));
        return "scormfront/usercenter/userAttentions";
    }

    //个人中心  添加问题
    @RequestMapping(value = "addQuestionDo", method = {RequestMethod.GET})
    public String addQuestionDo(HttpServletRequest request) {
        request.setAttribute("attentionUsers", userCenterService.getAttentionUsers());
        return "scormfront/usercenter/question/addQuestion";
    }

    //个人中心  我的提问
    @RequestMapping(value = "askQuestionsDo", method = {RequestMethod.GET})
    public String askQuestionsDo(HttpServletRequest request) {
        userCenterService.getAskQuestions(request);
        return "scormfront/usercenter/question/askQuestions";
    }

    //个人中心  提问我的
    @RequestMapping(value = "askUserQuestionsDo", method = {RequestMethod.GET})
    public String askUserQuestionsDo(HttpServletRequest request) {
        userCenterService.getUserQuestions(request);
        return "scormfront/usercenter/question/askUserQuestions";
    }

    //个人中心  笔记本
    @RequestMapping(value = "notesDo", method = {RequestMethod.GET})
    public String notesDo(HttpServletRequest request, @RequestParam("scormId") String scormId) {
        scormService.getAllStudyNotesByScormIdAndUserId(Integer.parseInt(scormId), request);
        return "scormfront/usercenter/note/notes";
    }

    @RequestMapping(value = "delNote", method = {RequestMethod.POST})
    @ResponseBody
    public void delNote(@RequestParam("noteId") int noteId) {
        scormService.delNote(noteId);
    }

    //个人中心  添加笔记
    @RequestMapping(value = "addNoteDo", method = {RequestMethod.GET})
    public String addNoteDo(HttpServletRequest request) {
        userCenterService.getAllRegisterScormInfo(request);
        return "scormfront/usercenter/note/addNote";
    }


    //个人中心  评价课件
    @RequestMapping(value = "evaluateScormDo", method = {RequestMethod.GET})
    public String evaluateScormDo(HttpServletRequest request) {
        userCenterService.getAllRegisterScormInfo(request);
        return "scormfront/usercenter/evaluteScorm";
    }

    //个人中心  日程表
    @RequestMapping(value = "calendarDo", method = {RequestMethod.GET})
    public String calendarDo(HttpServletRequest request) {
        return "scormfront/usercenter/calendar";
    }

    @RequestMapping(value = "lookQuestionInfoAsk", method = {RequestMethod.GET})
    public String lookQuestionInfoAsk(HttpServletRequest request, HttpSession session, @RequestParam("questionId") int questionId) {
        userCenterService.getQuestionInfoAsk(request, session, questionId);
        return "scormfront/usercenter/question/questionInfoAsk";
    }

    @RequestMapping(value = "changeQuestionAskContent", method = {RequestMethod.POST})
    @ResponseBody
    public void changeQuestionAskContent(UserQuestion userQuestion) {
        userCenterService.changeQuestionAskContent(userQuestion);
    }

    @RequestMapping(value = "lookQuestionInfoAnswer", method = {RequestMethod.GET})
    public String lookQuestionInfoAnswer(HttpServletRequest request, HttpSession session, @RequestParam("questionId") int questionId) {
        userCenterService.getQuestionInfoAnswer(request, session, questionId);
        return "scormfront/usercenter/question/questionInfoAnswer";
    }

    @RequestMapping(value = "changeQuestionAnswerContent", method = {RequestMethod.POST})
    @ResponseBody
    public void changeQuestionAnswerContent(UserQuestion userQuestion) {
        userCenterService.changeQuestionAnswerContent(userQuestion);
    }

    @RequestMapping(value = "cancelNewAskByQuestionId", method = {RequestMethod.POST})
    @ResponseBody
    public void cancelNewAskByQuestionId(@RequestParam("questionId") int questionId) {
        userCenterService.cancelNewAskByQuestionId(questionId);
    }

    @RequestMapping(value = "cancelNewAnswerByQuestionId", method = {RequestMethod.POST})
    @ResponseBody
    public void cancelNewAnswerByQuestionId(@RequestParam("questionId") int questionId) {
        userCenterService.cancelNewAnswerByQuestionId(questionId);
    }

    @RequestMapping(value = "getRegisterScorms", method = {RequestMethod.POST})
    @ResponseBody
    public List<Scorm> getRegisterScorms() {
        int userId = userService.getUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        return scormService.getRegisterScormsByUserId(userId);
    }

    @RequestMapping(value = "getCalendarEvents", method = {RequestMethod.POST})
    @ResponseBody
    public List<CalendarEvent> getCalendarEvents() {
        int userId = userService.getUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId();
        return userService.getAllCalendarEventsByUserId(userId);
    }

    @RequestMapping(value = "addCalendarEvents", method = {RequestMethod.POST})
    @ResponseBody
    public int addCalendarEvents(CalendarEvent calendarEvent) {
        return userService.addCalendarEvent(userService.getUserByLoginName(LoginUserUtil.getLoginName()).get(0).getUserId(), calendarEvent);
    }

    @RequestMapping(value = "delCalendarEvent", method = {RequestMethod.POST})
    @ResponseBody
    public void delCalendarEvent(@RequestParam("calendarId") int calendarId) {
        userService.delCalendarEventByCalendarId(calendarId);
    }

    @RequestMapping(value = "studyRecord", method = {RequestMethod.GET})
    public String studyRecord(HttpServletRequest request, @RequestParam("scormId") int scormId) {
        userCenterService.getRecordInfo(request, scormId);
        return "scormfront/usercenter/myscorm/scormRecord";
    }
}
