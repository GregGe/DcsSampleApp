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
package com.baidu.duer.dcs.sample.sdk.devicemodule.localaudioplayer.message;

import android.os.Parcel;

import com.baidu.duer.dcs.util.message.Payload;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by longyin01 on 17/9/26.
 */

public class SearchAndPlayRadioPayload extends Payload {

    private String type;
    private String query;
    private String modulation;
    private String frequency;
    private String category;
    private String channel;

    public SearchAndPlayRadioPayload(@JsonProperty("type") String type,
                                     @JsonProperty("query") String query,
                                     @JsonProperty("modulation") String modulation,
                                     @JsonProperty("frequency") String frequency,
                                     @JsonProperty("category") String category,
                                     @JsonProperty("channel") String channel) {
        this.type = type;
        this.query = query;
        this.modulation = modulation;
        this.frequency = frequency;
        this.category = category;
        this.channel = channel;
    }

    protected SearchAndPlayRadioPayload(Parcel in) {
        super(in);
        type = in.readString();
        query = in.readString();
        modulation = in.readString();
        frequency = in.readString();
        category = in.readString();
        channel = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
        dest.writeString(query);
        dest.writeString(modulation);
        dest.writeString(frequency);
        dest.writeString(category);
        dest.writeString(channel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchAndPlayRadioPayload> CREATOR = new Creator<SearchAndPlayRadioPayload>() {
        @Override
        public SearchAndPlayRadioPayload createFromParcel(Parcel in) {
            return new SearchAndPlayRadioPayload(in);
        }

        @Override
        public SearchAndPlayRadioPayload[] newArray(int size) {
            return new SearchAndPlayRadioPayload[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getModulation() {
        return modulation;
    }

    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
