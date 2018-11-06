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

public class CandidateRecipient implements Parcelable, Serializable {
    private String contactName = "";
    private String pinyin = "";

    public CandidateRecipient() {

    }

    protected CandidateRecipient(Parcel in) {
        contactName = in.readString();
        pinyin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CandidateRecipient> CREATOR = new Creator<CandidateRecipient>() {
        @Override
        public CandidateRecipient createFromParcel(Parcel in) {
            return new CandidateRecipient(in);
        }

        @Override
        public CandidateRecipient[] newArray(int size) {
            return new CandidateRecipient[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
