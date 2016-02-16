package com.sls.admin;

import com.core.autocomplete.service.AutoCompleteService;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.entity.Label;
import com.sls.system.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

@Controller
@RequestMapping("/admin/label/")
public class LabelController {
    @Autowired
    LabelService labelService;

    @Autowired
    private AutoCompleteService autoCompleteService;

    @RequestMapping(value = "listAllLabelDo", method = RequestMethod.GET)
    public String listAllLabelDo() {
        return "scormadmin/label/listAllLabelDo";
    }

    @RequestMapping(value = "listAllLabel", method = RequestMethod.POST)
    @ResponseBody
    public Page<Label> listAllLabel(PageParameter pageParameter, Label label) {
        return labelService.getAllLabelPageList(pageParameter, label);
    }

    @RequestMapping(value = "addLabelDo", method = {RequestMethod.GET})
    public String addLabelDo() {
        return "scormadmin/label/addLabelDo";
    }

    @RequestMapping(value = "checkRepeatLabelName", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkRepeatLabelName(@RequestParam("labelName") String labelName,
                                        @RequestParam("oldLabelName") String oldLabelName) {
        return labelService.checkRepeatLabelName(labelName, oldLabelName);
    }

    @RequestMapping(value = "addLabel", method = {RequestMethod.POST})
    @ResponseBody
    public void addLabel(Label label) {
        labelService.addLabel(label);
    }

    @RequestMapping(value = "editLabelDo", method = {RequestMethod.GET})
    public String editLabelDo(@RequestParam("labelId") int labelId, HttpServletRequest request) {
        labelService.findLabelById(labelId, request);
        return "scormadmin/label/editLabelDo";
    }

    @RequestMapping(value = "editLabel", method = {RequestMethod.POST})
    @ResponseBody
    public void editLabel(Label label) {
        labelService.editLabel(label);
    }

    @RequestMapping(value = "delLabel", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delLabel(@RequestParam("labelId") String labelId) throws GeneralSecurityException {
        labelService.delLabel(labelId);
        autoCompleteService.initAutoComplete();
    }
}
