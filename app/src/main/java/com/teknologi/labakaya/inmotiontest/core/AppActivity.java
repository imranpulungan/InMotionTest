package com.teknologi.labakaya.inmotiontest.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.teknologi.labakaya.inmotiontest.R;

public class AppActivity extends AppCompatActivity {
    protected Boolean enableBackButton;
    protected int resLayoutId;

    public AppActivity(int resLayoutId){
        this.enableBackButton = false;
        this.resLayoutId = resLayoutId;
    }

    public AppActivity(int resLayoutId, Boolean enableBackButton){
        this.enableBackButton = enableBackButton;
        this.resLayoutId = resLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.resLayoutId);

        if (this.enableBackButton) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void doShowMessage(int message, final Context context, final Class<?> activityClass, int actionText) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void doFail(int message) {
        doShowMessage(message, null, null, R.string.ok );
    }

    public void doConnectionError() {
        doShowMessage(R.string.network_error, null, null, R.string.ok);
    }

    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
