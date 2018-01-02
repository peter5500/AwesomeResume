package com.example.peter.awesomeresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.awesomeresume.model.Education;
import com.example.peter.awesomeresume.util.DateUtils;
import java.util.Arrays;


public class EducationActivityEdit extends AppCompatActivity {

    public static final String KEY_EDUCATION = "education";
    private Education education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //check if there are existing data need to be add in
        education = getIntent().getParcelableExtra(KEY_EDUCATION);
        if (education != null){
            // will pop up if going to edit (education is not null)
            Toast.makeText(this, "editting", Toast.LENGTH_LONG).show();
            setupUI(education);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else if (item.getItemId() == R.id.action_save){
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupUI(Education education) {
        //show the data on the UI
        ((TextView) findViewById(R.id.education_edit_school)).setText(education.school);
        ((TextView) findViewById(R.id.education_edit_major)).setText(education.major);
        ((TextView) findViewById(R.id.education_edit_start_date)).setText(
                DateUtils.dateToString(education.startDate));
        ((TextView) findViewById(R.id.education_edit_end_date)).setText(
                DateUtils.dateToString(education.endDate));
        ((TextView) findViewById(R.id.education_edit_courses)).setText(
                TextUtils.join("\n", education.courses));
    }

    private void saveAndExit() {
        //if not null need to create new id
        if (education == null){
            education = new Education();
        }

        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        education.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        education.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        education.courses = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.education_edit_courses)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION,education);
        setResult(RESULT_OK,resultIntent);

        finish();
    }
}
