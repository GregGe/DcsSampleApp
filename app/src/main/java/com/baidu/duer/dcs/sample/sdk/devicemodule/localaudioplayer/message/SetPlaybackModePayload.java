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
package com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by longyin01 on 17/10/23.
 */

public class SetPlaybackModePayload extends Payload {

    private String mode;

    public SetPlaybackModePayload(@JsonProperty("mode") String mode) {
        this.mode = mode;
    }

    protected SetPlaybackModePayload(Parcel in) {
        super(in);
        mode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetPlaybackModePayload> CREATOR = new Creator<SetPlaybackModePayload>() {
        @Override
        public SetPlaybackModePayload createFromParcel(Parcel in) {
            return new SetPlaybackModePayload(in);
        }

        @Override
        public SetPlaybackModePayload[] newArray(int size) {
            return new SetPlaybackModePayload[size];
        }
    };

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
