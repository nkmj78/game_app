package edu.android.appgame.game.game1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.android.appgame.R;

import static edu.android.appgame.game.game1.Main0Animal.ANIMAL_BEST;
import static edu.android.appgame.game.game1.Main0Animal.ANIMAL_SCORE;
import static edu.android.appgame.game.game1.Main0Korea.KOREA_BEST;
import static edu.android.appgame.game.game1.Main0Korea.KOREA_SCORE;
import static edu.android.appgame.game.game1.Main0Itwill.ITW_SCORE;
import static edu.android.appgame.game.game1.Main0Itwill.ITW_BEST;

public class Main0Activity extends AppCompatActivity {

    private String animal_score;
    private String animal_best;
    private String korea_score;
    private String korea_best;
    private String itw_score;
    private String itw_best;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_main);

        animal_best = getIntent().getStringExtra(ANIMAL_BEST);
        animal_score = getIntent().getStringExtra(ANIMAL_SCORE);
        korea_score = getIntent().getStringExtra(korea_score);
        korea_best = getIntent().getStringExtra(korea_best);
        itw_score = getIntent().getStringExtra(itw_score);
        itw_best = getIntent().getStringExtra(itw_best);


    }

    public void firstGame(View view) {
        finish();
       Intent intent = new Intent(this, Main0Animal.class);
       startActivity(intent);
    }
    public void twoGame(View view) {
        finish();
        Intent intent = new Intent(this, Main0Korea.class);
        startActivity(intent);
    }
    public void threeGame(View view) {
        finish();
        Intent intent = new Intent(this, Main0Itwill.class);
        startActivity(intent);
    }
    public void resultGame(View view) {
        finish();
        Intent intent = new Intent(this, Main0Result.class);
        startActivity(intent);
    }
}
