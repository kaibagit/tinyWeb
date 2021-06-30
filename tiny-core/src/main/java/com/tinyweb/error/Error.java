package com.tinyweb.error;

/**
 * Error
 * Created by luliru on 6/29/21.
 */
public class Error {

    private String code;

    private String message;

    public Error() {
        this.code = getClass().getSimpleName();
    }

    public Error(String code) {
        this.code = code;
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
