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

public class SetBluetoothPayload extends Payload {
    private String target;
    private Boolean bluetooth;

    public SetBluetoothPayload(@JsonProperty("target") String target,
                               @JsonProperty("bluetooth") Boolean bluetooth) {
        this.target = target;
        this.bluetooth = bluetooth;
    }

    protected SetBluetoothPayload(Parcel in) {
        super(in);
        target = in.readString();
        byte tmpBluetooth = in.readByte();
        bluetooth = tmpBluetooth == 0 ? null : tmpBluetooth == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        dest.writeByte((byte) (bluetooth == null ? 0 : bluetooth ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetBluetoothPayload> CREATOR = new Creator<SetBluetoothPayload>() {
        @Override
        public SetBluetoothPayload createFromParcel(Parcel in) {
            return new SetBluetoothPayload(in);
        }

        @Override
        public SetBluetoothPayload[] newArray(int size) {
            return new SetBluetoothPayload[size];
        }
    };

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(Boolean bluetooth) {
        this.bluetooth = bluetooth;
    }
}
