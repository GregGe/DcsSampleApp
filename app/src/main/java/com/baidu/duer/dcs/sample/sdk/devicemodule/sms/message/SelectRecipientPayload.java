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

import com.baidu.duer.dcs.util.message.Payload;

import java.util.List;

/**
 * Created by caoyushu01 on 2017/7/18.
 */

public class SelectRecipientPayload extends Payload {
    private String messageContent = "";
    private List<CandidateRecipientNumber> candidateRecipients;
    private String useSimIndex = "";
    private String useCarrier = "";

    public SelectRecipientPayload() {

    }

    protected SelectRecipientPayload(Parcel in) {
        super(in);
        messageContent = in.readString();
        candidateRecipients = in.createTypedArrayList(CandidateRecipientNumber.CREATOR);
        useSimIndex = in.readString();
        useCarrier = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(messageContent);
        dest.writeTypedList(candidateRecipients);
        dest.writeString(useSimIndex);
        dest.writeString(useCarrier);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SelectRecipientPayload> CREATOR = new Creator<SelectRecipientPayload>() {
        @Override
        public SelectRecipientPayload createFromParcel(Parcel in) {
            return new SelectRecipientPayload(in);
        }

        @Override
        public SelectRecipientPayload[] newArray(int size) {
            return new SelectRecipientPayload[size];
        }
    };

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public List<CandidateRecipientNumber> getCandidateRecipients() {
        return candidateRecipients;
    }

    public void setCandidateRecipients(List<CandidateRecipientNumber> candidateRecipients) {
        this.candidateRecipients = candidateRecipients;
    }

    public String getUseSimIndex() {
        return useSimIndex;
    }

    public void setUseSimIndex(String useSimIndex) {
        this.useSimIndex = useSimIndex;
    }

    public String getUseCarrier() {
        return useCarrier;
    }

    public void setUseCarrier(String useCarrier) {
        this.useCarrier = useCarrier;
    }
}
