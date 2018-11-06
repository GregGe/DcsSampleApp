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
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by longyin01 on 17/8/9.
 */

public class SetTimerPayload extends Payload {

    private int length;
    private String message;

    public SetTimerPayload(@JsonProperty("length") int length,
                           @JsonProperty("message") String message) {
        this.length = length;
        this.message = message;
    }

    protected SetTimerPayload(Parcel in) {
        super(in);
        length = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(length);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetTimerPayload> CREATOR = new Creator<SetTimerPayload>() {
        @Override
        public SetTimerPayload createFromParcel(Parcel in) {
            return new SetTimerPayload(in);
        }

        @Override
        public SetTimerPayload[] newArray(int size) {
            return new SetTimerPayload[size];
        }
    };

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
