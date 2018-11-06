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
package com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by caoyushu01 on 2017/7/26.
 */

public class SetPhonePowerPayload extends Payload {
    private String target;
    private String mode;
    private String phonePower;

    public SetPhonePowerPayload(@JsonProperty("target") String target,
                                @JsonProperty("mode") String mode,
                                @JsonProperty("PhonePower") String phonePower) {
        this.target = target;
        this.mode = mode;
        this.phonePower = phonePower;
    }

    protected SetPhonePowerPayload(Parcel in) {
        super(in);
        target = in.readString();
        mode = in.readString();
        phonePower = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        dest.writeString(mode);
        dest.writeString(phonePower);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetPhonePowerPayload> CREATOR = new Creator<SetPhonePowerPayload>() {
        @Override
        public SetPhonePowerPayload createFromParcel(Parcel in) {
            return new SetPhonePowerPayload(in);
        }

        @Override
        public SetPhonePowerPayload[] newArray(int size) {
            return new SetPhonePowerPayload[size];
        }
    };

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getPhonePower() {
        return phonePower;
    }

    public void setPhonePower(String phonePower) {
        this.phonePower = phonePower;
    }
}
