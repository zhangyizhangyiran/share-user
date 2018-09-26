package com.soe.sharesoe.net.http;

import android.util.Log;

import com.soe.sharesoe.entity.HttpBaseResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wangxiaofa on 2017/10/20.
 * 自定义解析最外层HttpBaseResult 数据
 */
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>" + response);
        //httpResult 只解析result字段
        HttpBaseResult httpResult = gson.fromJson(response, HttpBaseResult.class);

        if (httpResult.getCode() == 0) {
            //返回数据
            return gson.fromJson(response, type);
        } else if (httpResult.getCode() == 5001) {
            //返回数据
            return gson.fromJson(response, type);
        } else if (httpResult.getCode() == 5002) {
            //返回数据
            return gson.fromJson(response, type);
        } else if (httpResult.getCode() == 5003) {
            //返回数据
            return gson.fromJson(response, type);
        } else if (httpResult.getCode() == 5004) {
            //返回数据
            return gson.fromJson(response, type);
        } else {
            //抛出异常
            throw new ApiException(httpResult.getCode(), httpResult.getMsg());
        }

    }
}
