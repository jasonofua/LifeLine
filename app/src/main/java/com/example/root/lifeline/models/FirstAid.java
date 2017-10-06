package com.example.root.lifeline.models;


import android.os.Parcel;
import android.os.Parcelable;

public class FirstAid implements Parcelable {
    String id;
    String firstAidName;
    int firstAidImage;



    public FirstAid(String firstAidName, int firstAidImage) {
        this.firstAidName = firstAidName;
        this.firstAidImage = firstAidImage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstAidName() {
        return firstAidName;
    }

    public void setFirstAidName(String firstAidName) {
        this.firstAidName = firstAidName;
    }

    public int getFirstAidImage() {
        return firstAidImage;
    }

    public void setFirstAidImage(int firstAidImage) {
        this.firstAidImage = firstAidImage;
    }

    private FirstAid(Parcel in) {
        id = in.readString();
        firstAidImage = in.readInt();
        firstAidName = in.readString();
    }

    public static final Creator<FirstAid> CREATOR = new Creator<FirstAid>() {
        @Override
        public FirstAid createFromParcel(Parcel in) {
            return new FirstAid(in);
        }

        @Override
        public FirstAid[] newArray(int size) {

            return new FirstAid[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeInt(firstAidImage);
        parcel.writeString(firstAidName);
    }
}
