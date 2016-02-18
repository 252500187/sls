package com.core.autocomplete.service;

import com.core.autocomplete.entity.AutoCompleteParam;
import com.core.autocomplete.entity.AutoCompleteVO;

import java.util.List;

/**
 * 自动提示的抽象类
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public interface AutoCompleteService {

    void initAutoComplete();

    List<AutoCompleteVO> getAutoCompleteResultList(AutoCompleteParam autoCompleteParam);

}
