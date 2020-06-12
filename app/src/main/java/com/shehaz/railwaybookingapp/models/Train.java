package com.shehaz.railwaybookingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Train implements Parcelable {

    private String trainNo;
    private String trainName;
    private String serviceDay;

    public Train(String trainNo, String trainName,String serviceDay) {
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.serviceDay = serviceDay;
    }

    protected Train(Parcel in)
    {
        this.trainNo = in.readString();
        this.trainName = in.readString();
        this.serviceDay = in.readString();
    }

    public static final Creator<Train> CREATOR = new Creator<Train>() {
        @Override
        public Train createFromParcel(Parcel in) {
            return new Train(in);
        }
        @Override
        public Train[] newArray(int size) {
            return new Train[size];
        }
    };

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getServiceDay() {
        return serviceDay;
    }

    public void setServiceDay(String serviceDay) {
        this.serviceDay = serviceDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trainNo);
        dest.writeString(trainName);
        dest.writeString(serviceDay);
    }
}