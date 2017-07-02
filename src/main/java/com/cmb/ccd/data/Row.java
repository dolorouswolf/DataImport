package com.cmb.ccd.data;

import com.cmb.ccd.exception.UpdateNotAllowedException;
import com.cmb.ccd.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by LM on 2017/6/30.
 */
public class Row {
    private List<String> columnValues = new ArrayList<>();
    public static final String COL_SEPARATOR = " |\t";

    public Row(String readLine) {
        if (StringUtil.isEmpty(readLine))
            throw new IllegalArgumentException("数据行不能为空");
        String[] values = readLine.split(COL_SEPARATOR);
        if (values.length <= 1)
            throw new IllegalArgumentException("数据：『"+readLine+"』用空格分隔后只有一个列");
        this.columnValues = Stream.of(values).collect(Collectors.toList());
    }

    public Row update(Row existRow) {
        if (existRow.columnValues().size() != columnValues().size())
           throw new UpdateNotAllowedException("select的列与要更新的列数量不匹配");
        List<String> result = new ArrayList<>();
        try {
            for (int i = 1; i < existRow.columnValues().size(); i++) {
                List<String> exColValueList = Arrays.asList(existRow.columnValues().get(i).split(","));
                List<String> colValueList = Stream.of(columnValues().get(i).split(",")).collect(Collectors.toList());
                for (String exColValue : exColValueList) {
                    if (!colValueList.contains(exColValue)) {
                        colValueList.add(exColValue);
                    }
                }
                result.add(StringUtil.flat(colValueList, ",", "", ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rowStr = columnValues().get(0) + " " + StringUtil.flat(result, " ","","");
        return new Row(rowStr);
    }

    public List<String> columnValues() {
        return this.columnValues;
    }

    public String sqlPk() {
        return "'" + this.columnValues.get(0) + "'";
    }
    public String sqlColumns() {
        return StringUtil.flat(this.columnValues, "','", "'","'");
    }
}
