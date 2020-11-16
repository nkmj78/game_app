package edu.android.appgame.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;
import edu.android.appgame.test.test2.Explain2Dementia;
import edu.android.appgame.test.test1.ExplainDementia;
import edu.android.appgame.test.test3.Explain3Dementia;
import edu.android.appgame.test.test3.test3MainActivity;


public class TestSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_select);
    }


    public void onClickDementia(View view) {
//        Intent intent = new Intent(this, ExplainDementia.class);
//        startActivity(intent);
//        finish();
        Intent intent = new Intent(this, ExplainDementia.class);
       startActivity(intent);
    }

    public void onClickMemory(View view) {
        Intent intent1 = new Intent(this, Explain2Dementia.class);
        startActivity(intent1);
    }

    public void onClickGloomy(View view) {
        Intent intent2 = new Intent (this, Explain3Dementia.class);
        startActivity(intent2);
    }
}
