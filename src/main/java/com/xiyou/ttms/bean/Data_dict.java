package com.xiyou.ttms.bean;

public class Data_dict {
    private Integer dictId;

    private Integer dictParentId;

    private Integer dictIndex;

    private String dictName;

    private String dictValue;

    public Integer getDictId() {
        return dictId;
    }

    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }

    public Integer getDictParentId() {
        return dictParentId;
    }

    public void setDictParentId(Integer dictParentId) {
        this.dictParentId = dictParentId;
    }

    public Integer getDictIndex() {
        return dictIndex;
    }

    public void setDictIndex(Integer dictIndex) {
        this.dictIndex = dictIndex;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }
}