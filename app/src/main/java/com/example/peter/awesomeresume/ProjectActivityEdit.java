package com.example.peter.awesomeresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.peter.awesomeresume.model.Project;
import com.example.peter.awesomeresume.util.DateUtils;
import com.example.peter.awesomeresume.util.EditBaseActivity;

import java.util.Arrays;

public class ProjectActivityEdit extends EditBaseActivity<Project> {

    public final static String KEY_PROJECT = "project";
    public static final String KEY_PROJECT_ID = "project_id";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project_edit);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        project = getIntent().getParcelableExtra(KEY_PROJECT);
//
//        //check if there are existing data need to be add in
//        if (project != null){
//            Toast.makeText(this, "editting", Toast.LENGTH_LONG).show();
//            setupUI(project);
//        }
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            finish();
//            return true;
//        }else if (item.getItemId() == R.id.action_save){
//            saveAndExit();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_project_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.project_edit_delete).setVisibility(View.GONE);
    }
    @Override
    protected void setupUIForEdit(@NonNull final Project data) {
        ((EditText) findViewById(R.id.project_edit_name))
                .setText(data.projectName);
        ((EditText) findViewById(R.id.project_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.project_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.project_edit_content))
                .setText(TextUtils.join("\n", data.contents));
        findViewById(R.id.project_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_PROJECT_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    protected void saveAndExit(@Nullable Project data) {
        if(data == null){
            data = new Project();
        }

        data.projectName = ((EditText) findViewById(R.id.project_edit_name)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_edit_end_date)).getText().toString());
        data.contents = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.project_edit_content)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_PROJECT, data);
        setResult(RESULT_OK,resultIntent);

        finish();
    }

    @Override
    protected Project initializeData() {
        return getIntent().getParcelableExtra(KEY_PROJECT);
    }
}



