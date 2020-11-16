package edu.android.appgame.game.game2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.android.appgame.GameActivity;
import edu.android.appgame.R;
import edu.android.appgame.game.GameDao;

import static edu.android.appgame.game.game2.Main3Activity.SCORE_CHO;


public class Result3Activity extends AppCompatActivity {

    private TextView textResult;
    private Button btnRetry;
    SharedPreferences spf = null;
    private String scoreData; // 최종 점수
    private static final String GAME2_NAME = "card";
    private GameDao dao = GameDao.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_result3);

        textResult = findViewById(R.id.textResult);
        btnRetry = findViewById(R.id.btnRetry);
        spf = getSharedPreferences("spfScore", MODE_PRIVATE);

        int score_cho = getIntent().getIntExtra(SCORE_CHO,0);
        int score = 60 - score_cho;

        String time = "걸린 시간: " + score + "초\n\n";
        if (score > 0 && score <= 20) {
            textResult.setText(time + "기억력 점수: " + "A \n" + "기억력이 매우 뛰어나십니다!");
            scoreData = "A";
        } if (score > 20 && score <= 40) {
            textResult.setText(time + "기억력 점수: " + "B \n" + "준수한 기억력을 가지고 계시군요!");
            scoreData = "B";
        } if (score > 40 && score <= 59) {
            textResult.setText(time + "기억력 점수: " + "C \n" + "오락가락하실 때가 많으시군요. 신경쓰세요!");
            scoreData = "C";
        } if (score >= 60) {
            textResult.setText(time + "기억력 점수: " + "D \n" + "당장 병원으로 가세요!");
            scoreData = "D";
        }


        dao.saveScoreToFileByGames(GAME2_NAME, scoreData);

//        if(spf.getInt("spfscore",0) < score){ //내점수가 저번 점수보다 크면
//            spf.edit().putInt("spfscore",score).commit(); //반영의 commit(). 현재상태 저장
//            textResult.setText("신기록달성\n"+score);
//        }


        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result3Activity.this, Main3Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void btnHome(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickGoToFirst(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
