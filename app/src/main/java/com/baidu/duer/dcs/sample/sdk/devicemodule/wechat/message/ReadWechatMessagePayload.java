/*
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
package com.baidu.duer.dcs.sample.sdk.devicemodule.wechat.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;

/**
 * SendWechatByName指令对应的payload
 * <p>
 * Created by wenzongliang on 2017/8/23.
 */
public class ReadWechatMessagePayload extends Payload {
    public String contactName;

    public ReadWechatMessagePayload(String contactName) {
        this.contactName = contactName;
    }

    protected ReadWechatMessagePayload(Parcel in) {
        super(in);
        contactName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(contactName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReadWechatMessagePayload> CREATOR = new Creator<ReadWechatMessagePayload>() {
        @Override
        public ReadWechatMessagePayload createFromParcel(Parcel in) {
            return new ReadWechatMessagePayload(in);
        }

        @Override
        public ReadWechatMessagePayload[] newArray(int size) {
            return new ReadWechatMessagePayload[size];
        }
    };
}
