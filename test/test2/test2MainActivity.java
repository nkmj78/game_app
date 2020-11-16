package edu.android.appgame.test.test2;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

public class test2MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPink)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorSoftPink));

        View view= findViewById(R.id.container2);
        if(view != null){
            if(savedInstanceState != null){

            }else{
                test2Fragment fragment= new test2Fragment();
                getSupportFragmentManager().beginTransaction().add(R.id.container2, fragment).commit();
            }
        }
    }
}
