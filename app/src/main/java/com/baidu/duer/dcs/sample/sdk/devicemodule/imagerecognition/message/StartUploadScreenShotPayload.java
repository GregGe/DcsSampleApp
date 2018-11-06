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
package com.baidu.duer.dcs.sample.sdk.devicemodule.imagerecognition.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;

/**
 * StartUploadScreenShot事件对应的payload结构
 * </p>
 * Created by wenzongliang on 2017/8/2.
 */
public class StartUploadScreenShotPayload extends Payload {
    /**
     * UploadScreenShot指令和StartUploadScreenShot事件的token要对应的一样
     */
    private String token;

    /**
     * 取图类型
     */
    private String type;

    /**
     * 图片地址，http(s)协议格式，即一个图片的地址
     */
    private String url;

    public StartUploadScreenShotPayload(String token, String type, String url) {
        this.token = token;
        this.type = type;
        this.url = url;
    }

    protected StartUploadScreenShotPayload(Parcel in) {
        super(in);
        token = in.readString();
        type = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(token);
        dest.writeString(type);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StartUploadScreenShotPayload> CREATOR = new Creator<StartUploadScreenShotPayload>() {
        @Override
        public StartUploadScreenShotPayload createFromParcel(Parcel in) {
            return new StartUploadScreenShotPayload(in);
        }

        @Override
        public StartUploadScreenShotPayload[] newArray(int size) {
            return new StartUploadScreenShotPayload[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
