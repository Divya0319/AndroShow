package com.fastturtle.androshow.staticclasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * @Author: Divya Gupta
 * @Date: 30-Dec-22
 */
public class Size2 implements Parcelable {
    private int width;
    private int height;

    public Size2(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static final Creator<Size2> CREATOR = new Creator<Size2>() {
        @Override
        public Size2 createFromParcel(Parcel in) {
            return new Size2(in);
        }

        @Override
        public Size2[] newArray(int size) {
            return new Size2[size];
        }
    };

    public Size2(Parcel parcelIn) {
        this(parcelIn.readInt(), parcelIn.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
    }

    @Override
    public String toString() {
        return "Size2{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
