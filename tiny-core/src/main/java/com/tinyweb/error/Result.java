package com.tinyweb.error;

import java.util.function.Consumer;

/**
 * Result
 * Created by luliru on 6/28/21.
 */
public class Result<T> {

    private T value;

    private Error error;

    private boolean errorCatched;

    public static <T> Result<T> ok(T t) {
        return new Result(t , null);
    }

    public static <T> Result<T> error(Error error) {
        return new Result(null ,error);
    }

    public static <T> Result<T> error(String error) {
        return new Result(null ,new Error(error));
    }

    public Result(T t, Error error) {
        this.value = t;
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isError() {
        return error != null;
    }

    public T value() {
        if (isError() && !errorCatched) {
            throw new ErrorNotHandleException(error);
        }
        return this.value;
    }

    public Error error() {
        return error;
    }

    /**
     * 忽略错误
     * @return
     */
    public Result<T> ignoreError() {
        errorCatched = true;
        return this;
    }

    /**
     * 将未处理错误转化为异常
     */
    public Result<T> unwrap() {
        if (isError() && !errorCatched) {
            throw new ErrorNotHandleException(error);
        }
        return this;
    }

    /**
     * 成功执行的逻辑，注意success必须放到except之后
     * @param consumer
     * @return
     */
    public Result<T> success(Consumer<T> consumer) {
        if (isError() && !errorCatched) {
            throw new ErrorNotHandleException(error);
        }
        if (isSuccess()) {
            consumer.accept(value);
        }
        return this;
    }

    /**
     * 错误处理
     * @param error
     * @param consumer
     * @return
     */
    public Result<T> except(String error, Consumer<Error> consumer) {
        if (isError() && !errorCatched) {
            if (error.equals(this.error.code())) {
                consumer.accept(this.error);
                errorCatched = true;
            }
        }
        return this;
    }

    /**
     * 错误处理
     * @param errors
     * @param consumer
     * @return
     */
    public Result<T> except(String[] errors, Consumer<Error> consumer) {
        if (isError() && !errorCatched) {
            for (String error : errors) {
                if (error.equals(this.error.code())) {
                    consumer.accept(this.error);
                    errorCatched = true;
                    break;
                }
            }
        }
        return this;
    }

    /**
     * 错误处理
     * @param error
     * @param consumer
     * @return
     */
    public Result<T> except(Class<? extends Error> error, Consumer<Error> consumer) {
        if (isError() && !errorCatched) {
            if (error.isInstance(this.error)) {
                consumer.accept(this.error);
                errorCatched = true;
            }
        }
        return this;
    }

    /**
     * 错误处理
     * @param errors
     * @param consumer
     * @return
     */
    public Result<T> except(Class<? extends Error>[] errors, Consumer<Error> consumer) {
        if (isError() && !errorCatched) {
            for (Class<? extends Error> error : errors) {
                if (error.isInstance(this.error)) {
                    consumer.accept(this.error);
                    errorCatched = true;
                    break;
                }
            }
        }
        return this;
    }
}
