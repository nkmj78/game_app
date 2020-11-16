package edu.android.appgame.game.game2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.android.appgame.R;

import static edu.android.appgame.game.game2.MainActivity.SCORE_CHO;

public class ResultActivity extends AppCompatActivity {

    private TextView textResult;
    private Button btnRetry;
    SharedPreferences spf = null;
    private String scoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2_result);

        textResult = findViewById(R.id.textResult);
        btnRetry = findViewById(R.id.btnRetry);

        int score_cho = getIntent().getIntExtra(SCORE_CHO,0); // 남은 시간
        int score = 25 - score_cho; // 걸린 시간 (30 - 남은시간)



//        spf = getSharedPreferences("spfScore", MODE_PRIVATE);

        String time = "걸린 시간: " + score + "초\n\n";
        if (score > 0 && score <= 8) {
            textResult.setText(time + "기억력 점수: " + "A \n" + "기억력이 매우 뛰어나십니다!");
            scoreData = "A";
        } if (score > 8 && score <= 16) {
            textResult.setText(time + "기억력 점수: " + "B \n" + "준수한 기억력을 가지고 계시군요!");
            scoreData = "B";
        } if (score > 16 && score <= 24) {
            textResult.setText(time + "기억력 점수: " + "C \n" + "오락가락하실 때가 많으시군요. 신경쓰세요!");
            scoreData = "C";
        } if (score >= 25) {
            textResult.setText(time + "기억력 점수: " + "D \n" + "당장 병원으로 가세요!");
            scoreData = "D";
        }

//        if(spf.getInt("spfscore",0) < score_cho){ //내점수가 저번 점수보다 크면
//            spf.edit().putInt("spfscore",score_cho).commit(); //반영의 commit(). 현재상태 저장
//            textResult.setText("신기록달성\n" + score_cho);
//        }



        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Next(View view) {

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void btnHome(View view) {
    }
}
