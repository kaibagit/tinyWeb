package com.tinyweb.error;

/**
 * ErrorNotHandleException
 * Created by luliru on 6/29/21.
 */
public class ErrorNotHandleException extends RuntimeException {

    private Error error;

    public ErrorNotHandleException(Error error) {
        super(error.code() + ":" + error.message());
        this.error = error;
    }

    public Error error() {
        return this.error;
    }
}
