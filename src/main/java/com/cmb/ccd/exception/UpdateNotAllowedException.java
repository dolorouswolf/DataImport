package com.cmb.ccd.exception;

/**
 * Created by LM on 2017/7/1.
 */
public class UpdateNotAllowedException extends RuntimeException {
    public UpdateNotAllowedException(String message) {
        super(message);
    }
}
