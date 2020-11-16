package edu.android.appgame.game.game3;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.zip.Inflater;

import edu.android.appgame.R;

public class Main2Game3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3_main2);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDarkBlue)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));

    }



    public void onClickGameStart(View view) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("색 인지 게임");
        alertDialog.setMessage("왼쪽 스케치북에 쓰인 글자가 의미하는 색이" +
                "\n오른쪽 스케치북에 쓰인 글자 색이어야 맞는 게임" +
                "\n맞으면 O  틀리면 X")
                .setCancelable(false)
                .setPositiveButton("게임 시작",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                Intent intent = new Intent(Main2Game3Activity.this, MainGame3Activity.class);
                                startActivity(intent);
                            }
                        });

        alertDialog.create();
        alertDialog.show();



    } // end onClickGameStart()

} // end class Main2Game3Activity
