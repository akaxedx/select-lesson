package anda.selectlesson.system.enums;

import lombok.Getter;

@Getter
public enum Code {
    SUCCESS(200, "success"),
    Error(400, "error");
    private final int code;
    private final String msg;
    private Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
