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
public class SendWechatByNamePayload extends Payload {
    public String messageType;
    public String contactName;
    public String messageContent;
    public String token;

    public SendWechatByNamePayload(String messageType, String contactName,
                                   String messageContent, String token) {
        this.messageType = messageType;
        this.contactName = contactName;
        this.messageContent = messageContent;
        this.token = token;
    }

    protected SendWechatByNamePayload(Parcel in) {
        super(in);
        messageType = in.readString();
        contactName = in.readString();
        messageContent = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(messageType);
        dest.writeString(contactName);
        dest.writeString(messageContent);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SendWechatByNamePayload> CREATOR = new Creator<SendWechatByNamePayload>() {
        @Override
        public SendWechatByNamePayload createFromParcel(Parcel in) {
            return new SendWechatByNamePayload(in);
        }

        @Override
        public SendWechatByNamePayload[] newArray(int size) {
            return new SendWechatByNamePayload[size];
        }
    };
}
