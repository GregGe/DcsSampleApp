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
package com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by caoyushu01 on 2017/7/18.
 */

public class CandidateRecipientNumber implements Parcelable, Serializable {
    private String displayName = "";
    private String phoneNumber = "";

    public CandidateRecipientNumber() {

    }

    protected CandidateRecipientNumber(Parcel in) {
        displayName = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CandidateRecipientNumber> CREATOR = new Creator<CandidateRecipientNumber>() {
        @Override
        public CandidateRecipientNumber createFromParcel(Parcel in) {
            return new CandidateRecipientNumber(in);
        }

        @Override
        public CandidateRecipientNumber[] newArray(int size) {
            return new CandidateRecipientNumber[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
