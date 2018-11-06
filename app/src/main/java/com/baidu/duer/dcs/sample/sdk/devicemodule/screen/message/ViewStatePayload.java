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
package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;


public class ViewStatePayload extends Payload {
    private String token;

    public ViewStatePayload(String token) {
        this.token = token;
    }

    protected ViewStatePayload(Parcel in) {
        super(in);
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ViewStatePayload> CREATOR = new Creator<ViewStatePayload>() {
        @Override
        public ViewStatePayload createFromParcel(Parcel in) {
            return new ViewStatePayload(in);
        }

        @Override
        public ViewStatePayload[] newArray(int size) {
            return new ViewStatePayload[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}