package cn.com.magicabc.http;

import rx.functions.Func1;

/**
 * Created by bei on 2017/4/26.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (!tHttpResult.isSuccessful()) {
            throw new ApiException("网络异常");
        }
        return tHttpResult.getResult();
    }
}
