package com.example.peter.awesomeresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.peter.awesomeresume.model.Project;
import com.example.peter.awesomeresume.util.DateUtils;

import java.util.Arrays;

public class ProjectActivityEdit extends AppCompatActivity {

    public final static String KEY_PROJECT = "project";
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        project = getIntent().getParcelableExtra(KEY_PROJECT);

        //check if there are existing data need to be add in
        if (project != null){
            Toast.makeText(this, "editting", Toast.LENGTH_LONG).show();
            setupUI(project);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if (item.getItemId() == R.id.action_save){
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupUI(Project project) {
        //show the data
        ((TextView) findViewById(R.id.project_edit_name)).setText(project.projectName);
        ((TextView) findViewById(R.id.project_edit_start_date)).setText(
                DateUtils.dateToString(project.startDate));
        ((TextView) findViewById(R.id.project_edit_end_date)).setText(
                DateUtils.dateToString(project.endDate));
        ((TextView) findViewById(R.id.project_edit_content)).setText(
                TextUtils.join("\n", project.contents));
    }

    private void saveAndExit() {
        if(project == null){
            project = new Project();
        }

        project.projectName = ((EditText) findViewById(R.id.project_edit_name)).getText().toString();
        project.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_edit_start_date)).getText().toString());
        project.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.project_edit_end_date)).getText().toString());
        project.contents = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.project_edit_content)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_PROJECT, project);
        setResult(RESULT_OK,resultIntent);

        finish();
    }


}
