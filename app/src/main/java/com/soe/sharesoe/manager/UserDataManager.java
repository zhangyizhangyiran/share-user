package com.soe.sharesoe.manager;/*
 * Copyright (C) 2012 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.text.TextUtils;

import com.soe.sharesoe.BuildConfig;
import com.soe.sharesoe.base.App;
import com.soe.sharesoe.entity.RegisterModel;
import com.soe.sharesoe.utils.S;


/**
 * @version V1.0
 * @ClassName: LocalUserData
 * @Description: 本地数据存储类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:43:25
 */
public class UserDataManager {

    private static RegisterModel model;

    public static void saveUserInfo(RegisterModel modle) {
        AuthorityManager.getInstance().setAuthorityType(modle.posCode);
        S.setObject(App.getInstance(), "userinfo", modle);
    }

    public static synchronized RegisterModel getUserInfo() {
        if (model == null) {
            model = S.getObject(App.getInstance(), "userinfo", RegisterModel.class);
            if (model == null) {
                model = new RegisterModel();
            }
        }
        return model;
    }

    public static boolean isLogin() {
        if (TextUtils.isEmpty(getToken())) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户手机号
     */
    public static String getPhone() {
        return (String) S.get(App.getInstance(), "userPhone", "");

    }

    public static void setPhone(String Phone) {
        if (TextUtils.isEmpty(Phone)) {
            return;
        }
        S.put(App.getInstance(), "userPhone", Phone);
    }


    /**
     * 保存用户Token
     */
    public static String getToken() {
        return (String) S.get(App.getInstance(), "userToken", "");

    }

    public static void setToken(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        S.put(App.getInstance(), "userToken", token);
    }

    /**
     * 保存用户实名状态
     */
    public static String getCertify() {
        return (String) S.get(App.getInstance(), "attestation", "");

    }

    public static void setCertify(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        S.put(App.getInstance(), "attestation", token);
    }

    /**
     * 保存钱包余额
     */
    public static String getWalletAvailableAmount() {
        return (String) S.get(App.getInstance(), "WalletAvailableAmount", "");

    }

    public static void setWalletAvailableAmount(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        S.put(App.getInstance(), "WalletAvailableAmount", token);
    }

    public static void setUid(String uid) {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        S.put(App.getInstance(), "uid", uid);
    }

    public static void getUid(String uid) {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        S.put(App.getInstance(), "uid", uid);
    }


    public static void clear(Context context) {
        S.clear(context);
        model = null;

    }

    public static String getString(String name, String defaultValue) {
        return (String) S.get(App.getInstance(), name, defaultValue);
    }

    public static String getUpdateVersion() {
        if (!BuildConfig.DEBUG) {
            return "coolqi-1.4-lock.bin";
        }
        return getString("xxx", "coolqi-1.4-lock.bin");
    }
}

