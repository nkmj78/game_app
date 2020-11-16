package edu.android.appgame.test.test2;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.android.appgame.R;
import edu.android.appgame.test.TestSelectActivity;

import static edu.android.appgame.test.test2.test2Fragment.TOTAL_SCORE;

public class test2Result extends AppCompatActivity {

    private TextView textScore2, textGuide2;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_result);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPink)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorSoftPink));


        textScore2 = findViewById(R.id.textScore2);
        textGuide2 = findViewById(R.id.textGuide2);
        total = getIntent().getIntExtra(TOTAL_SCORE, 0);

        if (total <= 6) {
            textScore2.setText("기억력 점수: " + total);
            textGuide2.setText("정상입니다! \n\n 치매 예방 관리 10대 수치 \n \n 1. 손과 입을 빠르게 움직여라 \n 2. 머리를 써라 \n 3. 담배는 당신의 뇌도 태운다. \n 4. 과도한 음주는 당신의 뇌를 삼킨다. \n " +
                    "5. 건강한 식습관이 건강한 뇌를 만든다.\n 6. 몸을 움직여야 뇌도 건강하다. \n 7. 사람들과 만나고 어울리자 \n 8. 치매가 의심되면 보건소에 가자.\n 9. 치매에 걸리면 가능한 빨리 치료하자. \n 10. 치매 치료관리는 꾸준히 하자.");
        }
        if (total > 6) {
            textScore2.setText("기억력 점수: " + total);
            textGuide2.setText("(주의) 기억력 장애 의심\n\n 실제 기억력 장애는 없고 우울증 등 다른 원인에 의해서도\n " +
                    "높은 점수가 나올 수 있으므로 가급적 정보제공자 설문 혹은 간이 인지기능검사와 함께 사용하는 것이 바람직하다.");

        }

    }

    public void btnGoBackToTest(View view) {
        Intent intent = new Intent(this, TestSelectActivity.class);
        startActivity(intent);
        finish();
    }
}
