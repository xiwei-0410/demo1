package com.wxw.util;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础状态返回类
 * @author wxw
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 200);
        put("msg", "操作成功");
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static Result success(Object object) {
        Result r = new Result();
        r.put("data", object);
        return r;
    }

    public static Result success(String msg,Object object) {
        Result r = new Result();
        r.put("msg", msg);
        r.put("data", object);
        return r;
    }

    public static Result success(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result success() {
        return new Result();
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
