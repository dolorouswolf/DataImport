package com.cmb.ccd.domain.model;

import com.cmb.ccd.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LM on 2017/7/11.
 */
public class InputRow {
    private InputCell pk;
    private List<InputCell> cols = new ArrayList<>();

    public InputRow(List<String> colNames, List<String> values) {
        this.pk = new InputCell(colNames.get(0), values.get(0));
        for (int i = 1; i < colNames.size() && i < values.size(); i++) {
            this.cols.add(new InputCell(colNames.get(i), values.get(i)));
        }
    }

    public boolean isColExists(String columnName) {
        if (StringUtil.isEmpty(columnName))
            return true;
        return (this.pk != null && columnName.equals(this.pk.columnName())) ||
                (this.cols.stream().filter(x->columnName.equals(x.columnName())).count() > 0);
    }

    @Override
    public String toString() {
        return "{cols:["+pk.columnName()+","+
                cols.stream().map(x->x.columnName()).reduce((x,y)->x+","+y) +
                "],values:["+pk.value()+","+
                cols.stream().map(x->x.value()).reduce((x,y)->x+","+y) +
                "]}";

    }
}
