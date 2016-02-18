package com.core.autocomplete.entity;

/**
 * @author gaoxinyu
 * @version 1.0.1
 */
public class AutoCompleteParam {

    private String keyword;
    private boolean isShowAll;
    private String flag;
    private String sql;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
