package com.sandyr.demo.gettyimages.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandyr on 7/14/2017.
 */

public class GettyImage implements Parcelable {
    private String id;
    private String title;
    private String caption;
    private List<DisplaySize> display_sizes;

    protected GettyImage(Parcel in) {
        id = in.readString();
        title = in.readString();
        caption = in.readString();
        display_sizes = new ArrayList<DisplaySize>();
        display_sizes = in.createTypedArrayList(DisplaySize.CREATOR);
    }

    public GettyImage() {
    }

    public static final Creator<GettyImage> CREATOR = new Creator<GettyImage>() {
        @Override
        public GettyImage createFromParcel(Parcel in) {
            return new GettyImage(in);
        }

        @Override
        public GettyImage[] newArray(int size) {
            return new GettyImage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<DisplaySize> getDisplay_size() {
        return display_sizes;
    }

    public void setDisplay_size(List<DisplaySize> display_size) {
        this.display_sizes = display_size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(caption);
        parcel.writeTypedList(display_sizes);
    }

}
