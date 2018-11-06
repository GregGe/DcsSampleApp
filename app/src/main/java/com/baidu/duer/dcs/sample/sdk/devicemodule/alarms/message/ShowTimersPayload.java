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
package com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;

/**
 * Created by longyin01 on 17/8/9.
 */

public class ShowTimersPayload extends Payload {

    public ShowTimersPayload() {

    }

    protected ShowTimersPayload(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShowTimersPayload> CREATOR = new Creator<ShowTimersPayload>() {
        @Override
        public ShowTimersPayload createFromParcel(Parcel in) {
            return new ShowTimersPayload(in);
        }

        @Override
        public ShowTimersPayload[] newArray(int size) {
            return new ShowTimersPayload[size];
        }
    };
}
