package com.sls.admin;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.scorm.entity.PublicScorm;
import com.sls.scorm.entity.Scorm;
import com.sls.scorm.service.ScormService;
import com.sls.system.service.LabelService;
import com.sls.util.DateUtil;
import com.sls.util.DictConstant;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

@Controller
@Transactional
@RequestMapping("/admin/scorm/")
public class ScormManageController {

    @Autowired
    private ScormService scormService;

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "listNotAuditScormInfoDo", method = {RequestMethod.GET})
    public String listAllScormInfoDo() {
        return "scormadmin/scorm/listNotAuditScormInfoDo";
    }

    @RequestMapping(value = "listNotAuditScormInfo", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Scorm> listNotAuditScormInfo(PageParameter pageParameter, Scorm scorm) {
        return scormService.listNotAuditScormPageList(pageParameter, scorm);
    }

    @RequestMapping(value = "listAuditScormInfoDo", method = {RequestMethod.GET})
    public String listAuditScormInfoDo() {
        return "scormadmin/scorm/listAuditScormInfoDo";
    }

    @RequestMapping(value = "listAuditScormInfo", method = {RequestMethod.POST})
    @ResponseBody
    public Page<Scorm> listAuditScormInfo(PageParameter pageParameter, Scorm scorm) {
        return scormService.listAuditScormPageList(pageParameter, scorm);
    }

    @RequestMapping(value = "scormInfo", method = {RequestMethod.GET})
    public String scormInfo(HttpServletRequest request, @RequestParam("scormId") String scormId) {
        scormService.checkScormInfo(request, Integer.parseInt(scormId));
        return "scormadmin/scorm/scormInfo";
    }

    @RequestMapping(value = "approveScorm", method = {RequestMethod.POST})
    @ResponseBody
    public void approveScorm(@RequestParam("scormId") String scormId) {
        scormService.changeScormInUse(Integer.parseInt(scormId), DictConstant.IN_USE);
    }

    @RequestMapping(value = "forbiddenScorm", method = {RequestMethod.POST})
    @ResponseBody
    public void forbiddenScorm(@RequestParam("scormId") String scormId) {
        scormService.changeScormInUse(Integer.parseInt(scormId), DictConstant.NO_USE);
    }

    @RequestMapping(value = "changScormRecommend", method = {RequestMethod.POST})
    @ResponseBody
    public String[] changScormRecommend(@RequestParam("scormId") String scormId, @RequestParam("level") String level) {
        String str[] = {scormService.changeScormRecommend(Integer.parseInt(scormId), Integer.parseInt(level))};
        return str;
    }

    @RequestMapping(value = "changScormCompleteWay", method = {RequestMethod.POST})
    @ResponseBody
    public void changScormCompleteWay(@RequestParam("scormId") String scormId, @RequestParam("completeWay") String completeWay) {
        scormService.changScormCompleteWay(Integer.parseInt(scormId), Integer.parseInt(completeWay));
    }

    @RequestMapping(value = "upScormDo", method = {RequestMethod.GET})
    public String upScormDo(HttpServletRequest request) {
        labelService.getAllLabel(request);
        scormService.getUpScormGroupsByUserId(request);
        return "scormadmin/scorm/upScormDo";
    }

    @RequestMapping(value = "upScorm", method = {RequestMethod.POST})
    public String upScorm(HttpServletRequest request, Scorm scorm, @RequestParam("scormLabelList") String scormLabelList, @RequestParam("groupId") String groupId) throws ServletException, IOException, ParserConfigurationException, SAXException,
            XPathExpressionException {
        scorm.setInUse(DictConstant.IN_USE);
        int scormId = scormService.upScorm(request, "upScorm", "upImg", scorm, Integer.parseInt(groupId));
        labelService.editScormLabelList(scormLabelList.trim(), scormId);
        labelService.getAllLabel(request);
        scormService.getUpScormGroupsByUserId(request);
        return "scormadmin/scorm/upScormDo";
    }

    @RequestMapping(value = "listAllPublicScormDo", method = {RequestMethod.GET})
    public String listAllPublicScormDo() {
        return "scormadmin/scorm/listAllPublicScormItemDo";
    }

    @RequestMapping(value = "listAllPublicScorm", method = {RequestMethod.POST})
    @ResponseBody
    public Page<PublicScorm> listAllPublicScorm(PageParameter pageParameter, PublicScorm publicScorm) {
        return scormService.listAllPublicScormPageList(pageParameter, publicScorm);
    }

    @RequestMapping(value = "delPublicScorm", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delPublicScorm(@RequestParam("publicId") int publicId) {
        scormService.delPublicScorm(publicId);
    }

    @RequestMapping(value = "addPublicScormDo", method = {RequestMethod.GET})
    public String addPublicScormDo(HttpServletRequest request) {
        request.setAttribute("scorms", scormService.getAllInUseScorm());
        return "scormadmin/scorm/addPublicScormDo";
    }

    @RequestMapping(value = "addPublicScorm", method = {RequestMethod.POST})
    @ResponseBody
    public void addPublicScorm(PublicScorm publicScorm) {
        scormService.addPublicScorm(publicScorm);
    }
}
