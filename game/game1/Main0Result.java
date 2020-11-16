package edu.android.appgame.game.game1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.android.appgame.R;
import edu.android.appgame.game.GameDao;

import static edu.android.appgame.MainActivity.isLogin;

public class Main0Result extends AppCompatActivity {

    private static Main0Result instance = null;
    private TextView textAML, textKr, textItw;
    private ArrayList<String> scoreList;
    private int bestScore;
    private Context context;
    private String gameName1 = "animal.txt";
    private String gameName2 = "korea.txt";
    private String gameName3 = "itw.txt";
    public static Map<String, Integer> TOTALSCORE = new HashMap<>();
    private GameDao dao = GameDao.getInstance(this);

    public Main0Result(){}

    private Main0Result(Context context){
        this.context = context;
    }

    public static Main0Result getInstance(Context context){
        if(instance == null) {
            instance = new Main0Result(context);
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1_result);
        context = this;
        textAML = findViewById(R.id.textAML);
        textKr = findViewById(R.id.textKr);
        textItw = findViewById(R.id.textItw);
        isInFile(gameName1.split("\\.")[0]);
        isInFile(gameName2.split("\\.")[0]);
        isInFile(gameName3.split("\\.")[0]);
        textAML.setText("동식물게임 최고점수 : " + String.valueOf(readContentsFromTxtFile(gameName1)));
        textKr.setText("사자성어게임 최고점수 : " + String.valueOf(readContentsFromTxtFile(gameName2)));
        textItw.setText("아이티윌게임 최고점수 : " + String.valueOf(readContentsFromTxtFile(gameName3)));
    }

    public int saveScoreToFileByGames(String gameName, int scoreByGames) {
        if(!isLogin) {
            return scoreByGames;
        }
        isInFile(gameName); // 최고점수가 기록된 파일이 있는지 여부 검사
        bestScore = readContentsFromTxtFile(gameName + ".txt");// 최고점수가 기록된 파일에서 점수를 가져오기
        if (bestScore >= scoreByGames) { // 가져온 점수와 지금 점수중 어느것이 큰지 비교
            return bestScore;
        }
        writeToFile(gameName, scoreByGames); // 그중에 큰 값을 파일에 저장
        return scoreByGames;
    }// end saveScoreToFileByGames()

    private void isInFile(String gameName) {
        Log.i("tag", "gameName: " + gameName);
        try {
            context.openFileInput(gameName + ".txt");
        } catch (FileNotFoundException e) {
            try {
                FileOutputStream f = context.openFileOutput(gameName + ".txt", MODE_PRIVATE);
                f.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    } // end isInFile()

    private void writeToFile(String gameName, int scoreByGames){
        String insertData = String.format("%s,%d\n", gameName, scoreByGames);

        OutputStream out = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            out = context.openFileOutput(gameName + ".txt", MODE_PRIVATE);
            writer = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(writer);

            bw.write(insertData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int readContentsFromTxtFile(String fileName) {
        bestScore = 0;
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader br = null;

        try {
            in = context.openFileInput(fileName);
            reader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
                bestScore = Integer.parseInt(line.split(",")[1]);
                line = br.readLine();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bestScore;
    } // end readContentsFromTxtFile()

    public void calculateGrade() {
        int score1 = 0;
        int score2 = 0;
        int score3 = 0;
        int avgScore = 0;
        String gameGrade;
        try {
            score1 = TOTALSCORE.get("animal");
            score2 = TOTALSCORE.get("krScore");
            score3 = TOTALSCORE.get("itwScore");
        } catch (Exception e) {
            Log.i("tag", "error score!");
        } finally {
            Log.i("tag", "finally~~~~~~~~~~~");
            avgScore = (score1 + score2 + score3) / 3;
            if (avgScore > 90) {
                gameGrade = "A";
            } else if (avgScore > 70 && avgScore <= 90) {
                gameGrade = "B";
            } else if (avgScore > 50 && avgScore <= 70) {
                gameGrade = "C";
            } else {
                gameGrade = "D";
            }
            dao.saveScoreToFileByGames("quiz", gameGrade);
        }
    }

}
