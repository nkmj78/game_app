package edu.android.appgame.test.test3;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class test3MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDarkBlue)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.lightBlue900));

        View view= findViewById(R.id.container2);
        if(view != null){
            if(savedInstanceState != null){

            }else{
                test3Fragment fragment = new test3Fragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();
            }
    }
    }
}
