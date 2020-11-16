package edu.android.appgame.test.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.android.appgame.MainActivity;
import edu.android.appgame.R;
import edu.android.appgame.test.TestSelectActivity;

import static edu.android.appgame.test.test1.test1Fragment.TOTAL_SCORE;

public class test1Result extends AppCompatActivity {

    private TextView textScore, textGuide;
    private int total;
    private test1Fragment test1 = new test1Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1_result);

        textScore = findViewById(R.id.textScore);
        textGuide = findViewById(R.id.textGuide1);
        total = getIntent().getIntExtra(TOTAL_SCORE, 0);

        if (total >= 0 && total < 7) {
            textScore.setText("점수: " + total);
            textGuide.setText("치매가 아닌 것으로 보입니다. 그래도 늘 신경쓰세요! \n" +
                    "다음 10가지 내용을 잘 지키도록 하시면 치매 예방에 도움이 됩니다.\n" +
                    "\n" +
                    "1. 고혈압을 치료해야 합니다.\n" +
                    "2. 콜레스테롤을 점검해야 합니다.\n" +
                    "3. 심장병을 초기에 발견해 치료받아야 합니다.\n" +
                    "4. 적절한 운동을 꾸준히 하십시오.\n" +
                    "5. 절대로 담배를 피우지 마십시오.\n" +
                    "6. 과음은 절대 금물입니다.\n" +
                    "7. 일이나 취미활동을 계속하십시오.\n" +
                    "8. 당뇨병을 조심해야 합니다.\n" +
                    "9. 체중을 줄여야 합니다.\n" +
                    "10. 우울증 치료해야 합니다.");
        }
        if (total >= 7 && total <= 30) {
            textScore.setText("점수: " + total);
            textGuide.setText("치매가 의심됩니다. 반드시 병원이나 센터에 찾아가보세요!\n" +
                    "치매센터을 방문하시면,\n" +
                    "\n" +
                    "전문의사의 진찰과 아울러 뇌 촬영(MRI, CT, PET 등), 혈액검사, 신경심리검사 등의 검사를 받게 됩니다. 이러한 진찰과 검사는 치매 여부, 원인 및 진행 정도를 밝히기 위해 꼭 필요한 것들입니다.\n" +
                    "\n" +
                    "또한, 각 지역에 마련되어 있는 치매센터(지역에 상관없이 3431-7200)로 연락하시면,\n" +
                    "\n" +
                    "치매에 대한 전문적인 교육을 받은 간호사가 치매가 의심될 때 어떻게 해야 하는지 친절히 상담해드리고, 치매와 관련된 다양하고 유용한 정보를 제공해드립니다.");
        }
    }

    public void btnGoBackToTest(View view) {
        Intent intent = new Intent(this, TestSelectActivity.class);
        startActivity(intent);
        finish();
    }
}