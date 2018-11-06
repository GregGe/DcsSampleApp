package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message;

import android.os.Parcel;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

import java.util.List;

public class RenderHintPayload extends TokenPayload {
    public List<String> cueWords;

    public RenderHintPayload() {

    }

    protected RenderHintPayload(Parcel in) {
        super(in);
        cueWords = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeStringList(cueWords);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderHintPayload> CREATOR = new Creator<RenderHintPayload>() {
        @Override
        public RenderHintPayload createFromParcel(Parcel in) {
            return new RenderHintPayload(in);
        }

        @Override
        public RenderHintPayload[] newArray(int size) {
            return new RenderHintPayload[size];
        }
    };
}
