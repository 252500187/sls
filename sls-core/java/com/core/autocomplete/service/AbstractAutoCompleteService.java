/*
* AbstractAutoComplete.java
* Created on  2013-10-26 下午4:26
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-26       gaoxinyu    初始版本
*
*/
package com.core.autocomplete.service;

import com.core.autocomplete.dao.AutoCompleteBaseDao;
import com.core.autocomplete.entity.AutoCompleteParam;
import com.core.autocomplete.entity.AutoCompleteVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动提示的抽象类
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public abstract class AbstractAutoCompleteService implements AutoCompleteService {

    private Map<String, List<AutoCompleteVO>> autoCompleteMap;

    @Autowired
    private AutoCompleteBaseDao autoCompleteBaseDao;

    public void initAutoComplete() {
        initAutoCompleteMap();
        initAutoCompleteSpell();
    }

    abstract protected void initAutoCompleteMap();

    private void initAutoCompleteSpell() {
        Map<String, List<AutoCompleteVO>> map = getAutoCompleteMap();
        for (String str : map.keySet()) {
            initAutoCompleteSpell(map.get(str));
        }
    }

    private void initAutoCompleteSpell(List<AutoCompleteVO> list) {
        if (list == null) {
            return;
        }
        for (AutoCompleteVO autoCompleteVO : list) {
            autoCompleteVO.setSpellAndFirstSpellByName();
        }
    }

    public List<AutoCompleteVO> getAutoCompleteResultList(AutoCompleteParam autoCompleteParam) {
        return getMatchAutoCompleteList(autoCompleteParam, getAllAutoCompleteList(autoCompleteParam));
    }

    private List<AutoCompleteVO> getAllAutoCompleteList(AutoCompleteParam autoCompleteParam) {
        List<AutoCompleteVO> autoCompleteList;
        if (autoCompleteParam.getSql() == null || autoCompleteParam.getSql().equals("")) {
            autoCompleteList = getAutoCompleteMap().get(autoCompleteParam.getFlag());
        } else {
            autoCompleteList = autoCompleteBaseDao.findAutoCompleteList(autoCompleteParam.getSql());
            initAutoCompleteSpell(autoCompleteList);
        }
        return autoCompleteList;
    }

    private List<AutoCompleteVO> getMatchAutoCompleteList(AutoCompleteParam autoCompleteParam, List<AutoCompleteVO> autoCompleteList) {
        List<AutoCompleteVO> autoCompleteResultList = new ArrayList<AutoCompleteVO>();
        if (autoCompleteList == null || autoCompleteList.size() <= 0) {
            return autoCompleteList;
        }
        for (AutoCompleteVO autoCompleteVO : autoCompleteList) {
            if (!autoCompleteParam.isShowAll() && autoCompleteResultList.size() >= 10) {
                break;
            }
            if (autoCompleteVO.contain(autoCompleteParam.getKeyword())) {
                autoCompleteResultList.add(autoCompleteVO);
            }
        }
        return autoCompleteResultList;
    }

    public Map<String, List<AutoCompleteVO>> getAutoCompleteMap() {
        return autoCompleteMap;
    }

    public void setAutoCompleteMap(Map<String, List<AutoCompleteVO>> autoCompleteMap) {
        this.autoCompleteMap = autoCompleteMap;
    }
}
