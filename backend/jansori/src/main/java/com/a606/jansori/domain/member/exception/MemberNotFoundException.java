package com.a606.jansori.domain.member.exception;

import com.a606.jansori.global.exception.NotFoundException;
import lombok.Getter;

@Getter
public class MemberNotFoundException extends NotFoundException {

    private static final String code = "800";
    private static final String message = "유저를 찾을 수 없습니다.";

    public MemberNotFoundException() {
        super(code, message);
    }
}
