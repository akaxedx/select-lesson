package anda.selectlesson.system;

import anda.selectlesson.system.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Response<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    public static <T> Response<T> ok(T data) {
        return new Response<>(Code.SUCCESS.getCode(), Code.SUCCESS.getMsg(), data);
    }

    public static <T> Response<T> ok() {
        return ok(null);
    }
    public static <T> Response<T> ok(String msg) {
        return new Response<>(Code.SUCCESS.getCode(), msg, null);
    }

    public static <T> Response<T> error(T data) {
        return  new Response<>(Code.Error.getCode(), Code.Error.getMsg(), data);
    }
    public static <T> Response<T> error() {
        return error(null);
    }
    public static <T> Response<T> error(String msg) {
        return new Response<>(Code.Error.getCode(), msg, null);
    }
}
