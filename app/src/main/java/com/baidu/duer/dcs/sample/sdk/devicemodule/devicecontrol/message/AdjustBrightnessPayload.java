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

public class AdjustBrightnessPayload extends Payload {
    private String target;
    private Integer brightness;

    public AdjustBrightnessPayload(@JsonProperty("target") String target,
                                   @JsonProperty("brightness") Integer brightness) {
        this.target = target;
        this.brightness = brightness;
    }

    protected AdjustBrightnessPayload(Parcel in) {
        super(in);
        target = in.readString();
        if (in.readByte() == 0) {
            brightness = null;
        } else {
            brightness = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        if (brightness == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(brightness);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AdjustBrightnessPayload> CREATOR = new Creator<AdjustBrightnessPayload>() {
        @Override
        public AdjustBrightnessPayload createFromParcel(Parcel in) {
            return new AdjustBrightnessPayload(in);
        }

        @Override
        public AdjustBrightnessPayload[] newArray(int size) {
            return new AdjustBrightnessPayload[size];
        }
    };

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }
}
