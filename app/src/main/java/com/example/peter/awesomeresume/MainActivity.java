package com.example.peter.awesomeresume;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.peter.awesomeresume.model.BasicInfo;
import com.example.peter.awesomeresume.model.Education;
import com.example.peter.awesomeresume.model.Project;
import com.example.peter.awesomeresume.model.Work;
import com.example.peter.awesomeresume.util.DateUtils;
import com.example.peter.awesomeresume.util.ModelUtils;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final int REQ_CODE_PROJECT_EDIT = 101;
    private static final int REQ_CODE_WORK_EDIT = 102;

    private static final String MODEL_EDUCATIONS = "educations";

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Project> projects;
    private List<Work> works;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        fakeData();
        setupUI();
    }

    private void loadData() {
        List<Education> savedEducation = ModelUtils.read(this,
                MODEL_EDUCATIONS,
                new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //check if the data is get back from the education_edit
                case REQ_CODE_EDUCATION_EDIT:
                    String educationId = data.getStringExtra(EducationActivityEdit.KEY_EDUCATION_ID);
                    if (educationId != null) {
                        deleteEducation(educationId);
                    } else {
                        Education education = data.getParcelableExtra(EducationActivityEdit.KEY_EDUCATION);
                        updateEducation(education);
                    }
                    break;

                case REQ_CODE_PROJECT_EDIT:
                    Project resultP = data.getParcelableExtra(ProjectActivityEdit.KEY_PROJECT);

                    boolean PisUpdate = false;
                    for (int i = 0; i < projects.size(); i++) {
                        Project project = projects.get(i);
                        if (project.id.equals(resultP.id)) {
                            projects.set(i, resultP);
                            PisUpdate = true;
                            break;
                        }
                    }

                    if (!PisUpdate) {
                        projects.add(resultP);
                    }

                    setUpProjectsUI();
                    break;

                case REQ_CODE_WORK_EDIT:
                    Work resultW = data.getParcelableExtra(WorkEditActivity.KEY_WORK);

                    boolean WisUpdate = false;
                    for (int i = 0; i < works.size(); i++) {
                        Work work = works.get(i);
                        if (work.id.equals(resultW.id)) {
                            works.set(i, resultW);
                            WisUpdate = true;
                            break;
                        }
                    }

                    if (!WisUpdate) {
                        works.add(resultW);
                    }
                    setUpWorksUI();
                    break;
            }
        }
    }

    private void setupUI(){
        setContentView(R.layout.activity_main);

        ImageButton addEducationBtn = (ImageButton) findViewById(R.id.add_education_btn);
        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationActivityEdit.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        findViewById(R.id.add_project_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProjectActivityEdit.class);
                startActivityForResult(intent, REQ_CODE_PROJECT_EDIT);
            }
        });

        findViewById(R.id.add_working_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WorkEditActivity.class);
                startActivityForResult(intent, REQ_CODE_WORK_EDIT);
            }
        });

        setupBasicInfoUI();
        setupEducations();
        setUpProjectsUI();
        setUpWorksUI();
    }

    private void setupBasicInfoUI(){
        ((TextView) findViewById(R.id.name)).setText(basicInfo.name);
        ((TextView) findViewById(R.id.mail)).setText(basicInfo.mail);
    }

//    private void setupEducationsUI(){
//        LinearLayout educationsContainer = findViewById(R.id.educations_container);
//        educationsContainer.removeAllViews(); //avoid to add duplicate data
//        for (final Education education : educations){
//            //將布局文件(education_item)轉成view, 數據轉入介面
//            View view = getLayoutInflater().inflate(R.layout.education_item,null);
//            String timeSpan =  "(" + DateUtils.dateToString(education.startDate) + " ~ "
//                    + DateUtils.dateToString(education.endDate) + ")";
//            ((TextView) view.findViewById(R.id.education_school)).setText(education.school + timeSpan);
//            ((TextView) view.findViewById(R.id.education_courses)).setText(formatItems(education.courses));
//
//            view.findViewById(R.id.edit_education_btn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //put the exist data into education_edit page
//                    Intent intent = new Intent(MainActivity.this,EducationActivityEdit.class);
//                    intent.putExtra(EducationActivityEdit.KEY_EDUCATION,education);
//                    startActivityForResult(intent,REQ_CODE_EDUCATION_EDIT); //code that shows back from education_edit
//                }
//            });
//
//            //將內存中的view添加到已經存在介面上的容器
//            educationsContainer.addView(view);
//        }
//    }

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

    private void setUpWorksUI() {
        LinearLayout worksContainer = findViewById(R.id.works_container);
        worksContainer.removeAllViews();
        for (final Work work : works){
            View view = getLayoutInflater().inflate(R.layout.work_item, null);
            String timeSpan = "(" + DateUtils.dateToString(work.startDate) + " ~ "
                    + DateUtils.dateToString(work.endDate) + ")";
            ((TextView) view.findViewById(R.id.work_place)).setText(work.workPlace + timeSpan);
            ((TextView) view.findViewById(R.id.work_title)).setText(work.workTitle);
            ((TextView) view.findViewById(R.id.work_contents)).setText(formatItems(work.contents));

            view.findViewById(R.id.edit_work_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, WorkEditActivity.class);
                    intent.putExtra(WorkEditActivity.KEY_WORK, work);
                    startActivityForResult(intent, REQ_CODE_WORK_EDIT);
                }
            });
            worksContainer.addView(view);
        }
    }




    private void fakeData(){
        basicInfo = new BasicInfo();
        basicInfo.name = "Peter Cheng";
        basicInfo.mail = "peter55005050@gmail.com";

//        educations = new ArrayList<>();
//        Education education = new Education();
//        education.school = "YZU";
//        education.major = "Photonics Engineering";
//        education.startDate = DateUtils.stringToDate("09/2011");
//        education.endDate = DateUtils.stringToDate("06/2015");
//        education.courses = new ArrayList<>();
//        education.courses.add("Introduction to Computer Science");
//        education.courses.add("Data Structure");
//
//        Education education2 = new Education();
//        education2.school = "NEU";
//        education2.major = "Information Systems";
//        education2.startDate = DateUtils.stringToDate("09/2017");
//        education2.endDate = DateUtils.stringToDate("06/2019");
//        education2.courses = new ArrayList<>();
//        education2.courses.add("Database");
//        education2.courses.add("WebTool");

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

//        educations.add(education);
//        educations.add(education2);

        projects.add(project);
        projects.add(project2);

        works = new ArrayList<>();
        Work work = new Work();
        work.workPlace = "National ChengChi University";
        work.startDate = DateUtils.stringToDate("09/2016");
        work.endDate = DateUtils.stringToDate("08/2017");
        work.workTitle = "Assistant Software Engineer";
        work.contents = new ArrayList<>();
        work.contents.add("Accomplished the software applications’ test cases for the APPs and websites that developed by the department, and reported testing process and results to improve their performance.");
        work.contents.add("Participated in designing APPs related to 3D printing project, among which the APP “Qmodel Creator” has been launched in AppStore and Google Play.");

        works.add(work);


    }

    private void updateEducation(Education education) {
        boolean found = false;
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, education.id)) {
                found = true;
                educations.set(i, education);
                break;
            }
        }

        if (!found) {
            educations.add(education);
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void deleteEducation(@NonNull String educationId) {
        for (int i = 0; i < educations.size(); ++i) {
            Education e = educations.get(i);
            if (TextUtils.equals(e.id, educationId)) {
                educations.remove(i);
                break;
            }
        }

        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducations();
    }

    private void setupEducations() {
        LinearLayout educationsLayout = (LinearLayout) findViewById(R.id.educations_container);
        educationsLayout.removeAllViews();
        for (Education education : educations) {
            View educationView = getLayoutInflater().inflate(R.layout.education_item, null);
            setupEducations(educationView, education);
            educationsLayout.addView(educationView);
        }
    }

    private void setupEducations(View educationView, final Education education) {
        String dateString = DateUtils.dateToString(education.startDate)
                + " ~ " + DateUtils.dateToString(education.endDate);
        ((TextView) educationView.findViewById(R.id.education_school))
                .setText(education.school + " " + education.major + " (" + dateString + ")");
        ((TextView) educationView.findViewById(R.id.education_courses))
                .setText(formatItems(education.courses));

        ImageButton editEducationBtn = (ImageButton) educationView.findViewById(R.id.edit_education_btn);
        editEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationActivityEdit.class);
                intent.putExtra(EducationActivityEdit.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
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
