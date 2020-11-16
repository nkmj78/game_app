package edu.android.appgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.android.appgame.game.game1.Main0Activity;
import edu.android.appgame.game.game2.MainActivity;
import edu.android.appgame.game.game3.Main2Game3Activity;
import edu.android.appgame.game.game4.MainGame4Activity;
import edu.android.appgame.game.game5.Game5MainActivity;
import edu.android.appgame.game.game5.Game5StartImage;
import edu.android.appgame.game.game7.MainActivityGame7;

import static edu.android.appgame.GameActivity.KEY_GAME_INDEX;

public class GameDetailActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        if(savedInstanceState == null) {
            Intent intent = getIntent();
            position = intent.getIntExtra(KEY_GAME_INDEX, 0);

            switch (position) {
                case 0:
                    Intent intent0 = new Intent(this, Main0Activity.class);
                    startActivity(intent0);
                    finish();
                    break;
                case 1:
                    Intent intent1 = new Intent(this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case 2:
                    Intent intent2 = new Intent(this, Main2Game3Activity.class);
                    startActivity(intent2);
                    finish();
                    break;
                case 3:
                    Intent intent3 = new Intent(this, MainGame4Activity.class);
                    startActivity(intent3);
                    finish();
                    break;
                case 4:
                    Intent intent4 = new Intent(this, Game5StartImage.class);
                    startActivity(intent4);
                    finish();
                    break;

                case 5:
                    Intent intent6 = new Intent(this, MainActivityGame7.class);
                    startActivity(intent6);
                    finish();
                    break;
            } // end switch
        } // end if

    } // end onCreate()
} // end class GameDetailActivity
