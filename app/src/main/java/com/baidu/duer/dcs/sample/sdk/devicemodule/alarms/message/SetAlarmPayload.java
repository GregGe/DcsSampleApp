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

import java.util.List;

/**
 * Created by caoyushu01 on 2017/7/26.
 */

public class SetAlarmPayload extends Payload {

    private int hour;
    private int minutes;
    private String message;
    private List<String> days;
    private boolean viberate;

    public SetAlarmPayload(@JsonProperty("hour") int hour,
                           @JsonProperty("minutes") int minutes,
                           @JsonProperty("message") String message,
                           @JsonProperty("days") List<String> days,
                           @JsonProperty("viberate") boolean viberate) {
        this.hour = hour;
        this.minutes = minutes;
        this.message = message;
        this.days = days;
        this.viberate = viberate;
    }

    protected SetAlarmPayload(Parcel in) {
        super(in);
        hour = in.readInt();
        minutes = in.readInt();
        message = in.readString();
        days = in.createStringArrayList();
        viberate = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(hour);
        dest.writeInt(minutes);
        dest.writeString(message);
        dest.writeStringList(days);
        dest.writeByte((byte) (viberate ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetAlarmPayload> CREATOR = new Creator<SetAlarmPayload>() {
        @Override
        public SetAlarmPayload createFromParcel(Parcel in) {
            return new SetAlarmPayload(in);
        }

        @Override
        public SetAlarmPayload[] newArray(int size) {
            return new SetAlarmPayload[size];
        }
    };

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public boolean isViberate() {
        return viberate;
    }

    public void setViberate(boolean viberate) {
        this.viberate = viberate;
    }
}
