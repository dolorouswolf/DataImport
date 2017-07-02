package com.cmb.ccd.data;

import com.cmb.ccd.output.VerticaClient;
import com.cmb.ccd.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    VerticaClient verticaClient;
    RowDef rowDef;

    public DataManager() {
        rowDef = new RowDef().setSchema("tag").setTable("udr_s_pluto_fraud_userinfo_tag");
        verticaClient = new VerticaClient();
    }

    public void start(String filePath) {
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (!file.isFile() || !file.exists()) {
                System.out.println("找不到指定的文件");
                return;
            }
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            int count = 0;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println("『" + lineTxt + "』");
                if (StringUtil.isEmpty(lineTxt))
                    continue;
                if (count == 0) {
                    rowDef.setColumns(lineTxt);
                } else {
                    try {
                        produce(rowDef, new Row(lineTxt));
                    } catch (Exception e) {
                        System.out.println("======ERROR===== " + e.getMessage());
                        continue;
                    }
                }
                count++;
                Thread.sleep(500);

                read.close();
                verticaClient.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    public void produce(RowDef rowDef, Row row) {
        if (!rowDef.isInitialed())
            throw new IllegalArgumentException("文件中没有定义表的列定义");
        Row existRow = select(rowDef, row);
        if (existRow == null || !row.sqlPk().equals(existRow.sqlPk())) {
            insert(rowDef, row);
        } else {
            update(rowDef, row.update(existRow));
        }
    }

    private void update(RowDef rowDef, Row row) {
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

    private void insert(RowDef rowDef, Row row) {
        String sql = "insert into " + rowDef.fullTableName() + "(" + rowDef.columnStr()
                + ") values(" + row.sqlColumns() + ")";
        System.out.println(sql);
        verticaClient.exec(sql);
    }

    private Row select(RowDef rowDef, Row row) {
        String sql = "select " + rowDef.columnStr() + " from " + rowDef.fullTableName()
                + " where " + rowDef.pk() + "=" + row.sqlPk();
        Row result = verticaClient.query(sql);
        System.out.println(sql + " result:" + result.columnValues());
        return result;
    }

    public static void main(String[] args) {
        new DataManager().start("F:\\device.txt");

    }
}
