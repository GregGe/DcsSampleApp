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
package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.duer.dcs.util.message.Payload;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenxiaojian on 17/11/1.
 */

public class RenderAudioListPlayload extends Payload {

    private int nowPlayingIndex;
    private String title;
    private String token;
    private List<AudioItemsBean> audioItems;

    public RenderAudioListPlayload() {

    }

    protected RenderAudioListPlayload(Parcel in) {
        super(in);
        nowPlayingIndex = in.readInt();
        title = in.readString();
        token = in.readString();
        audioItems = in.createTypedArrayList(AudioItemsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(nowPlayingIndex);
        dest.writeString(title);
        dest.writeString(token);
        dest.writeTypedList(audioItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderAudioListPlayload> CREATOR = new Creator<RenderAudioListPlayload>() {
        @Override
        public RenderAudioListPlayload createFromParcel(Parcel in) {
            return new RenderAudioListPlayload(in);
        }

        @Override
        public RenderAudioListPlayload[] newArray(int size) {
            return new RenderAudioListPlayload[size];
        }
    };

    public int getNowPlayingIndex() {
        return nowPlayingIndex;
    }

    public void setNowPlayingIndex(int nowPlayingIndex) {
        this.nowPlayingIndex = nowPlayingIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<AudioItemsBean> getAudioItems() {
        return audioItems;
    }

    public void setAudioItems(List<AudioItemsBean> audioItems) {
        this.audioItems = audioItems;
    }

    public static class AudioItemsBean implements Parcelable, Serializable {

        private ImageBean image;
        private boolean isFavorited;
        private String title;
        private String titleSubtext1;
        private String url;

        public AudioItemsBean() {

        }

        protected AudioItemsBean(Parcel in) {
            image = in.readParcelable(ImageBean.class.getClassLoader());
            isFavorited = in.readByte() != 0;
            title = in.readString();
            titleSubtext1 = in.readString();
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(image, flags);
            dest.writeByte((byte) (isFavorited ? 1 : 0));
            dest.writeString(title);
            dest.writeString(titleSubtext1);
            dest.writeString(url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AudioItemsBean> CREATOR = new Creator<AudioItemsBean>() {
            @Override
            public AudioItemsBean createFromParcel(Parcel in) {
                return new AudioItemsBean(in);
            }

            @Override
            public AudioItemsBean[] newArray(int size) {
                return new AudioItemsBean[size];
            }
        };

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
        }

        public boolean isIsFavorited() {
            return isFavorited;
        }

        public void setIsFavorited(boolean isFavorited) {
            this.isFavorited = isFavorited;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitleSubtext1() {
            return titleSubtext1;
        }

        public void setTitleSubtext1(String titleSubtext1) {
            this.titleSubtext1 = titleSubtext1;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class ImageBean implements Parcelable, Serializable {

            private String src;

            public ImageBean() {

            }

            protected ImageBean(Parcel in) {
                src = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(src);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
                @Override
                public ImageBean createFromParcel(Parcel in) {
                    return new ImageBean(in);
                }

                @Override
                public ImageBean[] newArray(int size) {
                    return new ImageBean[size];
                }
            };

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }
}
