package com.example.peter.awesomeresume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.peter.awesomeresume.model.Work;
import com.example.peter.awesomeresume.util.DateUtils;

import java.util.Arrays;

public class WorkEditActivity extends AppCompatActivity {

    public static final String KEY_WORK = "work";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_save){
            saveAndExit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAndExit() {
        Work work = new Work();
        work.workPlace = ((EditText) findViewById(R.id.work_edit_place)).getText().toString();
        work.workTitle = ((EditText) findViewById(R.id.work_edit_title)).getText().toString();
        work.workPlace = ((EditText) findViewById(R.id.work_edit_place)).getText().toString();
        work.startDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.work_edit_start_date)).getText().toString());
        work.endDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.work_edit_end_date)).getText().toString());
        work.contents = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.work_edit_contents)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_WORK, work);
        setResult(RESULT_OK, resultIntent);
    }

}
