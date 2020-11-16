package edu.android.appgame.test.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class Explain3Dementia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3_dementia);
    }

    public void btnStart(View view) {
        Intent intent = new Intent(this, test3MainActivity.class);
        startActivity(intent);
        finish();

    }
}
