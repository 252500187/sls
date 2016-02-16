package com.sls.tourist;

import com.sls.scorm.entity.Scorm;
import com.sls.scorm.service.ScormService;
import com.sls.system.service.LabelService;
import com.sls.user.entity.User;
import com.sls.user.service.UserService;
import com.sls.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/tourist/")
public class TouristController {
    @Autowired
    private UserService userService;

    @Autowired
    private ScormService scormService;

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "register", method = {RequestMethod.POST})
    @ResponseBody
    public void register(User user) {
        userService.addUser(user);
    }

    @RequestMapping(value = "checkRepeatLoginName", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkRepeatLoginName(@RequestParam("registerLoginName") String loginName) {
        return userService.checkRepeatLoginName(loginName);
    }

    @RequestMapping(value = "checkRepeatEmail", method = RequestMethod.POST)
    @ResponseBody
    public boolean checkRepeatEmail(@RequestParam("email") String email) {
        return userService.checkRepeatEmail(email);
    }

    @RequestMapping(value = "scormInfo", method = {RequestMethod.GET})
    public String scormInfo(@RequestParam("scormId") int scormId, HttpServletRequest request) {
        scormService.getAllAboutScormInfo(scormId, request);
        scormService.getScormOperate(scormId, request);
        scormService.getSummarizeInfo(scormId, request);
        scormService.getAllStudyNotesByScormIdAndUserId(scormId, request);
        scormService.getAllRegisterUsers(scormId, request);
        return "scormfront/scorm/scormInfo";
    }

    @RequestMapping(value = "findScorm", method = {RequestMethod.GET})
    public String findScorm(@RequestParam("queryInfo") String findInfo, HttpServletRequest request) {
        scormService.findResult(BaseUtil.iso2utf(findInfo), request);
        scormService.findRecommendScorm(request);
        scormService.findRegisterScorm(request);
        return "scormfront/scorm/findResult";
    }

    @RequestMapping(value = "sortScorm", method = {RequestMethod.GET})
    public String sortScorm(@RequestParam("labelId") int labelId, HttpServletRequest request) {
        scormService.sortScorm(labelId, request);
        return "scormfront/scorm/sortResult";
    }

    @RequestMapping(value = "checkValidateCodeYesOrNot", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkValidateCodeYesOrNot(@RequestParam("validateCode") String validateCode, HttpServletRequest request) throws Exception {
        return (request.getSession().getAttribute("validation_code").toString()).equals(validateCode);
    }

    @RequestMapping(value = "validateCode", method = {RequestMethod.GET, RequestMethod.POST})
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codeChars = "0123456789";//验证码的字符集合
        //关闭客户端浏览器的缓冲区。
        response.setHeader("ragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expirse", "0");
        //设置图形大小。
        int width = 65, height = 20;
        //建立图形缓冲区。
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();//获得   Graphics 对象。
        g.setColor(getRandomColor(180, 250));//设置背景色。
        g.fillRect(0, 0, width, height);//填充背景。
        StringBuilder validationCode = new StringBuilder();//用于保存最后的验证码
        String[] fontNames = {"Times New Roman", "Arial"};//用于随机的字体的集合
        Random r = new Random();
        //随机生成3-5个验证码
        for (int i = 0; i < 4; i++) {
            g.setFont(new Font(fontNames[r.nextInt(2)], Font.BOLD, height));
            char codeChar = codeChars.charAt(r.nextInt(codeChars.length()));
            validationCode.append(codeChar);
            g.setColor(getRandomColor(10, 100));
            g.drawString(String.valueOf(codeChar), 16 * i + r.nextInt(7), height - r.nextInt(6));//在图形上输出验证码
        }
        //随机生干扰码
        for (int i = 0; i < 30; i++) {
            g.setColor(getRandomColor(90, 200));
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            g.drawLine(x, y, x + r.nextInt(10), y + r.nextInt(5));
        }

        HttpSession session = request.getSession();//得到HttpSession对象
        session.setAttribute("validation_code", validationCode.toString());//将验证码保存在session中
        g.dispose();//关闭Graphics对象
        OutputStream os = response.getOutputStream();//得到输出流
        ImageIO.write(image, "JPEG", os);//以JPEG格式向客户端发送图形验证码
    }

    private Color getRandomColor(int minColor, int maxColor) {
        Random r = new Random();
        int red = minColor + r.nextInt(maxColor - minColor);
        int green = minColor + r.nextInt(maxColor - minColor);
        int blue = minColor + r.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }

    @RequestMapping(value = "userInfo", method = {RequestMethod.GET})
    public String userInfo(@RequestParam("userId") int userId, HttpServletRequest request, HttpSession session) {
        request.setAttribute("user", userService.getUserAllInfoById(userId));
        request.setAttribute("labels", labelService.getLabelsByUserId(userId));
        request.setAttribute("registerScorms", scormService.getRegisterScormsByUserId(userId));
        request.setAttribute("upScorms", scormService.getUpScormsByUserId(userId));
        request.setAttribute("attentionUsers", userService.getAttentionUserUsersByUserId(userId));
        request.setAttribute("answerQuestions", userService.getUserAnsWerQuestionsByUserId(userId));
        request.setAttribute("userPeiCharts", userService.getPieCharts(userId));
        userService.getUserOperate(userId, request);
        userService.clearAllNewMessage(userId, session);
        return "scormfront/user/userInfo";
    }

    @RequestMapping(value = "groupsScorm", method = {RequestMethod.GET})
    public String groupsScorm(HttpServletRequest request) {
        request.setAttribute("groupsScorm", scormService.findGroupsScorm());
        request.setAttribute("latestScorms", scormService.findLatestScormsByNum(10));
        return "scormfront/scorm/groupscorm/allGroupsScorm";
    }

    @RequestMapping(value = "getGroupScorms", method = {RequestMethod.POST})
    @ResponseBody
    public List<Scorm> getGroupScorms(@RequestParam("groupId") int groupId) {
        return scormService.getGroupScorms(groupId);
    }
}
