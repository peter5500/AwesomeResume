package com.example.peter.awesomeresume;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.peter.awesomeresume.model.BasicInfo;
import com.example.peter.awesomeresume.model.Education;
import com.example.peter.awesomeresume.util.DateUtils;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private BasicInfo basicInfo;
    private List<Education> educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fakeData();
        setupUI();
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);

        setupBasicInfoUI();
        setupEducationsUI();
    }

    private void setupBasicInfoUI(){
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.mail)).setText(basicInfo.mail);
    }

    private void setupEducationsUI(){
        LinearLayout educationsContainer = (LinearLayout) findViewById(R.id.educations_container);
        for(Education education : educations){
            //將布局文件(education_item)轉成view
            View view = getLayoutInflater().inflate(R.layout.education_item,null);
            String timeSpan =  "(" + DateUtils.dateToString(education.startDate) + " ~ "
                    + DateUtils.dateToString(education.endDate) + ")";
            ((TextView) view.findViewById(R.id.school)).setText(education.school + timeSpan);
            ((TextView) view.findViewById(R.id.courses)).setText(formatItems(education.courses));

            //將內存中的view添加到已經存在介面上的容器
            educationsContainer.addView(view);
        }


    }

    private void fakeData(){
        basicInfo = new BasicInfo();
        basicInfo.name = "Peter Cheng";
        basicInfo.mail = "peter55005050@gmail.com";

        Education education = new Education();
        education.school = "YZU";
        education.major = "Photonics Engineering";
        education.startDate = DateUtils.stringToDate("09/2011");
        education.endDate = DateUtils.stringToDate("06/2015");
        education.courses = new ArrayList<>();
        education.courses.add("Introduction to Computer Science");
        education.courses.add("Data Structure");

        Education education2 = new Education();
        education.school = "NEU";
        education.major = "Information Systems";
        education.startDate = DateUtils.stringToDate("09/2017");
        education.endDate = DateUtils.stringToDate("06/2019");
        education.courses = new ArrayList<>();
        education.courses.add("Database");
        education.courses.add("WebTool");

        educations = new ArrayList<>();
        educations.add(education);
        educations.add(education2);

    }

    private static String formatItems(List<String> items){
        StringBuilder sb = new StringBuilder();
        for(String item:items){
            sb.append(' ').append('-').append(' ').append(item).append('\n');
        }
        if(sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
