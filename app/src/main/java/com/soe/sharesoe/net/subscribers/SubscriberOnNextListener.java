package com.soe.sharesoe.net.subscribers;

/**
 * Created by wangxiaofa on 16/11/22.
 *
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onCompleted();
    void onError();

}
