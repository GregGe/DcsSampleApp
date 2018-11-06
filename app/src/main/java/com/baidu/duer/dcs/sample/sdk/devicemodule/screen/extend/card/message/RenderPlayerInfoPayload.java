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

public class RenderPlayerInfoPayload extends Payload {


    private ContentBean content;
    private String token;
    private List<ControlsBean> controls;

    public RenderPlayerInfoPayload() {
    }

    protected RenderPlayerInfoPayload(Parcel in) {
        super(in);
        content = in.readParcelable(ContentBean.class.getClassLoader());
        token = in.readString();
        controls = in.createTypedArrayList(ControlsBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(content, flags);
        dest.writeString(token);
        dest.writeTypedList(controls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderPlayerInfoPayload> CREATOR = new Creator<RenderPlayerInfoPayload>() {
        @Override
        public RenderPlayerInfoPayload createFromParcel(Parcel in) {
            return new RenderPlayerInfoPayload(in);
        }

        @Override
        public RenderPlayerInfoPayload[] newArray(int size) {
            return new RenderPlayerInfoPayload[size];
        }
    };

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ControlsBean> getControls() {
        return controls;
    }

    public void setControls(List<ControlsBean> controls) {
        this.controls = controls;
    }

    public static class ContentBean implements Parcelable, Serializable {

        private ArtBean art;
        private String audioItemType;
        private LyricBean lyric;
        private int mediaLengthInMilliseconds;
        private ProviderBean provider;
        private String title;
        private String titleSubtext1;
        private String titleSubtext2;

        public ContentBean() {

        }

        protected ContentBean(Parcel in) {
            art = in.readParcelable(ArtBean.class.getClassLoader());
            audioItemType = in.readString();
            lyric = in.readParcelable(LyricBean.class.getClassLoader());
            mediaLengthInMilliseconds = in.readInt();
            provider = in.readParcelable(ProviderBean.class.getClassLoader());
            title = in.readString();
            titleSubtext1 = in.readString();
            titleSubtext2 = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(art, flags);
            dest.writeString(audioItemType);
            dest.writeParcelable(lyric, flags);
            dest.writeInt(mediaLengthInMilliseconds);
            dest.writeParcelable(provider, flags);
            dest.writeString(title);
            dest.writeString(titleSubtext1);
            dest.writeString(titleSubtext2);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel in) {
                return new ContentBean(in);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };

        public ArtBean getArt() {
            return art;
        }

        public void setArt(ArtBean art) {
            this.art = art;
        }

        public String getAudioItemType() {
            return audioItemType;
        }

        public void setAudioItemType(String audioItemType) {
            this.audioItemType = audioItemType;
        }

        public LyricBean getLyric() {
            return lyric;
        }

        public void setLyric(LyricBean lyric) {
            this.lyric = lyric;
        }

        public int getMediaLengthInMilliseconds() {
            return mediaLengthInMilliseconds;
        }

        public void setMediaLengthInMilliseconds(int mediaLengthInMilliseconds) {
            this.mediaLengthInMilliseconds = mediaLengthInMilliseconds;
        }

        public ProviderBean getProvider() {
            return provider;
        }

        public void setProvider(ProviderBean provider) {
            this.provider = provider;
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

        public String getTitleSubtext2() {
            return titleSubtext2;
        }

        public void setTitleSubtext2(String titleSubtext2) {
            this.titleSubtext2 = titleSubtext2;
        }

        public static class ArtBean implements Parcelable, Serializable {
            private String src;

            public ArtBean() {

            }

            protected ArtBean(Parcel in) {
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

            public static final Creator<ArtBean> CREATOR = new Creator<ArtBean>() {
                @Override
                public ArtBean createFromParcel(Parcel in) {
                    return new ArtBean(in);
                }

                @Override
                public ArtBean[] newArray(int size) {
                    return new ArtBean[size];
                }
            };

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }

        public static class LyricBean implements Parcelable, Serializable {

            private String format;
            private String url;

            public LyricBean() {

            }

            protected LyricBean(Parcel in) {
                format = in.readString();
                url = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(format);
                dest.writeString(url);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<LyricBean> CREATOR = new Creator<LyricBean>() {
                @Override
                public LyricBean createFromParcel(Parcel in) {
                    return new LyricBean(in);
                }

                @Override
                public LyricBean[] newArray(int size) {
                    return new LyricBean[size];
                }
            };

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ProviderBean implements Parcelable, Serializable {

            private LogoBean logo;
            private String name;

            public ProviderBean() {

            }

            protected ProviderBean(Parcel in) {
                name = in.readString();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(name);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ProviderBean> CREATOR = new Creator<ProviderBean>() {
                @Override
                public ProviderBean createFromParcel(Parcel in) {
                    return new ProviderBean(in);
                }

                @Override
                public ProviderBean[] newArray(int size) {
                    return new ProviderBean[size];
                }
            };

            public LogoBean getLogo() {
                return logo;
            }

            public void setLogo(LogoBean logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class LogoBean {

                private String src;

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }
            }
        }
    }

    public static class ControlsBean implements Parcelable, Serializable {

        private boolean enabled;
        private String name;
        private boolean selected;
        private String type;
        private String selectedValue;

        public ControlsBean() {

        }

        protected ControlsBean(Parcel in) {
            enabled = in.readByte() != 0;
            name = in.readString();
            selected = in.readByte() != 0;
            type = in.readString();
            selectedValue = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte) (enabled ? 1 : 0));
            dest.writeString(name);
            dest.writeByte((byte) (selected ? 1 : 0));
            dest.writeString(type);
            dest.writeString(selectedValue);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ControlsBean> CREATOR = new Creator<ControlsBean>() {
            @Override
            public ControlsBean createFromParcel(Parcel in) {
                return new ControlsBean(in);
            }

            @Override
            public ControlsBean[] newArray(int size) {
                return new ControlsBean[size];
            }
        };

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSelectedValue() {
            return selectedValue;
        }

        public void setSelectedValue(String selectedValue) {
            this.selectedValue = selectedValue;
        }
    }
}
