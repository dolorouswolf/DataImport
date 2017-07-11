package com.cmb.ccd.domain.model;

/**
 * Created by LM on 2017/7/11.
 */
public class InputCell {
    String columnName;
    String value;

    public InputCell(String columnName, String value) {
        this.columnName = columnName;
        this.value = value;
    }

    public String columnName() {
        return columnName;
    }

    public String value() {
        return value;
    }
}
