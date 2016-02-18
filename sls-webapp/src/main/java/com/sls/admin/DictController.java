package com.sls.admin;

import com.core.autocomplete.service.AutoCompleteService;
import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.system.entity.DictDefine;
import com.sls.system.entity.DictValues;
import com.sls.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

@Controller
@Transactional
@RequestMapping("/admin/dict/")
public class DictController {
    @Autowired
    private DictService dictService;

    @Autowired
    private AutoCompleteService autoCompleteService;

    @RequestMapping(value = "listAllDictDefineDo", method = {RequestMethod.GET, RequestMethod.POST})
    public String listAllDictDefineDo() {
        return "scormadmin/dict/listAllDictDefineDo";
    }

    @RequestMapping(value = "listAllDictDefine", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllDictDefine(PageParameter pageParameter, DictDefine dictDefine) {
        return dictService.findDictDefinePageList(pageParameter, dictDefine);
    }

    @RequestMapping(value = "addDictDefineDo", method = {RequestMethod.GET})
    public String addDictDefineDo() {
        return "scormadmin/dict/addDictDefineDo";
    }

    @RequestMapping(value = "checkRepeatDictDefineName", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkRepeatDictDefineName(DictDefine dictDefine) {
        return dictService.checkRepeatDictDefineName(dictDefine.getDictName(), dictDefine.getOldDictName());
    }

    @RequestMapping(value = "addDictDefine", method = {RequestMethod.POST})
    @ResponseBody
    public void addDictDefine(DictDefine dictDefine) {
        dictService.addDictDefine(dictDefine);
        autoCompleteService.initAutoComplete();
    }

    @RequestMapping(value = "editDictDefineDo", method = {RequestMethod.GET, RequestMethod.POST})
    public String editDictDefineDo(HttpServletRequest request, @RequestParam("dictName") String dictName) throws GeneralSecurityException {
        DictDefine dictDefine = dictService.findDictDefineByDictName(dictName);
        request.setAttribute("dictDefine", dictDefine);
        return "scormadmin/dict/editDictDefineDo";
    }

    @RequestMapping(value = "editDictDefine", method = RequestMethod.POST)
    @ResponseBody
    public void editDictDefine(DictDefine dictDefine, @RequestParam("oldDictName") String oldDictName) throws GeneralSecurityException {
        dictService.editDictDefine(dictDefine, oldDictName);
        autoCompleteService.initAutoComplete();
    }

    @RequestMapping(value = "delDictDefine", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delDictDefine(@RequestParam("dictName") String dictName) throws GeneralSecurityException {
        dictService.delDictDefine(dictName);
        autoCompleteService.initAutoComplete();
    }

    @RequestMapping(value = "listAllDictValuesDo", method = {RequestMethod.GET, RequestMethod.POST})
    public String listAllDictValuesDo(HttpServletRequest request, @RequestParam("dictName") String dictName) {
        request.setAttribute("dictName", dictName);
        return "scormadmin/dict/listAllDictValuesDo";
    }

    @RequestMapping(value = "listAllDictValues", method = RequestMethod.POST)
    @ResponseBody
    public Page listAllDictValues(PageParameter pageParameter, DictValues dictValues) {
        return dictService.findDictValuesPageList(pageParameter, dictValues);
    }

    @RequestMapping(value = "addDictValuesDo", method = {RequestMethod.GET})
    public String addDictValuesDo(HttpServletRequest request, @RequestParam("dictName") String dictName) {
        request.setAttribute("dictName", dictName);
        return "scormadmin/dict/addDictValuesDo";
    }

    @RequestMapping(value = "checkRepeatDictValuesCode", method = {RequestMethod.POST})
    @ResponseBody
    public boolean checkRepeatDictValuesCode(DictValues dictValues) {
        return dictService.checkRepeatDictValuesCode(dictValues);
    }

    @RequestMapping(value = "addDictValues", method = {RequestMethod.POST})
    @ResponseBody
    public void addDictValues(DictValues dictValues) {
        dictService.addDictValues(dictValues);
        autoCompleteService.initAutoComplete();
    }

    @RequestMapping(value = "editDictValuesDo", method = {RequestMethod.GET, RequestMethod.POST})
    public String editDictValuesDo(HttpServletRequest request, DictValues dictValues) throws GeneralSecurityException {
        DictValues dictValuesTemp = dictService.findDictValuesByNameAndCode(dictValues);
        request.setAttribute("dictValuesTemp", dictValuesTemp);
        return "scormadmin/dict/editDictValuesDo";
    }

    @RequestMapping(value = "editDictValues", method = RequestMethod.POST)
    @ResponseBody
    public void editDictValues(DictValues dictValues) throws GeneralSecurityException {
        dictService.editDictValues(dictValues);
        autoCompleteService.initAutoComplete();
    }

    @RequestMapping(value = "delDictValues", method = {RequestMethod.DELETE})
    @ResponseBody
    public void delDictValues(DictValues dictValues) throws GeneralSecurityException {
        dictService.delDictValues(dictValues);
        autoCompleteService.initAutoComplete();
    }
}
