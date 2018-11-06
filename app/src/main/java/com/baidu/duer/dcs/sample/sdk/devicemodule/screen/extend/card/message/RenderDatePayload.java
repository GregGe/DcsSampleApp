package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

public class RenderDatePayload extends TokenPayload {
    public String datetime;
    public String timeZoneName;
    public String day;

    public RenderDatePayload() {

    }

    protected RenderDatePayload(Parcel in) {
        super(in);
        datetime = in.readString();
        timeZoneName = in.readString();
        day = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(datetime);
        dest.writeString(timeZoneName);
        dest.writeString(day);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderDatePayload> CREATOR = new Creator<RenderDatePayload>() {
        @Override
        public RenderDatePayload createFromParcel(Parcel in) {
            return new RenderDatePayload(in);
        }

        @Override
        public RenderDatePayload[] newArray(int size) {
            return new RenderDatePayload[size];
        }
    };
}
