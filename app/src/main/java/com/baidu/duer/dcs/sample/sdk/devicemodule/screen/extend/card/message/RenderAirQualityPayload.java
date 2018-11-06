package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

public class RenderAirQualityPayload extends TokenPayload {
    public String city;
    public String currentTemperature;
    public String pm25;
    public String airQuality;
    public String day;
    public String date;
    public String dateDescription;
    public String tips;

    public RenderAirQualityPayload() {
    }

    protected RenderAirQualityPayload(Parcel in) {
        super(in);
        city = in.readString();
        currentTemperature = in.readString();
        pm25 = in.readString();
        airQuality = in.readString();
        day = in.readString();
        date = in.readString();
        dateDescription = in.readString();
        tips = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(city);
        dest.writeString(currentTemperature);
        dest.writeString(pm25);
        dest.writeString(airQuality);
        dest.writeString(day);
        dest.writeString(date);
        dest.writeString(dateDescription);
        dest.writeString(tips);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderAirQualityPayload> CREATOR = new Creator<RenderAirQualityPayload>() {
        @Override
        public RenderAirQualityPayload createFromParcel(Parcel in) {
            return new RenderAirQualityPayload(in);
        }

        @Override
        public RenderAirQualityPayload[] newArray(int size) {
            return new RenderAirQualityPayload[size];
        }
    };
}
