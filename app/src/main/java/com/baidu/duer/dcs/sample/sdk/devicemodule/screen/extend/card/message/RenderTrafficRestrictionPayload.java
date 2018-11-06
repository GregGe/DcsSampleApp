package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

import java.io.Serializable;
import java.util.List;

public class RenderTrafficRestrictionPayload extends TokenPayload {
    public String city;
    public String day;
    public String date;
    public String dateDescription;
    public String restrictionRule;
    public String todayRestriction;
    public String tomorrowRestriction;
    public List<Restriction> weekRestriction;

    public static final class Restriction implements Parcelable, Serializable {
        public String restriction;
        public String day;

        public Restriction() {

        }

        protected Restriction(Parcel in) {
            restriction = in.readString();
            day = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(restriction);
            dest.writeString(day);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Restriction> CREATOR = new Creator<Restriction>() {
            @Override
            public Restriction createFromParcel(Parcel in) {
                return new Restriction(in);
            }

            @Override
            public Restriction[] newArray(int size) {
                return new Restriction[size];
            }
        };
    }

    public RenderTrafficRestrictionPayload() {
    }

    protected RenderTrafficRestrictionPayload(Parcel in) {
        super(in);
        city = in.readString();
        day = in.readString();
        date = in.readString();
        dateDescription = in.readString();
        restrictionRule = in.readString();
        todayRestriction = in.readString();
        tomorrowRestriction = in.readString();
        weekRestriction = in.createTypedArrayList(Restriction.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(city);
        dest.writeString(day);
        dest.writeString(date);
        dest.writeString(dateDescription);
        dest.writeString(restrictionRule);
        dest.writeString(todayRestriction);
        dest.writeString(tomorrowRestriction);
        dest.writeTypedList(weekRestriction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderTrafficRestrictionPayload> CREATOR =
            new Creator<RenderTrafficRestrictionPayload>() {
                @Override
                public RenderTrafficRestrictionPayload createFromParcel(Parcel in) {
                    return new RenderTrafficRestrictionPayload(in);
                }

                @Override
                public RenderTrafficRestrictionPayload[] newArray(int size) {
                    return new RenderTrafficRestrictionPayload[size];
                }
            };
}
