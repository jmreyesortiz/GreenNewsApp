package com.juanma.greennews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Sources implements Parcelable

{
    private String id;
    private String name;

    public Sources(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sources() {
    }

    protected Sources(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<Sources> CREATOR = new Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel in) {
            return new Sources(in);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
    }

    @Override
    public String toString() {
        return "Sources{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
