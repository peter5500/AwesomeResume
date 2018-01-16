package com.example.peter.awesomeresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.peter.awesomeresume.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by peter on 2018/1/15.
 */

public class Work implements Parcelable{

    public String id;

    public String workPlace;

    public String workTitle;

    public Date startDate;

    public Date endDate;

    public List<String> contents;

    public Work(){
        id = UUID.randomUUID().toString();
    }

    protected Work(Parcel in) {
        id = in.readString();
        workPlace = in.readString();
        workTitle = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        contents = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(workPlace);
        dest.writeString(workTitle);
        dest.writeString(DateUtils.dateToString(startDate));
        dest.writeString(DateUtils.dateToString(endDate));
        dest.writeStringList(contents);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Work> CREATOR = new Creator<Work>() {
        @Override
        public Work createFromParcel(Parcel in) {
            return new Work(in);
        }

        @Override
        public Work[] newArray(int size) {
            return new Work[size];
        }
    };
}
