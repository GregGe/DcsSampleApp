package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

import java.io.Serializable;
import java.util.List;

public class RenderWeatherPayload extends TokenPayload {
    public String token;
    public String city;
    public String askingDay;
    public String askingDate;
    public String askingDateDescription;
    public List<WeatherForecast> weatherForecast;

    public static final class WeatherForecast implements Parcelable, Serializable {
        public ImageStructure weatherIcon;
        public String highTemperature;
        public String lowTemperature;
        public String day;
        public String date;
        public String weatherCondition;
        public String windCondition;
        public String currentTemperature;
        public String currentPM25;
        public String currentAirQuality;

        public WeatherForecast() {

        }

        protected WeatherForecast(Parcel in) {
            weatherIcon = in.readParcelable(ImageStructure.class.getClassLoader());
            highTemperature = in.readString();
            lowTemperature = in.readString();
            day = in.readString();
            date = in.readString();
            weatherCondition = in.readString();
            windCondition = in.readString();
            currentTemperature = in.readString();
            currentPM25 = in.readString();
            currentAirQuality = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(weatherIcon, flags);
            dest.writeString(highTemperature);
            dest.writeString(lowTemperature);
            dest.writeString(day);
            dest.writeString(date);
            dest.writeString(weatherCondition);
            dest.writeString(windCondition);
            dest.writeString(currentTemperature);
            dest.writeString(currentPM25);
            dest.writeString(currentAirQuality);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<WeatherForecast> CREATOR = new Creator<WeatherForecast>() {
            @Override
            public WeatherForecast createFromParcel(Parcel in) {
                return new WeatherForecast(in);
            }

            @Override
            public WeatherForecast[] newArray(int size) {
                return new WeatherForecast[size];
            }
        };
    }

    public static final class ImageStructure implements Parcelable, Serializable {
        public String src;

        public ImageStructure() {

        }

        protected ImageStructure(Parcel in) {
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

        public static final Creator<ImageStructure> CREATOR = new Creator<ImageStructure>() {
            @Override
            public ImageStructure createFromParcel(Parcel in) {
                return new ImageStructure(in);
            }

            @Override
            public ImageStructure[] newArray(int size) {
                return new ImageStructure[size];
            }
        };
    }

    public RenderWeatherPayload() {

    }

    protected RenderWeatherPayload(Parcel in) {
        super(in);
        token = in.readString();
        city = in.readString();
        askingDay = in.readString();
        askingDate = in.readString();
        askingDateDescription = in.readString();
        weatherForecast = in.createTypedArrayList(WeatherForecast.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(token);
        dest.writeString(city);
        dest.writeString(askingDay);
        dest.writeString(askingDate);
        dest.writeString(askingDateDescription);
        dest.writeTypedList(weatherForecast);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderWeatherPayload> CREATOR = new Creator<RenderWeatherPayload>() {
        @Override
        public RenderWeatherPayload createFromParcel(Parcel in) {
            return new RenderWeatherPayload(in);
        }

        @Override
        public RenderWeatherPayload[] newArray(int size) {
            return new RenderWeatherPayload[size];
        }
    };
}
