package com.cmb.ccd.domain.service;

import com.cmb.ccd.domain.model.InputCell;
import com.cmb.ccd.domain.model.InputRow;
import com.cmb.ccd.domain.model.InputTable;

import java.util.List;

/**
 * Created by LM on 2017/7/11.
 */
public interface Repository {
    public InputTable select(InputTable table);
    public void update(InputTable table);
}
