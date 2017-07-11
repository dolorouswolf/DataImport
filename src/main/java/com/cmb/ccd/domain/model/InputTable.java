package com.cmb.ccd.domain.model;

import com.cmb.ccd.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LM on 2017/7/11.
 */
public class InputTable {
    public static final String COL_SEPARATOR = " |\t";
    private String table;
    private String schema;
    private List<String> columns = new ArrayList<>();
    private List<InputRow> rows = new ArrayList<>();

    public InputTable setTable(String table) {
        this.table = table;
        return this;
    }
    public InputTable setSchema(String schema) {
        this.schema = schema;
        return this;
    }
    public InputTable setColumns(String columns) {
        if (StringUtil.isEmpty(columns))
            throw new IllegalArgumentException("文件第一行必须是列名，以空格分隔");
        this.columns = Stream.of(columns.split(COL_SEPARATOR)).collect(Collectors.toList());
        return this;
    }
    public InputTable addRow(String line) {
        if (StringUtil.isEmpty(line) || !this.isInitialed())
            throw new IllegalArgumentException("数据行不能为空");
        String[] values = line.split(COL_SEPARATOR);
        if (values.length <= 1)
            throw new IllegalArgumentException("数据：『"+line+"』用空格分隔后只有一个列");
        InputRow row = new InputRow(columns(), Arrays.asList(values));
        if (!isRowExists(row))
            this.rows.add(row);
        return this;
    }

    private boolean isRowExists(InputRow row) {
        if (row == null)
            return true;
        return this.rows.stream().filter(x->row.toString().equals(x.toString())).count()>0;
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
    public String pkCol() {
        return isInitialed() ? this.columns.get(0) : "";
    }

    public boolean isInitialed() {
        return !this.columns.isEmpty();
    }
}
