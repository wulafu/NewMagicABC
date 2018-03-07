package cn.com.magicabc.http;

/**
 * Created by bei on 2017/4/26.
 * 响应数据的"基类"，通过指定泛型获取想要的数据类型
 */

public class HttpResult<T> {

    private int code;
    private String msg;
    //private String obj;
    private T result;


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return code == 200 ? true : false;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "[" + "code:" + code + " msg: " + msg + " data: " + result + "]";
    }
}
