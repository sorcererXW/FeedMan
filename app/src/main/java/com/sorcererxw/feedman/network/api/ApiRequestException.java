package com.sorcererxw.feedman.network.api;

import java.io.IOException;

/**
 * @description:
 * @author: Sorcerer
 * @date: 2016/9/17
 */
public class ApiRequestException extends IOException {
    private final int mErrorCode;

    public ApiRequestException(String message, int errorCode) {
        super(message);
        mErrorCode = errorCode;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getMessage() {
        return super.getMessage() + " (" + mErrorCode + ")";
    }
}
