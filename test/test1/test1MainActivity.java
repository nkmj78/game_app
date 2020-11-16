package edu.android.appgame.test.test1;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class test1MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorForest)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        View view= findViewById(R.id.container2);
        if(view != null){
            if(savedInstanceState != null){

            }else{
                test1Fragment fragment= new test1Fragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();
            }
        }
    }
}