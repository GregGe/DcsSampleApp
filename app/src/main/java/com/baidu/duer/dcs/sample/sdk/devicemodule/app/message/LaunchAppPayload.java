/*
 * *
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.baidu.duer.dcs.sample.sdk.devicemodule.app.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;

/**
 * Created by longyin01 on 17/11/27.
 */

public class LaunchAppPayload extends Payload {

    /**
     * 可选字段，应用的名称
     */
    private String appName;

    /**
     * 可选字段，应用的包名
     */
    private String packageName;

    /**
     * 可选字段，打开应用指定功能
     */
    private String deepLink;

    /**
     * 打开这个应用的token
     */
    private String token;

    public LaunchAppPayload() {

    }

    protected LaunchAppPayload(Parcel in) {
        super(in);
        appName = in.readString();
        packageName = in.readString();
        deepLink = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeString(deepLink);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LaunchAppPayload> CREATOR = new Creator<LaunchAppPayload>() {
        @Override
        public LaunchAppPayload createFromParcel(Parcel in) {
            return new LaunchAppPayload(in);
        }

        @Override
        public LaunchAppPayload[] newArray(int size) {
            return new LaunchAppPayload[size];
        }
    };

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
