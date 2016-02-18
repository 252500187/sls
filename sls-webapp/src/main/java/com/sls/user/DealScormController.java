package com.sls.user;

import com.sls.scorm.entity.ScormSummarize;
import com.sls.scorm.service.ScormService;
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
@RequestMapping("/user/dealScorm/")
public class DealScormController {

    @Autowired
    private ScormService scormService;

    @RequestMapping(value = "evaluateScorm", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean evaluateScorm(ScormSummarize scormSummarize) {
        return scormService.evaluateScorm(scormSummarize);
    }

    @RequestMapping(value = "discussScorm", method = {RequestMethod.POST})
    @ResponseBody
    public void discussScorm(ScormSummarize scormSummarize) {
        scormService.discussScorm(scormSummarize);
    }

    @RequestMapping(value = "review", method = {RequestMethod.GET})
    public String review(@RequestParam("scormId") String scormId, HttpServletRequest request) {
        request.setAttribute("scormId", scormId);
        scormService.findReviewsByScormId(Integer.parseInt(scormId), request);
        return "scormfront/scorm/review";
    }
}
