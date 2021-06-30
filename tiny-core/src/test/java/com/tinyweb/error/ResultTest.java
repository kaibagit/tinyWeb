package com.tinyweb.error;

import org.junit.Test;

/**
 * ResultTest
 * Created by luliru on 6/30/21.
 */
public class ResultTest {

    @Test
    public void test() {
        Result<Integer> result = fooSuccess();
        result.except("short write2", e -> {
            System.out.println(e);
        }).except("unexpected EOF2", e -> {
            System.out.println(e);
        }).except(new String[]{"unexpected EOF2", "eee"}, e -> {
            System.out.println(e);
        }).except(Error.class, error1 -> {

        }).success(t -> System.out.println(t));

        result = fooFail();
        result.except("short write2", e -> {
            System.out.println(e);
        }).except("unexpected EOF2", e -> {
            System.out.println(e);
            throw new RuntimeException("??");
        }).success(t -> System.out.println(t));

        result.value();
    }

    private Result<Integer> fooSuccess() {
        return Result.ok(1);
    }

    private Result<Integer> fooFail() {
        return Result.error("unexpected EOF");
    }
}
