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

public class SetPhoneModePayload extends Payload {
    private String target;
    private String mode;
    private Boolean phoneMode;

    public SetPhoneModePayload(@JsonProperty("target") String target,
                               @JsonProperty("mode") String mode,
                               @JsonProperty("phoneMode") Boolean phoneMode) {
        this.target = target;
        this.mode = mode;
        this.phoneMode = phoneMode;
    }

    protected SetPhoneModePayload(Parcel in) {
        super(in);
        target = in.readString();
        mode = in.readString();
        byte tmpPhoneMode = in.readByte();
        phoneMode = tmpPhoneMode == 0 ? null : tmpPhoneMode == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        dest.writeString(mode);
        dest.writeByte((byte) (phoneMode == null ? 0 : phoneMode ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetPhoneModePayload> CREATOR = new Creator<SetPhoneModePayload>() {
        @Override
        public SetPhoneModePayload createFromParcel(Parcel in) {
            return new SetPhoneModePayload(in);
        }

        @Override
        public SetPhoneModePayload[] newArray(int size) {
            return new SetPhoneModePayload[size];
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

    public Boolean getPhoneMode() {
        return phoneMode;
    }

    public void setPhoneMode(Boolean phoneMode) {
        this.phoneMode = phoneMode;
    }
}
