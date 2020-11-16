package edu.android.appgame.test.test3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.android.appgame.R;
import edu.android.appgame.test.TestSelectActivity;

import static edu.android.appgame.test.test3.test3Fragment.TOTAL_SCORE;

public class test3Result extends AppCompatActivity {

    private TextView textScore3, textGuide3;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3_result);

        textScore3 = findViewById(R.id.textScore3);
        textGuide3 = findViewById(R.id.textGuide3);

        total = getIntent().getIntExtra(TOTAL_SCORE, 0);

        if (total >= 0 && total <= 10) {
            textScore3.setText("우울증 점수: " + total);
            textGuide3.setText("당신은 우울증과 거리가 멉니다. 지금처럼 즐기세요!");
        } else if (total > 10 && total <= 14) {
            textScore3.setText("우울증 점수: " + total);
            textGuide3.setText("우울증에 가깝습니다. 상담 혹은 병원에 찾아가시길 권장합니다.");
        } else if (total > 14 && total <= 30) {
            textScore3.setText("우울증 점수: " + total);
            textGuide3.setText("당신은 분명한 우울증입니다. 반드시 조치를 취하셔야 합니다!");


        }

    }

    public void btnTestGo(View view) {
        Intent intent = new Intent(this, TestSelectActivity.class);
        startActivity(intent);
        finish();
    }


}