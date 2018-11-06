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

public class SearchAndPlayUnicastPayload extends Payload {

    private String query;
    private String album;
    private String track;
    private String trackNumber;
    private String artist;
    private String category;
    private String sortType;

    public SearchAndPlayUnicastPayload(@JsonProperty("query") String query,
                                       @JsonProperty("album") String album,
                                       @JsonProperty("track") String track,
                                       @JsonProperty("trackNumber") String trackNumber,
                                       @JsonProperty("artist") String artist,
                                       @JsonProperty("category") String category,
                                       @JsonProperty("sortType") String sortType) {
        this.query = query;
        this.album = album;
        this.track = track;
        this.trackNumber = trackNumber;
        this.artist = artist;
        this.category = category;
        this.sortType = sortType;
    }

    protected SearchAndPlayUnicastPayload(Parcel in) {
        super(in);
        query = in.readString();
        album = in.readString();
        track = in.readString();
        trackNumber = in.readString();
        artist = in.readString();
        category = in.readString();
        sortType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(query);
        dest.writeString(album);
        dest.writeString(track);
        dest.writeString(trackNumber);
        dest.writeString(artist);
        dest.writeString(category);
        dest.writeString(sortType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchAndPlayUnicastPayload> CREATOR = new Creator<SearchAndPlayUnicastPayload>() {
        @Override
        public SearchAndPlayUnicastPayload createFromParcel(Parcel in) {
            return new SearchAndPlayUnicastPayload(in);
        }

        @Override
        public SearchAndPlayUnicastPayload[] newArray(int size) {
            return new SearchAndPlayUnicastPayload[size];
        }
    };

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
