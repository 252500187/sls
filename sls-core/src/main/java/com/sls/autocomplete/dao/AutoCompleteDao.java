package com.sls.autocomplete.dao;

import com.core.autocomplete.entity.AutoCompleteVO;

import java.util.List;

public interface AutoCompleteDao {
    List<AutoCompleteVO> getDictValues(String dictName);
}
