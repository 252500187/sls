package com.sls.system.entity;

public class DictDefine {
    private String dictName;
    private String oldDictName;
    private String dictDescription;

    public DictDefine() {
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getOldDictName() {
        return oldDictName;
    }

    public void setOldDictName(String oldDictName) {
        this.oldDictName = oldDictName;
    }

    public String getDictDescription() {
        return dictDescription;
    }

    public void setDictDescription(String dictDescription) {
        this.dictDescription = dictDescription;
    }
}
