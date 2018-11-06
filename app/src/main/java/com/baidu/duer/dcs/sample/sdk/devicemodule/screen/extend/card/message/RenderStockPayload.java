package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.extend.card.message;

import android.os.Parcel;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

public class RenderStockPayload extends TokenPayload {
    public String token;
    public double changeInPrice;
    public double changeInPercentage;
    public double marketPrice;
    public String marketStatus;
    public String marketName;
    public String name;
    public String datetime;
    public double openPrice;
    public double previousClosePrice;
    public double dayHighPrice;
    public double dayLowPrice;
    public double priceEarningRatio;
    public long marketCap;
    public long dayVolume;

    public RenderStockPayload() {

    }

    protected RenderStockPayload(Parcel in) {
        super(in);
        token = in.readString();
        changeInPrice = in.readDouble();
        changeInPercentage = in.readDouble();
        marketPrice = in.readDouble();
        marketStatus = in.readString();
        marketName = in.readString();
        name = in.readString();
        datetime = in.readString();
        openPrice = in.readDouble();
        previousClosePrice = in.readDouble();
        dayHighPrice = in.readDouble();
        dayLowPrice = in.readDouble();
        priceEarningRatio = in.readDouble();
        marketCap = in.readLong();
        dayVolume = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(token);
        dest.writeDouble(changeInPrice);
        dest.writeDouble(changeInPercentage);
        dest.writeDouble(marketPrice);
        dest.writeString(marketStatus);
        dest.writeString(marketName);
        dest.writeString(name);
        dest.writeString(datetime);
        dest.writeDouble(openPrice);
        dest.writeDouble(previousClosePrice);
        dest.writeDouble(dayHighPrice);
        dest.writeDouble(dayLowPrice);
        dest.writeDouble(priceEarningRatio);
        dest.writeLong(marketCap);
        dest.writeLong(dayVolume);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderStockPayload> CREATOR = new Creator<RenderStockPayload>() {
        @Override
        public RenderStockPayload createFromParcel(Parcel in) {
            return new RenderStockPayload(in);
        }

        @Override
        public RenderStockPayload[] newArray(int size) {
            return new RenderStockPayload[size];
        }
    };
}
