package edu.android.appgame.Prevention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.android.appgame.R;

import static edu.android.appgame.Prevention.PreventionMainActivity.KEY_PREVENTION_INDEX;
import static edu.android.appgame.GameActivity.KEY_GAME_INDEX;

public class PreventionDetail extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prevention_detail);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            position = intent.getIntExtra(KEY_PREVENTION_INDEX, 0);

            switch (position) {
                case 0:
                    Intent intent1 = new Intent(this, Prevention_1.class);
                    startActivity(intent1);
                    finish();
                    break;
                case 1:
                    Intent intent2 = new Intent(this, Prevention_2.class);
                    startActivity(intent2);
                    finish();
                    break;
                case 2:
                    Intent intent3 = new Intent(this, Prevention_3.class);
                    startActivity(intent3);
                    finish();
                    break;
                case 3:
                    Intent intent4 = new Intent(this, Prevention_4.class);
                    startActivity(intent4);
                    finish();
                    break;
                case 4:
                    Intent intent5 = new Intent(this, Prevention_5.class);
                    startActivity(intent5);
                    finish();
                    break;

            }
        }
    }
}