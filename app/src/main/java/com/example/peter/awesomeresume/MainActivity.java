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
import com.example.peter.awesomeresume.model.Project;
import com.example.peter.awesomeresume.util.DateUtils;



import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static int REQ_CODE_EDUCATION_EDIT = 100;
    private static int REQ_CODE_PROJECT_EDIT = 101 ;
    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Project> projects;

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

        if (resultCode == RESULT_OK && requestCode == REQ_CODE_PROJECT_EDIT){
            Project resultP = data.getParcelableExtra(ProjectActivityEdit.KEY_PROJECT);

            boolean isUpdate = false;
            for (int i = 0; i < projects.size(); i++) {
                Project project = projects.get(i);
                if(project.id.equals(resultP.id)){
                    projects.set(i, resultP);
                    isUpdate = true;
                    break;
                }
            }

            if(!isUpdate){
                projects.add(resultP);
            }

            setUpProjectsUI();
        }
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);

        findViewById(R.id.add_education_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationActivityEdit.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);  //get the result
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectActivityEdit.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        setupBasicInfoUI();
        setupEducationsUI();
        setUpProjectsUI();
    }

    private void setupBasicInfoUI(){
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.mail)).setText(basicInfo.mail);
    }

    private void setupEducationsUI(){
        LinearLayout educationsContainer = findViewById(R.id.educations_container);
        educationsContainer.removeAllViews(); //avoid to add duplicate data
        for (final Education education : educations){
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

    private void setUpProjectsUI() {
        LinearLayout projectsContainer = findViewById(R.id.projects_container);
        projectsContainer.removeAllViews();
        for (final Project project : projects) {
             View view = getLayoutInflater().inflate(R.layout.project_item, null);
            String timeSpan = "(" + DateUtils.dateToString(project.startDate) + " ~ "
                    + DateUtils.dateToString(project.endDate) + ")";
            ((TextView) view.findViewById(R.id.project_name)).setText(project.projectName + timeSpan);
            ((TextView) view.findViewById(R.id.project_contents)).setText(formatItems(project.contents));

            view.findViewById(R.id.edit_project_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProjectActivityEdit.class);
                    intent.putExtra(ProjectActivityEdit.KEY_PROJECT, project);
                    startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
                }
            });

            //將內存中的view添加到已經存在介面上的容器
            projectsContainer.addView(view);

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

        projects = new ArrayList<>();
        Project project = new Project();
        project.projectName = "WebTool";
        project.startDate = DateUtils.stringToDate("09/2017");
        project.endDate = DateUtils.stringToDate("12/2017");
        project.contents = new ArrayList<>();
        project.contents.add("Develop the website");
        project.contents.add("Design the UI");

        Project project2 = new Project();
        project2.projectName = "Database";
        project2.startDate = DateUtils.stringToDate("05/2017");
        project2.endDate = DateUtils.stringToDate("07/2017");
        project2.contents = new ArrayList<>();
        project2.contents.add("Collect the data");
        project2.contents.add("Analyze the data");

        educations.add(education);
        educations.add(education2);

        projects.add(project);
        projects.add(project2);

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
