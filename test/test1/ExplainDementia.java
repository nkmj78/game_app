package edu.android.appgame.test.test1;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class ExplainDementia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_dementia);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorForest)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
    }

    public void btnStart(View view) {
        Intent intent = new Intent(this, test1MainActivity.class);
        startActivity(intent);
        finish();
    }
}
