package edu.android.appgame;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startLoading();
    } // end onCreate()

    private void startLoading() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, 2000); // 2초
    } // end startLoading()
} // end class LoadingActivity
