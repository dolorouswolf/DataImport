package com.cmb.ccd.domain.service.imp;

import com.cmb.ccd.domain.model.InputTable;
import com.cmb.ccd.domain.service.TableGenerator;
import com.cmb.ccd.util.StringUtil;

import java.io.*;

/**
 * Created by LM on 2017/7/11.
 */
public class FileTableGenerator {

    public InputTable generate(String filePath) {
        InputTable table = new InputTable();
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (!file.isFile() || !file.exists()) {
                throw new FileNotFoundException("找不到指定的文件:"+filePath);
            }
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file), encoding);//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("『" + line + "』");
                if (StringUtil.isEmpty(line))
                    continue;
                if (count == 0) {
                    table.setColumns(line);
                }
                if (!table.isInitialed())
                    throw new IllegalArgumentException("没有数据列名");

                table.addRow(line);
                count ++;
            }
            read.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return table;
        }
    }

    public InputTable generate(InputTable source, String pk) {

        return null;
    }
}
