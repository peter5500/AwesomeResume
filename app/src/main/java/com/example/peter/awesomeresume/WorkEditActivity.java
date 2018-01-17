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

import com.example.peter.awesomeresume.model.Project;
import com.example.peter.awesomeresume.model.Work;
import com.example.peter.awesomeresume.util.DateUtils;
import com.example.peter.awesomeresume.util.EditBaseActivity;

import java.util.Arrays;

public class WorkEditActivity extends EditBaseActivity<Work> {

    public static final String KEY_WORK = "work";
    public static final String KEY_WORK_ID = "work_id";

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_work_edit);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        work = getIntent().getParcelableExtra(KEY_WORK);
//        if (work != null){
//            setupUI(work);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_edit, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home){
//            finish();
//            return true;
//        } else if (item.getItemId() == R.id.action_save){
//            saveAndExit();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void setupUI(Work work){
//        ((TextView) findViewById(R.id.work_edit_place)).setText(work.workPlace);
//        ((TextView) findViewById(R.id.work_edit_start_date)).setText(
//                DateUtils.dateToString(work.startDate));
//        ((TextView) findViewById(R.id.work_edit_end_date)).setText(
//                DateUtils.dateToString(work.endDate));
//        ((TextView) findViewById(R.id.work_edit_title)).setText(work.workTitle);
//        ((TextView) findViewById(R.id.work_edit_contents)).setText(
//                TextUtils.join("\n", work.contents));
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_edit;
    }

    @Override
    protected void setupUIForCreate() {
        findViewById(R.id.work_edit_delete).setVisibility(View.GONE);
    }
    @Override
    protected void setupUIForEdit(@NonNull final Work data) {
        ((EditText) findViewById(R.id.work_edit_place))
                .setText(data.workPlace);
        ((EditText) findViewById(R.id.work_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.work_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));
        ((EditText) findViewById(R.id.work_edit_title))
                .setText(data.workTitle);
        ((EditText) findViewById(R.id.work_edit_contents))
                .setText(TextUtils.join("\n", data.contents));
        findViewById(R.id.work_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_WORK_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    protected void saveAndExit(@Nullable Work data) {
        if (data == null){
            data = new Work();
        }
        data.workPlace =
                ((EditText) findViewById(R.id.work_edit_place)).getText().toString();
        data.startDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.work_edit_start_date)).getText().toString());
        data.endDate =
                DateUtils.stringToDate(((EditText) findViewById(R.id.work_edit_end_date)).getText().toString());
        data.workTitle =
                ((EditText) findViewById(R.id.work_edit_title)).getText().toString();
        data.contents = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.work_edit_contents)).getText().toString(),"\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_WORK, data);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @Override
    protected Work initializeData() {
        return getIntent().getParcelableExtra(KEY_WORK);
    }

}
