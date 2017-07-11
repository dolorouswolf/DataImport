package com.cmb.ccd.domain.service.imp;

import com.cmb.ccd.domain.model.InputTable;
import com.cmb.ccd.domain.service.Repository;

/**
 * Created by LM on 2017/7/11.
 */
public class VerticaRepository implements Repository {
    @Override
    public InputTable select(InputTable table) {
        return null;
    }

    @Override
    public void update(InputTable table) {

    }


    /**
     *
     public void produce(InputDefinition rowDef, Row row) {
     if (!rowDef.isInitialed())
     throw new IllegalArgumentException("文件中没有定义表的列定义");
     Row existRow = select(rowDef, row);
     if (existRow == null || !row.sqlPk().equals(existRow.sqlPk())) {
     insert(rowDef, row);
     } else {
     update(rowDef, row.update(existRow));
     }
     }

     private void update(InputDefinition rowDef, Row row) {
     List<String> sets = new ArrayList<>();
     for (int i = 1; i < rowDef.columns().size() && i < row.columnValues().size(); i++) {
     String set = rowDef.columns().get(i) + "='" + row.columnValues().get(i) + "'";
     sets.add(set);
     }
     String sql = "update " + rowDef.fullTableName() + " set "
     + StringUtil.flat(sets, ",", "", "") + " where "
     + rowDef.pk() + "=" + row.sqlPk() + "";
     System.out.println(sql);
     verticaClient.exec(sql);
     }

     private void insert(InputDefinition rowDef, Row row) {
     String sql = "insert into " + rowDef.fullTableName() + "(" + rowDef.columnStr()
     + ") values(" + row.sqlColumns() + ")";
     System.out.println(sql);
     verticaClient.exec(sql);
     }

     private Row select(InputDefinition rowDef, Row row) {
     String sql = "select " + rowDef.columnStr() + " from " + rowDef.fullTableName()
     + " where " + rowDef.pk() + "=" + row.sqlPk();
     Row result = verticaClient.query(sql);
     System.out.println(sql + " result:" + result.columnValues());
     return result;
     }

     */
}
