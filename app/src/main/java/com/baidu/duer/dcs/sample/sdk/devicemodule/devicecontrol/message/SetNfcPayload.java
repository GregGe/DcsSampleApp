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

public class SetNfcPayload extends Payload {
    private String target;
    private Boolean nfc;

    public SetNfcPayload(@JsonProperty("target") String target,
                         @JsonProperty("nfc") Boolean nfc) {
        this.target = target;
        this.nfc = nfc;
    }

    protected SetNfcPayload(Parcel in) {
        super(in);
        target = in.readString();
        byte tmpNfc = in.readByte();
        nfc = tmpNfc == 0 ? null : tmpNfc == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        dest.writeByte((byte) (nfc == null ? 0 : nfc ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetNfcPayload> CREATOR = new Creator<SetNfcPayload>() {
        @Override
        public SetNfcPayload createFromParcel(Parcel in) {
            return new SetNfcPayload(in);
        }

        @Override
        public SetNfcPayload[] newArray(int size) {
            return new SetNfcPayload[size];
        }
    };

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getNfc() {
        return nfc;
    }

    public void setNfc(Boolean nfc) {
        this.nfc = nfc;
    }
}
