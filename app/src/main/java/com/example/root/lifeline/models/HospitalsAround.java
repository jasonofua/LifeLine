package com.example.root.lifeline.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 9/30/17.
 */

public class HospitalsAround implements Parcelable {
    String hospitalId;
    String hospitalName;
    String hospitalAddress;
    int phoneNumber;
    boolean isHospitalOpen;

    public HospitalsAround() {

    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public boolean isHospitalOpen() {
        return isHospitalOpen;
    }

    public void setHospitalOpen(boolean hospitalOpen) {
        isHospitalOpen = hospitalOpen;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public HospitalsAround(Parcel in) {
        hospitalId = in.readString();
        hospitalName = in.readString();
        hospitalAddress = in.readString();
        phoneNumber = in.readInt();
        isHospitalOpen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hospitalName);
        dest.writeString(hospitalAddress);
        dest.writeInt(phoneNumber);
        dest.writeString(hospitalId);
        dest.writeByte((byte) (isHospitalOpen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HospitalsAround> CREATOR = new Creator<HospitalsAround>() {
        @Override
        public HospitalsAround createFromParcel(Parcel in) {
            return new HospitalsAround(in);
        }

        @Override
        public HospitalsAround[] newArray(int size) {
            return new HospitalsAround[size];
        }
    };
}
