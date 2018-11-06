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
package com.baidu.duer.dcs.sample.sdk.devicemodule.app.message;

import android.os.Parcel;

import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.AppInfo;
import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by caoyushu01 on 2017/7/21.
 */

public class AppStatePayload extends Payload {
    public List<AppInfo> installedApps;

    public AppStatePayload(@JsonProperty("installedApps") List<AppInfo> installedApps) {
        this.installedApps = installedApps;
    }

    protected AppStatePayload(Parcel in) {
        super(in);
        installedApps = in.createTypedArrayList(AppInfo.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(installedApps);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AppStatePayload> CREATOR = new Creator<AppStatePayload>() {
        @Override
        public AppStatePayload createFromParcel(Parcel in) {
            return new AppStatePayload(in);
        }

        @Override
        public AppStatePayload[] newArray(int size) {
            return new AppStatePayload[size];
        }
    };
}
