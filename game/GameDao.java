package edu.android.appgame.game;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;

import static edu.android.appgame.MainActivity.currentMemberId;
import static edu.android.appgame.MainActivity.isLogin;

import edu.android.appgame.ChartFriendDialog;
import edu.android.appgame.Member;
import edu.android.appgame.R;

public class GameDao  {

    private static final String TAG = "file_tag";
    private static final String AVERAGE_FILE_NAME = "allaverage.txt";


    private List<Game> gameList = new ArrayList<>();
    private static GameDao instance = null;
    private Context context;
    private List<String> gradeList = new ArrayList<>(); // 등급들 저장할 리스트
    private int averageGrade; // 평균 등급
    public List<String> gameScoreList;
    private Map<String, Object> childUpdates;
    private Map<String, Object> postValues;
    private String userId;


    public interface GetFirebaseData {
        void onReceivedEvent(String userId);
    }

    private GetFirebaseData myGetFirebaseData;

    public void setOnReceivedFirebaseData(GetFirebaseData listener, String userId){
        Log.i(TAG, "No.2");
        this.userId = userId;
        myGetFirebaseData = listener;
        getGameScoreFromFirebase(userId);
    } // end setOnReceivedFirebaseData()


    public static GameDao getInstance(Context context) {
        if (instance == null ){
            instance = new GameDao(context);
        }
        return instance;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    private GameDao(Context context) {
        this.context = context/*.getApplicationContext()*/;
        makeDummyData();
    }

    private void makeDummyData() {
        gameList.add(new Game("언어능력게임", "언어는 사회생활을 하는 필수적인 요소입니다. 언어는 중요한 능력입니다 ", R.drawable.toys));
        gameList.add(new Game("기억능력게임 ", "정확하고 빠르게 계산하며 수에 관한 문제를 추리하고 이해하며 해결할 수 있는 능력 향상을 도와드립니다. ", R.drawable.cards));
        gameList.add(new Game("색인지게임", "점, 선, 면 등을 사용하여 이루어진 도형을 자유롭게 다룰수 있도록 도움을 주는 게임입니다.", R.drawable.gaming));
        gameList.add(new Game("집중력게임 ", "퍼즐을 이용해 공간 관계나 공간 위치를 감각을 통해 파악하는 능력을 개발해주는 게임입니다.", R.drawable.puzzle));
        gameList.add(new Game("수리능력게임", "정확하고 빠르게 계산하며 수에 관한 문제를 추리하고 이해하며 해결할 수 있는 능력 향상을 도와드립니다", R.drawable.game_1));
        gameList.add(new Game("관계순서인지능력게임", "숫자/문자들의 순서관계를 인지함과 동시에 기억의 집중을 통해 유연한 두뇌활동을 향상시킵니다.", R.drawable.potential));

    } // end makeDummyData()

    private FirebaseDatabase database;
    private DatabaseReference myRef;



    // 각 게임에서 넘어온 게임 점수 각 게임 파일로 (회차,점수) 저장하기 위한 파일 내용 읽어오기 + 추가
    public void saveScoreToFileByGames(String gameName, String gameGrade) {

        if(!isLogin) {
            return;
        }

        isInFile(gameName);
        StringBuilder builder = new StringBuilder();
        String fileName = gameName + ".txt";

        int time = 1; // 회차 저장을 위한 변수

        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader br = null;

        try {
            in = context.openFileInput(fileName);
            reader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                time++;
                gradeList.add(line.split(",")[1]);
                line = br.readLine();

            }

            String insert = time + "," + gameGrade;
            builder.append(insert).append("\n");

            gradeList.add(gameGrade);

            calculateTotalAverageGameScore(gameName);


        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        addScoreToPrevFile(builder, fileName);

    }// end saveScoreToFileByGames()

    private void isInFile(String gameName) {
        try {
            context.openFileInput(gameName + ".txt");
        } catch (FileNotFoundException e) {
            try {
                FileOutputStream f = context.openFileOutput(gameName + ".txt", Context.MODE_PRIVATE);
                f.close();
                if (gameName.equals("allaverage")) {
                    StringBuilder initBuilder = new StringBuilder();
                    initBuilder.append("quiz,").append(0).append("\n")
                            .append("card,").append(0).append("\n")
                            .append("word,").append(0).append("\n")
                            .append("calculate,").append(0).append("\n")
                            .append("qclick,").append(0);
                    addScoreToPrevFile(initBuilder, AVERAGE_FILE_NAME);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    } // end isInFile()


    // 각 게임에서 넘어온 게임 점수 각 게임 파일로 (회차,점수) 저장 - 파일에 쓰기
    private void addScoreToPrevFile(StringBuilder scorePlus, String fileName){

       // scorePlus =null; // 파일 속 내용 초기화 하기 위한 코드 (일단)
        OutputStream out = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            out = context.openFileOutput(fileName, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(writer);
            bw.write(scorePlus.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                    bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } // end addScoreToPrevFile()


    // 각 게임 총 평균 점수 계산
    private void calculateTotalAverageGameScore(String gameName){
        int totalGrade =0;
        averageGrade =0;

        for(int i = 0; i< gradeList.size(); i++){
            String grade = gradeList.get(i).toString();
            Log.i(TAG,"grade:" + grade);

            switch (grade){
                case "A":
                    totalGrade += 90;
                    Log.i(TAG, "totalGrade : " + totalGrade);
                    break;
                case "B":
                    totalGrade += 80;
                    Log.i(TAG, "totalGrade : " + totalGrade);
                    break;
                case "C":
                    totalGrade +=70;
                    Log.i(TAG, "totalGrade : " + totalGrade);
                    break;
                case "D":
                    totalGrade += 60;
                    Log.i(TAG, "totalGrade : " + totalGrade);
                    break;
            } // end switch()
        } // end for()

        Log.i(TAG, "totalGrade : " + totalGrade);
        averageGrade = (int) (totalGrade/gradeList.size());
        Log.i(TAG, "averageGrade : " + averageGrade);

        // TODO 평균 파일에 (게임이름, 평균 점수) 로 넣기
        String fileName = AVERAGE_FILE_NAME.split("\\.")[0];

        isInFile(fileName);
        List<String> oldData =readContentsFromTxtFile(AVERAGE_FILE_NAME);

        String quiz = oldData.get(0);
        String card = oldData.get(1);
        String word = oldData.get(2);
        String calculate = oldData.get(3);
        String qclick = oldData.get(4);

        switch (gameName) {
            case "quiz" :
                quiz = String.format("%s,%s", gameName, averageGrade);
                oldData.set(0, quiz);
                break;
            case "card" :
                card = String.format("%s,%s", gameName, averageGrade);
                oldData.set(1, card);
                break;
            case "word" :
                word = String.format("%s,%s", gameName, averageGrade);
                oldData.set(2, word);
                break;
            case "calculate" :
                calculate = String.format("%s,%s", gameName, averageGrade);
                oldData.set(3, calculate);
                break;
            case "qclick" :
                qclick = String.format("%s,%s", gameName, averageGrade);
                oldData.set(4, qclick);
                break;
        }
        String insert = String.format("%s\n%s\n%s\n%s\n%s",
                oldData.get(0), oldData.get(1), oldData.get(2), oldData.get(3), oldData.get(4));

        OutputStream out = null;
        OutputStreamWriter writer = null;
        BufferedWriter bw = null;
        try {
            out = context.openFileOutput(AVERAGE_FILE_NAME, context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(writer);

            bw.write(insert);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sendToFirebase(gameName, averageGrade);
    } // end calculateTotalAverageGameScore()

    private List<String> readContentsFromTxtFile(String fileName) {
        List<String> contents = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        InputStream in = null;
        InputStreamReader reader = null;
        BufferedReader br = null;

        try {
            in = context.openFileInput(fileName);
            reader = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(reader);

            String line = br.readLine();
            while (line != null) {
//                builder.append(line).append("\n");
//                contents.add(builder.toString());
                contents.add(line);
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
        return contents;
    } // end readContentsFromTxtFile()


    // 평균 점수 Firebase에 올리기
    private void sendToFirebase(String gameName, int averageGrade){

        if(isLogin){
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Member");
//            String key = myRef.child("/" + currentMemberId + "/game").push().getKey();
           myRef.child("/" + currentMemberId + "/" + gameName).push();
//            Log.i(TAG, "key: " + key);
            Map<String, Object> postValues = toMap(gameName, averageGrade);
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/" + currentMemberId + "/" + gameName, postValues.get(gameName));
            myRef.updateChildren(childUpdates);

            Toast.makeText(context, "Done DB Upload~", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "기록을 저장하려면 로그인이 필요합니다", Toast.LENGTH_SHORT).show();
        }

    } // end sendToFirebase()


    // 게임 점수 저장하기 위한 Map
    @Exclude
    public Map<String, Object> toMap(String gameName, int averageGrade){
        HashMap<String, Object> result = new HashMap<>();
        result.put(gameName, averageGrade);
        return result;
    } // end toMap()




    // 회원가입된 Member의 게임점수를 가져옴 - 그래프 띄우기
    public List<String> getGameScoreFromFirebase(final String userId) {
        this.userId = userId;
        if(isLogin){
            gameScoreList = new ArrayList<>();

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Member/"+ userId +"/");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String score = null;
                        String key = snapshot.getKey().toString();
                        switch (key) {
                            case "quiz" :
                                score = snapshot.getValue().toString();
                                gameScoreList.add(score);
                                Log.i(TAG,"quiz: " + score);
                                break;
                            case "card" :
                                score = snapshot.getValue().toString();
                                gameScoreList.add(score);
                                Log.i(TAG,"card: " + score);
                                break;
                            case "word" :
                                score = snapshot.getValue().toString();
                                gameScoreList.add(score);
                                Log.i(TAG,"word: " + score);
                                break;
                            case "calculate" :
                                score = snapshot.getValue().toString();
                                gameScoreList.add(score);
                                Log.i(TAG,"calculate: " + score);
                                break;
                            case "qclick" :
                                score = snapshot.getValue().toString();
                                gameScoreList.add(score);
                                Log.i(TAG,"qclick: " + score);
                                break;
                            default:
                                break;
                        }
                    }
                    Log.i(TAG,"gameScoreList: " + gameScoreList);
                    Log.i(TAG,"userId: " + userId);
                    // TODO 차트 그리는 메소드 호출
                    myGetFirebaseData.onReceivedEvent(userId);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i(TAG, "Failed to read value", databaseError.toException());
                }
            });
        } else {
            Toast.makeText(context, "그래프를 확인하려면 로그인이 필요합니다", Toast.LENGTH_SHORT).show();
        }
//        myRef.removeEventListener();
        return gameScoreList;
    } // end getGameScoreFromFirebase()


} // end class GameDao
