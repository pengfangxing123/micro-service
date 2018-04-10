package com.genius.znzx.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果封装
 *
 *
 * @since 2017-03-21
 */
public class JsonResult {

    public final static int SUCCESS = 0;//0,成功;其他为失败
    public final static String EMPTY = "";
    private int errCode = SUCCESS;//默认为成功
    private String msg = EMPTY;
    private final static Map map = new HashMap<>();
    private Object data;

    /**
     * 封装一个正常的返回对象.
     *
     * @param resultData result object
     * @return {@link JsonResult}
     */
    public static JsonResult build(Object resultData) {
        JsonResult result = new JsonResult();
        result.setData(resultData);
        return result;
    }

    /**
     * 封装返回一个指定的对象. btw:Data对象为空字符串
     *
     * @param errCode int value of return code
     * @param msg     String value of message
     * @return {@link JsonResult}
     */
    public static JsonResult build(int errCode, String msg) {
        return build(errCode, msg, map);
    }

    /**
     * 返回通用对象.
     *
     * @param errCode    返回码
     * @param msg        返回信息
     * @param resultData 对象
     * @return {@link JsonResult}
     */
    public static JsonResult build(int errCode, String msg, Object resultData) {
        JsonResult result = new JsonResult();
        result.setErrCode(errCode);
        result.setMsg(msg);
        result.setData(resultData);
        return result;
    }

    /**
     * 封装返回一个成功的对象.
     */
    public static JsonResult buildSuccess() {
        return JsonResult.build(SUCCESS, "success",map);
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int retcode) {
        this.errCode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
