package com.juanma.greennews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

//Model class for the Sources JSON query that contains some essential information

public class Sources

{
    private String id;
    private String name;

    public Sources(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Sources(Parcel in) {
        id = in.readString();
        name = in.readString();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Sources{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
