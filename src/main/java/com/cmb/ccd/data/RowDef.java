package com.cmb.ccd.data;

import com.cmb.ccd.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LM on 2017/7/1.
 */
public class RowDef {
    private String table;
    private String schema;
    private List<String> columns = new ArrayList<>();

    public RowDef setTable(String table) {
        this.table = table;
        return this;
    }
    public RowDef setSchema(String schema) {
        this.schema = schema;
        return this;
    }
    public RowDef setColumns(String columns) {
        if (StringUtil.isEmpty(columns))
            throw new IllegalArgumentException("文件第一行必须是列名，以空格分隔");
        this.columns = Stream.of(columns.split(Row.COL_SEPARATOR)).collect(Collectors.toList());
        return this;
    }
    public List<String> columns() {
        return this.columns;
    }
    public String columnStr() {
        return StringUtil.flat(this.columns, ",", "", "");
    }
    public String fullTableName() {
        return this.schema + "." + this.table;
    }
    public String pk() {
        return isInitialed() ? this.columns.get(0) : "";
    }

    public boolean isInitialed() {
        return !this.columns.isEmpty();
    }
}
