package com.core.autocomplete.entity;

import com.core.util.pinyin.Cn2Pinyin;

import java.io.Serializable;

public class AutoCompleteVO implements Serializable{

    private String id;
    private String name;
    private String spell;
    private String firstSpell;

    public void setSpellAndFirstSpellByName() {
        Cn2Pinyin cn2Spell = new Cn2Pinyin();
        setSpell(cn2Spell.converterToSpell(getName()));
        setFirstSpell(cn2Spell.converterToFirstSpell(getName()));
    }

    public boolean contain(String keyword) {
        return getSpell().contains(keyword) || getFirstSpell().contains(keyword) || getName().toLowerCase().contains(keyword);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getFirstSpell() {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }
}

