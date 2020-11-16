package edu.android.appgame.test.test2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class Explain2Dementia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_dementia);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPink)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorSoftPink));

    }

    public void btnStart(View view) {
        Intent intent = new Intent(this, test2MainActivity.class);
        startActivity(intent);
        finish();

    }
}
