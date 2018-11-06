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
package com.baidu.duer.dcs.sample.sdk.devicemodule.contacts.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by caoyushu01 on 2017/7/28.
 */

public class CreateContactPayload extends Payload {
    // 新建联系人的名称
    private String contactName;
    // 新建联系人的号码
    private String phoneNumber;

    public CreateContactPayload(@JsonProperty("contactName") String contactName,
                                @JsonProperty("phoneNumber") String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    protected CreateContactPayload(Parcel in) {
        super(in);
        contactName = in.readString();
        phoneNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(contactName);
        dest.writeString(phoneNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CreateContactPayload> CREATOR = new Creator<CreateContactPayload>() {
        @Override
        public CreateContactPayload createFromParcel(Parcel in) {
            return new CreateContactPayload(in);
        }

        @Override
        public CreateContactPayload[] newArray(int size) {
            return new CreateContactPayload[size];
        }
    };

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
