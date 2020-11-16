package edu.android.appgame.game.game1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

import edu.android.appgame.R;
import static edu.android.appgame.game.game1.Main0Result.TOTALSCORE;
public class Main0Korea extends AppCompatActivity {


    private static final String TAG = "tag";
    private String gameName = "korea";
    public static final String KOREA_SCORE = "korea_score";
    public static final String KOREA_BEST = "korea_best";
    private Main0Result mr = Main0Result.getInstance(this);

    private HashSet set = new HashSet();
    private int index =0;
    private TextView textQuiz, textHint;

    private String[] quizKorea = {
            "ㅇㅅㅊㄹ","ㅇㅈㅇㅌ","ㄱㄱㅊㅅ","ㅅㅅㅁㅊ","ㅅㅅㅂㄱ","ㅇㅈㅌㅇ","ㅇㅂㅅㅅ","ㅈㄱㄴㅂ"
            ,"ㅊㅅㅁㄱ","ㅎㄴㅇㄹ",
    };

    private String[] koreaHint = {
            "말이나 일의진행이 거침없이 쭉쭉나감.","한번 나아감과 한번물러섬(좋다가 나쁘다가함)",
            "잘못을 고치고 옳은 길에 들어섬","어찌할 도리가 없다.","아무런 손을 쓰지않고 보고만 있는다."
            ,"둘중에 하나를 가린다.","말에 이치가 맞지않다.","뒷사람의 말을 이루 다 막기는 어렵다."
            ,"온갖고생 무진 애를쓰다.","기쁨과 노여움과 슬픔과 즐거움등 사람의 온갖 감정"
    };

    private String[] koreaResult = {
            "일사천리","일진일퇴","개과천선","속수무책","수수방관","양자택일","어불성설","중구난방"
            ,"천신만고","희노애락"
    };

    private Random rnd;
    private Toast toast;
    private View layout;
    private Button btnCheck;
    private Button btnNext;
    private EditText textResult;
    private TextView krScore, krbest;
    private String bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_korea);

        textQuiz = findViewById(R.id.textQst);
        textHint = findViewById(R.id.textHint);
        btnNext = findViewById(R.id.btnNext);
        btnCheck = findViewById(R.id.btnCorrect);
        textResult = findViewById(R.id.editInput);
        krScore = findViewById(R.id.koreaScore);
        krbest = findViewById(R.id.koreaBest);

        textQuiz.setText(quizKorea[index]);
        textHint.setText(koreaHint[index]);
        bestScore = String.valueOf(mr.saveScoreToFileByGames(gameName, 0));
        krbest.setText(String.valueOf(bestScore));

    } // end onCreate()

    public int calculate(){
        Log.i(TAG, "size : " + quizKorea.length);
        rnd = new Random();
        int count1 = 0; // 현재 set에 있는 난수의 개수를 확인
        int count2 = 0; // 서로 다른 난수가 생성됐을때 set에 추가된 개수를 확인
        while (true) {
            count2 = set.size();
            Log.i(TAG, "count2 : " + count2);

            int x1 = rnd.nextInt(quizKorea.length - 1) + 1; // 난수 생성
            set.add(x1); // 난수를 hashset에 추가
            count1 = set.size(); // 현재 set에 있는 난수의 개수 체크
            Log.i(TAG, "count1 : " + count1);
            if (count1 > count2) { // 현재 set에 있는 난수의 개수와 추가된 난수가 있는지 여부 확인
                index = x1;
                Log.i(TAG, "rnd : " + index);
                break;
            }
            if (set.size() == quizKorea.length-1) {
//                btnNext.setEnabled(false);
//                btnCheck.setEnabled(false);
                Toast.makeText(this, "마지막 문제입니다...!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main0Activity.class);
                startActivity(intent);
                finish();
                break;
            }
        }

        return index;
    }

    public void nextGame(View view) {
        int index = calculate();
        textQuiz.setText(quizKorea[index]);
        textHint.setText(koreaHint[index]);

        btnNext.setEnabled(false);
        textResult.setText("");
        if (set.size() == quizKorea.length) {
            btnNext.setEnabled(false);
            btnCheck.setEnabled(false);
        } else {
            btnCheck.setEnabled(true);
        }
    } // end nextGame()

    public void onClickResult(View view) {

        String inputData = textResult.getText().toString();
        String answer = koreaResult[index];

//        Toast toastAnswer = Toast.makeText(this, "정답입니다!!\n축하합니다!", Toast.LENGTH_SHORT);
//        Toast toast = Toast.makeText(this, "땡!!!!!\n오답입니다..", Toast.LENGTH_SHORT);

        LayoutInflater inflater = getLayoutInflater();

        if (inputData.equals(answer)) {
            layout = inflater.inflate(R.layout.activity_game1_right, null);
            toast = new Toast(getApplicationContext());
            showToast();
            int score = Integer.parseInt(krScore.getText().toString());
            score++;
            krScore.setText(String.valueOf(score));
        } else {
            layout = inflater.inflate(R.layout.activity_game1_wrong, null);
            toast = new Toast(getApplicationContext());
            showToast();
        }
        int score = Integer.parseInt(krScore.getText().toString());
        bestScore = String.valueOf(mr.saveScoreToFileByGames(gameName, score));
        Log.i(TAG, "bestScore: ");
        krbest.setText(bestScore);


    }
    public void showToast(){
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,130);
        toast.setView(layout);
        toast.show();
        btnCheck.setEnabled(false);
        btnNext.setEnabled(true);
    }


    public void onClickMainGo(View view) {
        finish();
        int avgScore;
        int nScore = Integer.parseInt(krScore.getText().toString());
        avgScore = (nScore > 5) ? 100 : 50;
        TOTALSCORE.put(gameName, avgScore);
        mr.calculateGrade();
        Intent intent = new Intent(this, Main0Activity.class);
        startActivity(intent);
        finish();
    }
} // end class Main0Animal

