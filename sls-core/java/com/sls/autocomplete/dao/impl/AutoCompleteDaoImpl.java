package com.sls.autocomplete.dao.impl;

import com.core.autocomplete.dao.AutoCompleteBaseDao;
import com.core.autocomplete.entity.AutoCompleteVO;
import com.sls.autocomplete.dao.AutoCompleteDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("autoCompleteDao")
public class AutoCompleteDaoImpl extends AutoCompleteBaseDao implements AutoCompleteDao {
    @Override
    public List<AutoCompleteVO> getDictValues(String dictName) {
        return findAutoCompleteList("select dict_code id,dict_value name from dict_values " +
                "where dict_name='" + dictName + "'order by dict_code"   );
    }
}
