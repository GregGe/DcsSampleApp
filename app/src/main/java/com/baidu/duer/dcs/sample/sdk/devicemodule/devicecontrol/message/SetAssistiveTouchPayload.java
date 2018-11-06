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

public class SetAssistiveTouchPayload extends Payload {
    private String target;
    private Boolean assistiveTouch;

    public SetAssistiveTouchPayload(@JsonProperty("target") String target,
                                    @JsonProperty("assistiveTouch") Boolean assistiveTouch) {
        this.target = target;
        this.assistiveTouch = assistiveTouch;
    }

    protected SetAssistiveTouchPayload(Parcel in) {
        super(in);
        target = in.readString();
        byte tmpAssistiveTouch = in.readByte();
        assistiveTouch = tmpAssistiveTouch == 0 ? null : tmpAssistiveTouch == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(target);
        dest.writeByte((byte) (assistiveTouch == null ? 0 : assistiveTouch ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetAssistiveTouchPayload> CREATOR = new Creator<SetAssistiveTouchPayload>() {
        @Override
        public SetAssistiveTouchPayload createFromParcel(Parcel in) {
            return new SetAssistiveTouchPayload(in);
        }

        @Override
        public SetAssistiveTouchPayload[] newArray(int size) {
            return new SetAssistiveTouchPayload[size];
        }
    };

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getAssistiveTouch() {
        return assistiveTouch;
    }

    public void setAssistiveTouch(Boolean assistiveTouch) {
        this.assistiveTouch = assistiveTouch;
    }
}
