package com.baidu.duer.dcs.sample.sdk.devicemodule.screen.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.duer.dcs.sample.sdk.devicemodule.screen.TokenPayload;

import java.io.Serializable;
import java.util.List;

public class RenderCardPayload extends TokenPayload {
    public Type type;
    public String title;
    public String content;
    public ImageStructure image;
    public LinkStructure link;
    public List<ListItem> list;
    public List<ImageStructure> imageList;

    public enum Type {
        TextCard,
        StandardCard,
        ListCard,
        ImageListCard,
    }

    public static final class LinkStructure implements Parcelable, Serializable {
        public String url;
        public String anchorText;

        public LinkStructure() {

        }

        protected LinkStructure(Parcel in) {
            url = in.readString();
            anchorText = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(anchorText);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<LinkStructure> CREATOR = new Creator<LinkStructure>() {
            @Override
            public LinkStructure createFromParcel(Parcel in) {
                return new LinkStructure(in);
            }

            @Override
            public LinkStructure[] newArray(int size) {
                return new LinkStructure[size];
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

    public static final class ListItem implements Parcelable, Serializable {
        public String title;
        public String content;
        public ImageStructure image;
        public String url;

        public ListItem() {

        }

        protected ListItem(Parcel in) {
            title = in.readString();
            content = in.readString();
            image = in.readParcelable(ImageStructure.class.getClassLoader());
            url = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(content);
            dest.writeParcelable(image, flags);
            dest.writeString(url);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
            @Override
            public ListItem createFromParcel(Parcel in) {
                return new ListItem(in);
            }

            @Override
            public ListItem[] newArray(int size) {
                return new ListItem[size];
            }
        };
    }

    public RenderCardPayload() {
    }

    protected RenderCardPayload(Parcel in) {
        super(in);
        type = Type.values()[in.readInt()];
        title = in.readString();
        content = in.readString();
        image = in.readParcelable(ImageStructure.class.getClassLoader());
        link = in.readParcelable(LinkStructure.class.getClassLoader());
        list = in.createTypedArrayList(ListItem.CREATOR);
        imageList = in.createTypedArrayList(ImageStructure.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(type.ordinal());
        dest.writeString(title);
        dest.writeString(content);
        dest.writeParcelable(image, flags);
        dest.writeParcelable(link, flags);
        dest.writeTypedList(list);
        dest.writeTypedList(imageList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RenderCardPayload> CREATOR = new Creator<RenderCardPayload>() {
        @Override
        public RenderCardPayload createFromParcel(Parcel in) {
            return new RenderCardPayload(in);
        }

        @Override
        public RenderCardPayload[] newArray(int size) {
            return new RenderCardPayload[size];
        }
    };
}
