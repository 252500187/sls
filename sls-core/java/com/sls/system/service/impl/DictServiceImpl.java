package com.sls.system.service.impl;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.util.DictConstant;
import com.sls.system.dao.DictDao;
import com.sls.system.entity.DictDefine;
import com.sls.system.entity.DictValues;
import com.sls.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    public Page<DictDefine> findDictDefinePageList(PageParameter pageParameter, DictDefine dictDefine) {
        return dictDao.findDictDefinePageList(pageParameter, dictDefine);
    }

    public void addDictDefine(DictDefine dictDefine) {
        dictDao.addDictDefine(dictDefine);
    }

    public void editDictDefine(DictDefine dictDefine, String oldDictName) {
        dictDao.delDictDefine(oldDictName);
        dictDao.addDictDefine(dictDefine);
        dictDefine.setOldDictName(oldDictName);
        dictDao.changeDictNameOfDictValues(dictDefine);
    }

    public void delDictDefine(String dictName) {
        dictDao.delDictDefine(dictName);
        List<DictValues> dictValuesList = dictDao.findDictValuesByDictName(dictName);
        for (DictValues dictValues : dictValuesList) {
            dictDao.delDictValues(dictValues);
        }
    }

    public DictDefine findDictDefineByDictName(String dictName) {
        return dictDao.findDictDefineByDictName(dictName);
    }

    public Boolean checkRepeatDictDefineName(String dictName, String oldDictName) {
        Boolean notHasRepeatName = dictDao.checkRepeatDictDefineName(dictName);
        Boolean notEquOldName = !(dictName.equals(oldDictName));
        return !(!notHasRepeatName && notEquOldName);
    }

    public Page<DictValues> findDictValuesPageList(PageParameter pageParameter, DictValues dictValues) {
        return dictDao.findDictValuesPageList(pageParameter, dictValues);
    }

    public void addDictValues(DictValues dictValues) {
        dictDao.addDictValues(dictValues);
    }

    public void editDictValues(DictValues dictValues) {
        dictDao.editDictValues(dictValues);
    }

    public void delDictValues(DictValues dictValues) {
        dictDao.delDictValues(dictValues);
    }

    public DictValues findDictValuesByNameAndCode(DictValues dictValues) {
        return dictDao.findDictValuesByNameAndCode(dictValues);
    }

    public DictValues findDictValuesByNameAndValue(DictValues dictValues) {
        return dictDao.findDictValuesByNameAndValue(dictValues);
    }

    public Boolean checkRepeatDictValuesCode(DictValues dictValues) {
        return dictDao.checkRepeatDictValuesCode(dictValues);
    }

    public List<DictDefine> findAllDictDefine() {
        return dictDao.findAllDictDefine();
    }

    public List<DictValues> findAllDictValues() {
        return dictDao.findAllDictValues();
    }

    public String changeDictCodeToValue(int dictCode, String dictName) {
        if (dictCode == DictConstant.VOID_VALUE) {
            return "";
        }
        DictValues dictValues = new DictValues();
        dictValues.setDictName(dictName);
        dictValues.setDictCode(dictCode);
        return dictDao.findDictValuesByNameAndCode(dictValues).getDictValue();
    }

    public int changeDictValueToCode(String dictValue, String dictName) {
        if ("".equals(dictValue) || dictValue == null) {
            return DictConstant.VOID_VALUE;
        }
        DictValues dictValues = new DictValues();
        dictValues.setDictName(dictName);
        dictValues.setDictValue(dictValue);
        return dictDao.findDictValuesByNameAndValue(dictValues).getDictCode();
    }
}