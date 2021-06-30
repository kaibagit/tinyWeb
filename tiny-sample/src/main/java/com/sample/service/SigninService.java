package com.sample.service;

import com.sample.models.Member;
import com.tinyweb.error.Result;

import java.util.Objects;

/**
 * SigninService
 * Created by luliru on 6/30/21.
 */
public class SigninService {

    public static Result<Member> signin(String name, String password_sign) {
        Member member = Member.findByName(name);
        if (member == null || !Objects.equals(member.password_sign, password_sign)) {
            return Result.error("wrong-password");
        }
        return Result.ok(member);
    }
}
