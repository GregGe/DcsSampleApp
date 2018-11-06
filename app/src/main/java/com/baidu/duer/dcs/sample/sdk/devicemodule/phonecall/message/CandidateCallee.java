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
package com.baidu.duer.dcs.sample.sdk.devicemodule.phonecall.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by caoyushu01 on 17/7/6.
 */

public class CandidateCallee implements Parcelable, Serializable {
    public String contactName;
    public String pinYin;

    public CandidateCallee(@JsonProperty("contactName") String contactName, @JsonProperty("pinyin") String pinYin) {
        this.contactName = contactName;
        this.pinYin = pinYin;
    }

    protected CandidateCallee(Parcel in) {
        contactName = in.readString();
        pinYin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(pinYin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CandidateCallee> CREATOR = new Creator<CandidateCallee>() {
        @Override
        public CandidateCallee createFromParcel(Parcel in) {
            return new CandidateCallee(in);
        }

        @Override
        public CandidateCallee[] newArray(int size) {
            return new CandidateCallee[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPinYin() {
        return pinYin;
    }

    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }
}
