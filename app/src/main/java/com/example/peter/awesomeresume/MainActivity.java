package com.example.peter.awesomeresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.peter.awesomeresume.model.BasicInfo;
import com.example.peter.awesomeresume.model.Education;
import com.example.peter.awesomeresume.util.DateUtils;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static int REQ_CODE_EDUCATION_EDIT = 100;
    private BasicInfo basicInfo;
    private List<Education> educations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fakeData();
        setupUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check if the data is get back from the education_edit
        if (resultCode == RESULT_OK && requestCode == REQ_CODE_EDUCATION_EDIT){
            Education result = data.getParcelableExtra(EducationActivityEdit.KEY_EDUCATION);

            //edit data
            boolean isUpdate = false;
            for (int i = 0; i < educations.size(); i++) {
                Education education = educations.get(i);
                if(education.id.equals(result.id)){
                    educations.set(i, result);
                    isUpdate = true;
                    break;
                }
            }
            //create data
            if(!isUpdate){
                //add the new data on the UI
                educations.add(result);
            }

                setupEducationsUI();
        }
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationActivityEdit.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        setupBasicInfoUI();
        setupEducationsUI();
    }

    private void setupBasicInfoUI(){
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.mail)).setText(basicInfo.mail);
    }

    private void setupEducationsUI(){
        LinearLayout educationsContainer = (LinearLayout)findViewById(R.id.educations_container);
        educationsContainer.removeAllViews(); //avoid to add duplicate data
        for(final Education education : educations){
            //將布局文件(education_item)轉成view
            View view = getLayoutInflater().inflate(R.layout.education_item,null);
            String timeSpan =  "(" + DateUtils.dateToString(education.startDate) + " ~ "
                    + DateUtils.dateToString(education.endDate) + ")";
            ((TextView) view.findViewById(R.id.education_school)).setText(education.school + timeSpan);
            ((TextView) view.findViewById(R.id.education_courses)).setText(formatItems(education.courses));

            view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //put the exist data into education_edit page
                    Intent intent = new Intent(MainActivity.this,EducationActivityEdit.class);
                    intent.putExtra(EducationActivityEdit.KEY_EDUCATION,education);
                    startActivityForResult(intent,REQ_CODE_EDUCATION_EDIT);
                }
            });

            //將內存中的view添加到已經存在介面上的容器
            educationsContainer.addView(view);
        }
    }

    private void fakeData(){
        basicInfo = new BasicInfo();
        basicInfo.name = "Peter Cheng";
        basicInfo.mail = "peter55005050@gmail.com";

        educations = new ArrayList<>();
        Education education = new Education();
        education.school = "YZU";
        education.major = "Photonics Engineering";
        education.startDate = DateUtils.stringToDate("09/2011");
        education.endDate = DateUtils.stringToDate("06/2015");
        education.courses = new ArrayList<>();
        education.courses.add("Introduction to Computer Science");
        education.courses.add("Data Structure");

        Education education2 = new Education();
        education2.school = "NEU";
        education2.major = "Information Systems";
        education2.startDate = DateUtils.stringToDate("09/2017");
        education2.endDate = DateUtils.stringToDate("06/2019");
        education2.courses = new ArrayList<>();
        education2.courses.add("Database");
        education2.courses.add("WebTool");

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
