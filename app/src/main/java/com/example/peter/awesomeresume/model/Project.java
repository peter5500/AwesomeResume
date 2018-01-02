package com.example.peter.awesomeresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.peter.awesomeresume.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by peter on 2018/1/1.
 */

//implements to let its data to be separated
public class Project implements Parcelable{
    public String id;

    public String projectName;

    public Date startDate;

    public Date endDate;

    public List<String> contents;

    //each constructor already create its id
    public Project(){
        //use the ID to determine whether need to edit or create
        id = UUID.randomUUID().toString();
    }

    protected Project(Parcel in) {
        id = in.readString();
        projectName = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        contents = in.createStringArrayList();
    }


    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(projectName);
        parcel.writeString(DateUtils.dateToString(startDate));
        parcel.writeString(DateUtils.dateToString(endDate));
        parcel.writeStringList(contents);
    }
}

