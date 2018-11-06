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
package com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;

/**
 * Created by caoyushu01 on 2017/7/18.
 */

public class PhonecallByNumberPayload extends Payload {
    // 服务端返回的全部响应当前次打电话的电话号/或服务端解析到的名称
    private CandidateCalleeNumber callee;

    // 使用sim卡1/卡2
    private String useSimIndex;

    // 用户指定的运营商名称,可选字段
    private String useCarrier;

    // 拨打电话给指定号码的token
    private String token;

    public PhonecallByNumberPayload() {

    }

    protected PhonecallByNumberPayload(Parcel in) {
        super(in);
        callee = in.readParcelable(CandidateCalleeNumber.class.getClassLoader());
        useSimIndex = in.readString();
        useCarrier = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(callee, flags);
        dest.writeString(useSimIndex);
        dest.writeString(useCarrier);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PhonecallByNumberPayload> CREATOR = new Creator<PhonecallByNumberPayload>() {
        @Override
        public PhonecallByNumberPayload createFromParcel(Parcel in) {
            return new PhonecallByNumberPayload(in);
        }

        @Override
        public PhonecallByNumberPayload[] newArray(int size) {
            return new PhonecallByNumberPayload[size];
        }
    };

    public CandidateCalleeNumber getCallee() {
        return callee;
    }

    public void setCallee(CandidateCalleeNumber callee) {
        this.callee = callee;
    }

    public String getUseSimIndex() {
        return useSimIndex;
    }

    public void setUseSimIndex(String useSimIndex) {
        this.useSimIndex = useSimIndex;
    }

    public String getUseCarrier() {
        return useCarrier;
    }

    public void setUseCarrier(String useCarrier) {
        this.useCarrier = useCarrier;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
