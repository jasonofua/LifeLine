package com.example.root.lifeline.models;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreModel implements Parcelable {


    public String name, address, distance, duration,idItem;



    public StoreModel(String name, String address, String distance, String duration) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.duration = duration;

    }

    protected StoreModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        distance = in.readString();
        duration = in.readString();
        idItem = in.readString();
    }

    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel(Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray(int size) {
            return new StoreModel[size];
        }
    };

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(distance);
        parcel.writeString(duration);
        parcel.writeString(idItem);
    }
}
