package com.cmb.ccd.domain.service.imp;

import com.cmb.ccd.domain.model.InputTable;
import com.cmb.ccd.domain.service.DataImporter;

/**
 * Created by LM on 2017/7/11.
 */
public class DeviceIdImporter implements DataImporter {


    @Override
    public void run() {
        InputTable table = new FileTableGenerator().generate("F:\\device.txt")
                .setSchema("tag").setTable("udr_s_pluto_fraud_userinfo_tag");

        VerticaRepository repository = new VerticaRepository();
        repository.update(repository.select(table));

        table.setColumns("concat_id dev_id");
    }

    public static void main(String[] args) {
        DeviceIdImporter importer = new DeviceIdImporter();
        importer.run();
    }
}
