package edu.android.appgame.game.game3;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import edu.android.appgame.GameActivity;
import edu.android.appgame.R;
import edu.android.appgame.game.GameDao;

import static edu.android.appgame.game.game3.MainGame3Activity.CORRECT_SCORE;
import static edu.android.appgame.game.game3.MainGame3Activity.TOTAL_Q;

public class ResultGame3Activity extends AppCompatActivity {


    public static final String GAME_NAME = "word";
    private static final String TAG = "file_tag";
    private int score;
    private int total;
    private TextView textCount, textTotal;
    private ImageView imageGrade;
    private int grade;
//    private String stringGrade;


    private GameDao dao = GameDao.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game3_result);

        textCount = findViewById(R.id.textCount);
        textTotal = findViewById(R.id.textTotal);
        imageGrade = findViewById(R.id.imageGrade);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDarkBlue)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));

        score = getIntent().getIntExtra(CORRECT_SCORE,0);
        total = getIntent().getIntExtra(TOTAL_Q,0);

        textCount.setText(score+"");
        textTotal.setText(total+"");

        calScore();

    }// end onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game3_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void calScore(){
        String stringGrade = "";
        grade = (score/total)*100;
        Log.i(TAG, "grade : " + grade);

        if(grade == 100) {
            imageGrade.setImageResource(R.drawable.game3_alphabet_a);
            stringGrade = "A";
        } else if(grade <=99 && grade > 80){
            imageGrade.setImageResource(R.drawable.game3_alphabet_b);
            stringGrade = "B";
        } else if(grade <= 80 && grade >60){
            imageGrade.setImageResource(R.drawable.game3_alphabet_c);
            stringGrade = "C";
        } else if (grade <= 60){
            imageGrade.setImageResource(R.drawable.game3_alphabet_d);
            stringGrade = "D";
        }

        dao.saveScoreToFileByGames(GAME_NAME, stringGrade);
    } // end calScore()

    public void onClickHome(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    } // end onClickHome()



    public void onClickRestart(View view) {
        Intent intent = new Intent(this, MainGame3Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    } // end onClickRestart()

} // end class ResultGame3Activity
