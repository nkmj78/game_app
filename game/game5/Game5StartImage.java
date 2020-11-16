package edu.android.appgame.game.game5;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import edu.android.appgame.R;

public class Game5StartImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game5_start);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDarkBlue)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));
    }

    public void onClickGameStart(View view) {
        Intent intent= new Intent(this, Game5MainActivity.class);
        startActivity(intent);
    }
}
