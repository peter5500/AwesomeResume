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
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.awesomeresume.model.Education;
import com.example.peter.awesomeresume.util.DateUtils;
import com.example.peter.awesomeresume.util.EditBaseActivity;

import java.util.Arrays;


public class EducationActivityEdit extends EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    protected void setupUIForEdit(@NonNull final Education data) {
        //show the data on the UI
        ((TextView) findViewById(R.id.education_edit_school)).setText(data.school);
        ((TextView) findViewById(R.id.education_edit_major)).setText(data.major);
        ((TextView) findViewById(R.id.education_edit_start_date)).setText(
                DateUtils.dateToString(data.startDate));
        ((TextView) findViewById(R.id.education_edit_end_date)).setText(
                DateUtils.dateToString(data.endDate));
        ((TextView) findViewById(R.id.education_edit_courses)).setText(
                TextUtils.join("\n", data.courses));

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }


    protected void saveAndExit(@Nullable Education data) {
        //if null need to create new id
        if (data == null){
            data = new Education();
        }

        //if not null, won't change ID, and edit its content
        data.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        data.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        data.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        data.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        data.courses = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.education_edit_courses)).getText().toString(),"\n"));

        //collect the data and send back to main activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION,data);
        setResult(RESULT_OK,resultIntent);

        finish();
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }

}
