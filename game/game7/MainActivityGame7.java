package edu.android.appgame.game.game7;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

import edu.android.appgame.GameActivity;
import edu.android.appgame.R;
import edu.android.appgame.game.GameDao;

public class MainActivityGame7 extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "debug";
    private Random random = new Random();
    private int boundOffset;
    private int clickCount;
    private int prevValue = 0;
    private int currentValue = 0;
    private int pointX;
    private int pointY;
    private int countType;
    private TextView textView, textLevel, textBest, selectLevel, selectType;
    private Chronometer chronometer;
    private Animation animFadeout;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private HashSet set;
    private GameDao dao = GameDao.getInstance(this);

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home1:
                    GameActivity gd = (GameActivity) GameActivity.GameActivity;
                    gd.finish();
                    finish();
                    return true;
                case R.id.navigation_dashboard1:
                    // TODO
                    return true;
                case R.id.navigation_notifications1:
                    finish();
                    return true;
            }
            return false;
        }
    };
    private int bestMinute;
    private int bestSecond;
    private int currentMinute;
    private int currentSecond;
    private int minute;
    private int second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game7_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        textLevel = findViewById(R.id.textLevel);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                makeTableView();
//                makeInitValue();
                handler.removeMessages(0);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        chronometer.stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        View view = findViewById(R.id.tableBase);
        pointX = view.getWidth();
        pointY = view.getHeight();
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case -1:
                    onClickFadeout(v);
                    break;
                case R.id.imageRetry:
                    clickCount = 0; prevValue = 0;
                    TableLayout fr = findViewById(R.id.tableBase);
                    fr.removeAllViewsInLayout();
                    makeTableView();
                    break;
                case R.id.btnLevelUp:
                    onClickLevelUp();
                    makeTableView();
                    break;
                case R.id.btnLevelDown:
                    onClickLevelDown();
                    makeTableView();
                    break;
                case R.id.btnTypeUp:
                    onClickTypelUp();
                    makeTableView();
                    break;
                case R.id.btnTypeDown:
                    onClickTypeDown();
                    makeTableView();
                    break;
            }
    }

    private void onClickTypeDown() {
        clickCount = 0; prevValue = 0; startTimer(); stopTimer();
        TableLayout fr = findViewById(R.id.tableBase);
        fr.removeAllViewsInLayout();
        selectType = findViewById(R.id.textSelectType);
        switch (selectType.getText().toString()){
            case "1234":
                selectType.setText(getString(R.string.type_korean));
                break;
            case "ABCD":
                selectType.setText(getString(R.string.type_1234));
                break;
            case "가나다":
                selectType.setText(getString(R.string.type_english));
                break;
        }
    }

    private void onClickTypelUp() {
        clickCount = 0; prevValue = 0; startTimer(); stopTimer();
        TableLayout fr = findViewById(R.id.tableBase);
        fr.removeAllViewsInLayout();
        selectType = findViewById(R.id.textSelectType);
        switch (selectType.getText().toString()){
            case "1234":
                selectType.setText(getString(R.string.type_english));
                break;
            case "ABCD":
                selectType.setText(getString(R.string.type_korean));
                break;
            case "가나다":
                selectType.setText(getString(R.string.type_1234));
                break;
        }
    }

    private void onClickLevelDown() {
        clickCount = 0; prevValue = 0; startTimer(); stopTimer();
        TableLayout fr = findViewById(R.id.tableBase);
        fr.removeAllViewsInLayout();
        selectLevel = findViewById(R.id.textSelectLevel);
        textLevel = findViewById(R.id.textLevel);
        switch (selectLevel.getText().toString()){
            case "EASY":
                selectLevel.setText("");
                selectLevel.setText(getString(R.string.level_hard).toUpperCase());
                textLevel.setText(R.string.level_hard);
                break;
            case "NORMAL":
                selectLevel.setText(getString(R.string.level_easy).toUpperCase());
                textLevel.setText(R.string.level_easy);
                break;
            case "HARD":
                selectLevel.setText(getString(R.string.level_normal).toUpperCase());
                textLevel.setText(R.string.level_normal);
                break;
        }
    }

    private void onClickLevelUp() {
        clickCount = 0; prevValue = 0; startTimer(); stopTimer();
        TableLayout fr = findViewById(R.id.tableBase);
        fr.removeAllViewsInLayout();
        selectLevel = findViewById(R.id.textSelectLevel);
        textLevel = findViewById(R.id.textLevel);
        String currentLevel = selectLevel.getText().toString();
        String nextLevel;
        Log.i(TAG, getString(R.string.level_hard));
        switch (currentLevel){
            case "EASY":
                nextLevel = getString(R.string.level_normal);
                selectLevel.setText(nextLevel.toUpperCase());
                textLevel.setText(nextLevel);
                countType = 4;
                break;
            case "NORMAL":
                nextLevel = getString(R.string.level_hard);
                selectLevel.setText(nextLevel.toUpperCase());
                textLevel.setText(nextLevel);
                countType = 5;
                break;
            case "HARD":
                nextLevel = getString(R.string.level_easy);
                selectLevel.setText(nextLevel.toUpperCase());
                textLevel.setText(nextLevel);
                countType = 6;
                break;
        }
    }

    public void makeTableView(){
        set = new HashSet();
        int screenWidth;
        String level = textLevel.getText().toString();
        Log.i(TAG, "Level = " + level);
        switch (level) {
            case "Easy":
                countType = 4;
                break;
            case "Normal":
                countType = 5;
                break;
            case "Hard":
                countType = 6;
                break;
        }
        screenWidth = pointX / countType;
        final TableLayout tableLayout = findViewById(R.id.tableBase);
        tableLayout.setGravity(Gravity.CENTER);
        int t = 1; int ti = 1;
        for (int i = 0; i < countType; i++){
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT
            ));
            for (int j = 0; j < countType; j++){
                textView = new TextView(this);
                textView.setOnClickListener(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
                ));
//                textView.setText(String.valueOf(t++));
                typeCheck();
                textView.setBackgroundResource(R.drawable.table_layout_game7);
                textView.setTextSize(30); textView.setTextColor(getResources().getColor(R.color.colorAccent));
                textView.setGravity(Gravity.CENTER); //textView.setPadding(4,4,4,4);
                tableRow.addView(textView, screenWidth, screenWidth);
            }
            tableLayout.addView(tableRow);
        }
        findViewById(R.id.btnLevelUp).setOnClickListener(this);
        findViewById(R.id.btnLevelDown).setOnClickListener(this);
        findViewById(R.id.btnTypeUp).setOnClickListener(this);
        findViewById(R.id.btnTypeDown).setOnClickListener(this);
    }

    private void typeCheck() {
        selectType = findViewById(R.id.textSelectType);
        String type = selectType.getText().toString();
        if (type.equals(getString(R.string.type_1234))) {
            boundOffset = 0;
            int text = makeValues(set, boundOffset);
            set.add(text);
            textView.setText(String.valueOf(text + 1));
            textView.setTag(text + 1);
        } else if (type.equals(getString(R.string.type_english))) {
            boundOffset = 65; prevValue =65;
            int text = makeValues(set, boundOffset);
            set.add(text);
            textView.setText(String.valueOf((char) text));
            textView.setTag(text + 1);
        } else if (type.equals(getString(R.string.type_korean))) {
            boundOffset = 12587; prevValue =12587;
            int text = makeValues(set, boundOffset);
            set.add(text);
            textView.setText(String.valueOf((char) text));
            textView.setTag(text + 1);
        } else {
            Toast.makeText(this, "[ERROR_002]", Toast.LENGTH_SHORT).show();
        }
    }

    private int makeValues(HashSet set, int boundOffset) {
        int rnd = 0;
        int bound = countType * countType;
        int prevCount = set.size();
        int currentCount = set.size();
        while (true) {
            rnd = random.nextInt(bound) + boundOffset;
            if (rnd > 90) {
                rnd += 6;
            }
            set.add(rnd);
            currentCount = set.size();
            if (currentCount > prevCount) {
                break;
            }
            prevCount = set.size();
        }
        return rnd;
    }

    public void onClickFadeout(View view) {
        if (((TextView) view).getText().toString() != "") {
            currentValue = (int) view.getTag();
            Log.i(TAG, "currValue = " + currentValue);
                prevValue = (clickCount == countType * countType) ? prevValue - boundOffset : prevValue;
                Log.i(TAG, "prevValue = " + prevValue);
            if (currentValue == 1 + boundOffset
                    || (currentValue-prevValue == 7 && currentValue > 12593)) {
                startTimer();
                buttonsEnabled(false);
            }
            if (currentValue - prevValue == 1) {
                prevValue = currentValue;
                clickCount += 1;
//                Log.i(TAG, "clickCount-1 = " + clickCount);
                showFadeout(view);
                if (clickCount == countType * countType) {  // end game
//                    Log.i(TAG, "clickCount-36 = " + clickCount);
                    stopTimer();
//                    Log.i(TAG, "time = " + chronometer.getText().toString());
                    String gameGrade = recordResult(chronometer.getText().toString());  // To send with score
                    dao.saveScoreToFileByGames("qclick", gameGrade);

                    buttonsEnabled(true);

                    TableLayout fr = findViewById(R.id.tableBase);
                    fr.removeAllViewsInLayout();
                    fr.addView(View.inflate(this, R.layout.restart_game7, null));
                    findViewById(R.id.imageRetry).setOnClickListener(this);
                    floatToast();
                }
            } else if (currentValue - prevValue == 7 && currentValue > 97){
                prevValue = currentValue;
                clickCount += 1;
                Log.i(TAG, "clickCount-7 = " + clickCount);
                showFadeout(view);
            }

        }
    }

    private void showFadeout(View view) {
        animFadeout = AnimationUtils.loadAnimation(this, R.anim.anim_fadeout);
        view.startAnimation(animFadeout);
        ((TextView) view).setText("");
        view.setBackgroundColor(getResources().getColor(R.color.amber50));
    }

    private void buttonsEnabled(boolean b) {
        findViewById(R.id.btnLevelUp).setEnabled(b);
        findViewById(R.id.btnLevelDown).setEnabled(b);
        findViewById(R.id.btnTypeUp).setEnabled(b);
        findViewById(R.id.btnTypeDown).setEnabled(b);
    }

    private void floatToast() {
        Toast toast = Toast.makeText(getApplicationContext(), "Well Done !!!", Toast.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        messageTextView.setTextColor(getResources().getColor(R.color.amberA700));
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, -50);
        toast.show();
    }

    private void startTimer () {
        chronometer = findViewById(R.id.textScore);
        chronometer.setBase(SystemClock.elapsedRealtime());
//        chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
        chronometer.start();
    }

    private void stopTimer () {
//        stopTime = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();
    }

    private String recordResult(String score) {
        textBest = findViewById(R.id.textBest);
        selectLevel = findViewById(R.id.textSelectLevel);
        String[] bestScore = textBest.getText().toString().split(":");
        bestMinute = Integer.parseInt(bestScore[0]);
        bestSecond = Integer.parseInt(bestScore[1]);
        String[] times = score.split(":");
        currentMinute = Integer.parseInt(times[0]);
        currentSecond = Integer.parseInt(times[1]);
        String level = selectLevel.getText().toString();
        String type = selectType.getText().toString();
        Boolean isHighScore = false;
        String gameGrade = "D";
        if (level.equals("EASY")) {
            if (type.equals("1234")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "B" : "C";
            } else if (type.equals("ABCD")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "B" : "C";
            } else {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "B" : "C";
            }
        } else if (level.equals("NORMAL")) {
            if (type.equals("1234")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "B";
            } else if (type.equals("ABCD")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "B";
            } else {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "B";
            }
        } else {
            if (type.equals("1234")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "B";
            } else if (type.equals("ABCD")) {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "A";
            } else {
                isHighScore = compareScore(level, type);
                gameGrade = isHighScore ? "A" : "A";
            }
        }

        return gameGrade;
    }

    private Boolean compareScore(String level, String type) {
        Boolean isChangeBestScore = false;
        if (currentMinute != 0) {
            if (currentMinute > bestMinute) {
                minute = currentMinute; second = currentSecond;
                isChangeBestScore = true;
            } else if (currentMinute < bestMinute) {
                minute = bestMinute; second = bestSecond;
            } else {
                if (currentSecond > bestSecond) {
                    minute = currentMinute; second = currentSecond;
                    isChangeBestScore = true;
                } else {
                    minute = bestMinute; second =bestSecond;
                }
            }
        } else {
            if (currentSecond > bestSecond) {
                minute = currentMinute; second = currentSecond;
                isChangeBestScore = true;
            } else {
                minute = bestMinute; second = bestSecond;
            }
        }

        String best = String.format("%02d:%02d", minute, second);
        textBest.setText(best);
        return isChangeBestScore;
    }

}


