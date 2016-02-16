/*
* AutoCompleteServiceImpl.java
* Created on  2013-10-31 下午2:23
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-31       gaoxinyu    初始版本
*
*/
package com.sls.autocomplete.service;

import com.core.autocomplete.entity.AutoCompleteVO;
import com.core.autocomplete.service.AbstractAutoCompleteService;
import com.sls.autocomplete.dao.AutoCompleteDao;
import com.sls.system.dao.DictDao;
import com.sls.system.entity.DictDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AutoCompleteServiceImpl
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
@Service("autoCompleteService")
public class AutoCompleteServiceImpl extends AbstractAutoCompleteService {

    @Autowired
    private AutoCompleteDao autoCompleteDao;

    @Autowired
    private DictDao dictDao;

    @PostConstruct
    @Override
    public void initAutoComplete() {
        super.initAutoComplete();
    }

    @Override
    protected void initAutoCompleteMap() {
        Map<String, List<AutoCompleteVO>> autoCompleteMap = new HashMap<String, List<AutoCompleteVO>>();

//        String dictName;
//        List<DictDefine> dictDefineList = dictDao.findAllDictDefine();
//        if (dictDefineList != null && dictDefineList.size() != 0) {
//            for (DictDefine dictDefine : dictDefineList) {
//                dictName = dictDefine.getDictName();
//                autoCompleteMap.put(dictName, autoCompleteDao.getDictValues(dictName));
//            }
//        }

//        autoCompleteMap.put("allRoles", autoCompleteDao.findAllRoles());
//        autoCompleteMap.put("allGroupJudges", autoCompleteDao.findGroupJudges());
//        autoCompleteMap.put("candidateGroupValue", autoCompleteDao.findCandidateGroupList());
//        autoCompleteMap.put("permissionTokenValue", autoCompleteDao.findPermissionList());
//        autoCompleteMap.put("allPaperGrade", autoCompleteDao.findAllPaperGrade());
//        autoCompleteMap.put("groupJudgesValue", autoCompleteDao.findGroupJudgesValue());
//        autoCompleteMap.put("groupCandidateValue",autoCompleteDao.findGroupCandidateValue());
//        autoCompleteMap.put("groupDepartmentVote",autoCompleteDao.findGroupDepartmentVote());
//        autoCompleteMap.put("performanceContent",autoCompleteDao.findPerformanceContent());
//        autoCompleteMap.put("performanceGrade",autoCompleteDao.findPerformanceGrade());
//        autoCompleteMap.put("performanceRank",autoCompleteDao.findPerformanceRank());

        setAutoCompleteMap(autoCompleteMap);
    }
}
