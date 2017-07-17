package com.sandyr.demo.gettyimages.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sandyr on 7/17/2017.
 */

public class DisplaySize implements Parcelable {
    private String uri;

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public DisplaySize createFromParcel(Parcel in) {
            return new DisplaySize(in);
        }

        public DisplaySize[] newArray(int size) {
            return new DisplaySize[size];
        }
    };

    public String getUri() {
        return uri;
    }

    public DisplaySize() {
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uri);
    }

    public DisplaySize(Parcel in) {
        uri = in.readString();
    }
}
