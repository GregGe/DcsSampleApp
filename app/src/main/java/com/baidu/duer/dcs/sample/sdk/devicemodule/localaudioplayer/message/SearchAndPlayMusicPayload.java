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

import java.util.List;

/**
 * Created by longyin01 on 17/9/26.
 */

public class SearchAndPlayMusicPayload extends Payload {

    private String query;
    private List<String> singer;
    private String song;
    private String album;
    private List<String> tag;

    public SearchAndPlayMusicPayload(@JsonProperty("query") String query,
                                     @JsonProperty("singer") List<String> singer,
                                     @JsonProperty("song") String song,
                                     @JsonProperty("album") String album,
                                     @JsonProperty("tag") List<String> tag) {
        this.query = query;
        this.singer = singer;
        this.song = song;
        this.album = album;
        this.tag = tag;
    }

    protected SearchAndPlayMusicPayload(Parcel in) {
        super(in);
        query = in.readString();
        singer = in.createStringArrayList();
        song = in.readString();
        album = in.readString();
        tag = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(query);
        dest.writeStringList(singer);
        dest.writeString(song);
        dest.writeString(album);
        dest.writeStringList(tag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchAndPlayMusicPayload> CREATOR = new Creator<SearchAndPlayMusicPayload>() {
        @Override
        public SearchAndPlayMusicPayload createFromParcel(Parcel in) {
            return new SearchAndPlayMusicPayload(in);
        }

        @Override
        public SearchAndPlayMusicPayload[] newArray(int size) {
            return new SearchAndPlayMusicPayload[size];
        }
    };

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getSinger() {
        return singer;
    }

    public void setSinger(List<String> singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
