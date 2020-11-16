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

public class Main0Itwill extends AppCompatActivity {

    private static final String TAG = "tag";
    private String gameName = "itw";
    public static final String ITW_SCORE = "itw_score";
    public static final String ITW_BEST = "itw_best";
    private Main0Result mr = Main0Result.getInstance(this);

    private HashSet set = new HashSet();
    private int index =0;
    private TextView textQuiz, textHint;

    private String[] quizItwill = {"ㅎㅅㅅ","ㅈㅈㅇ","ㅂㅋㄴ","ㄱㅅㅎ, ㄱㄴㅇ, ㅇㅈㅇ","ㄹㄸ","ㅈㅅㄱ","ㄱㅅㄱ,ㅇㅈㅇ,ㄱㄴㅇ","ㄱㄴㅂㅂ","ㅋㅌㄹㅇㄷ","ㄱㅈㅊ"
            ,"ㄱㅅㅎ"

    };

    private String[] itwillHint = {"중도포기","1강의실 최대 미스테리","재철 형이 좋아하는 것은~?","1강의실 최고 귀요미", "매주 토요일 오후 8시 30분이 기다려짐", "이제 그만 말해",
            "1강의실 수강생 3대 갓","오늘은 거기 갈까?","종화형이 한때 점심시간을 불태웠던 게임",
            "??: 내 생일 돼지갈비..기억해놔라","1강의실 최고의 빵순이"

    };

   private String[] itwillResult = {"한성수","조준일","비키니","김서희, 김나영, 왕지영", "로또","전상근","김성겸,왕지영,김나영","강남불백","카트라이더","김재철",
           "김서희"

    };

    private Random rnd;
    private Toast toast;
    private View layout;
    private Button btnCheck;
    private Button btnNext;
    private EditText textResult;
    private TextView itwScore,itwBest;
    private String bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_itwill);

        textQuiz = findViewById(R.id.textQst);
        textHint = findViewById(R.id.textHint);
        btnNext = findViewById(R.id.btnNext);
        btnCheck = findViewById(R.id.btnCorrect);
        textResult = findViewById(R.id.editInput);
        itwScore = findViewById(R.id.itwScore);
        itwBest = findViewById(R.id.itwBest);

        textQuiz.setText(quizItwill[index]);
        textHint.setText(itwillHint[index]);
        bestScore = String.valueOf(mr.saveScoreToFileByGames(gameName, 0));
        itwBest.setText(String.valueOf(bestScore));

    } // end onCreate()

    public int calculate(){
        Log.i(TAG, "size : " + quizItwill.length);
        rnd = new Random();
        int count1 = 0; // 현재 set에 있는 난수의 개수를 확인
        int count2 = 0; // 서로 다른 난수가 생성됐을때 set에 추가된 개수를 확인
        while (true) {
            count2 = set.size();
            Log.i(TAG, "count2 : " + count2);

            int x1 = rnd.nextInt(quizItwill.length - 1) + 1; // 난수 생성
            set.add(x1); // 난수를 hashset에 추가
            count1 = set.size(); // 현재 set에 있는 난수의 개수 체크
            Log.i(TAG, "count1 : " + count1);
            if (count1 > count2) { // 현재 set에 있는 난수의 개수와 추가된 난수가 있는지 여부 확인
                index = x1;
                Log.i(TAG, "rnd : " + index);
                break;
            }
            if (set.size() == quizItwill.length-1) {
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
        textQuiz.setText(quizItwill[index]);
        textHint.setText(itwillHint[index]);

        btnNext.setEnabled(false);
        textResult.setText("");
        if (set.size() == quizItwill.length) {
            btnNext.setEnabled(false);
            btnCheck.setEnabled(false);
        } else {
            btnCheck.setEnabled(true);
        }
    } // end nextGame()

    public void onClickResult(View view) {

        String inputData = textResult.getText().toString();
        String answer = itwillResult[index];

//        Toast toastAnswer = Toast.makeText(this, "정답입니다!!\n축하합니다!", Toast.LENGTH_SHORT);
//        Toast toast = Toast.makeText(this, "땡!!!!!\n오답입니다..", Toast.LENGTH_SHORT);

        LayoutInflater inflater = getLayoutInflater();

        if (inputData.equals(answer)) {
            layout = inflater.inflate(R.layout.activity_game1_right, null);
            toast = new Toast(getApplicationContext());
            showToast();
            int score = Integer.parseInt(itwScore.getText().toString());
            score++;
            itwScore.setText(String.valueOf(score));
        } else {
            layout = inflater.inflate(R.layout.activity_game1_wrong, null);
            toast = new Toast(getApplicationContext());
            showToast();
        }

        int score = Integer.parseInt(itwScore.getText().toString());
        bestScore = String.valueOf(mr.saveScoreToFileByGames(gameName, score));
        Log.i(TAG, "bestScore: ");
        itwBest.setText(bestScore);
    }
    public void showToast(){
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,130);
        toast.setView(layout);
        toast.show();
        btnCheck.setEnabled(false);
        btnNext.setEnabled(true);
    }


    public void onClickMainHome(View view) {
        finish();
        int avgScore;
        int nScore = Integer.parseInt(itwScore.getText().toString());
        avgScore = (nScore > 5) ? 100 : 50;
        TOTALSCORE.put(gameName, avgScore);
        mr.calculateGrade();
        Intent intent = new Intent(this, Main0Activity.class);
        startActivity(intent);
//        intent.putExtra(ITW_SCORE, itwScore.getText().toString());
//        intent.putExtra(ITW_BEST, itwBest.getText().toString());
        finish();
    }
} // end class Main0Animal

