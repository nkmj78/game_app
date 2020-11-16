package edu.android.appgame.test.test1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import edu.android.appgame.R;

public class test1Fragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {

    private static final String[]  SURVEY_TEXT= {
            "1. 오늘이 몇 월이고, 무슨 요일인지 잘 모른다",
            "2. 자기가 놔둔 물건을 찾지 못한다.",
            "3. 같은 질문을 반복해서 한다.",
            "4. 약속을 하고서 잊어버린다.",
            "5. 물건을 가지러 갔다가 잊어버리고 그냥 온다.",
            "6. 물건이나 사람의 이름을 대기가 힘들어 머뭇거린다.",
            "7. 대화 중 내용이 이해되지 않아 반복해서 물어본다.",
            "8. 길을 잃거나 헤맨 적이 있다.",
            "9. 예전에 비해서 계산능력이 떨어졌다.\n" +
                    "(예: 물건값이나 거스름돈 계산을 못한다.)",
            "10. 예전에 비해 성격이 변했다.",
            "11. 이전에 잘 다루던 기구의 사용이 서툴러졌다.\n" +
                    "(세탁기, 전기밥솥, 경운기 등)",
            "12. 예전에 비해 방이나 집안의 정리 정돈을 하지 못한다.",
            "13. 상황에 맞게 스스로 옷을 선택하여 입지 못한다.",
            "14. 혼자 대중교통 수단을 이용하여 목적지에 가기 힘들다.\n" +
                    "(관절염 등 신체적인 문제로 인한 것은 제외)",
            "15. 내복이나 옷이 더러워져도 갈아입지 않으려고 한다."

    };
    public static final String TOTAL_SCORE = "total_score";
    private  static  final  String KEY_INDEX="current_index";
    private static final String TAG = "TextToSpeechDemo";
    private static final int MY_DATA_CHECK_CODE = 1234;
    private int currentIndex = 0;
    private TextView textSurvey1;
    private RadioButton radioNever,radioSometimes, radioOften;
    private Button btnNext, btnVoice;
    private int testScore=0;
    private TextToSpeech myTTS;

    public test1Fragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test1_fragment, container2, false);
        textSurvey1 = view.findViewById(R.id.textSurvey1);
        radioNever = view.findViewById(R.id.radioNever);
        radioSometimes = view.findViewById(R.id.radioSometimes);
        radioOften = view.findViewById(R.id.radioOften);
        btnNext = view.findViewById(R.id.btnNext);
        btnVoice = view.findViewById(R.id.btnVoice);
        btnVoice.setOnClickListener(this);
        myTTS = new TextToSpeech(getActivity(), this);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        if(savedInstanceState != null){
            currentIndex =savedInstanceState.getInt(KEY_INDEX);
            changeText();
        }
        textSurvey1.setText(SURVEY_TEXT[0]);
        return  view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    private void showNextText() {
        if(currentIndex<SURVEY_TEXT.length-1){
            currentIndex++;
            changeText();
            if(radioNever.isChecked()){
                radioNever.setChecked(false);
            } if(radioSometimes.isChecked()){
                radioSometimes.setChecked(false);
               } if (radioOften.isChecked()) {
                radioOften.setChecked(false);

            }
        }else{
            btnNext.setEnabled(false);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
            alertDialog.setTitle("검사결과창");
            alertDialog.setMessage("마지막 문제 입니다. 결과를 보시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(getActivity(), test1Result.class);
                                    intent.putExtra(TOTAL_SCORE, testScore);
                                    startActivity(intent);
                                }
                            });
            alertDialog.create();
            alertDialog.show();
        }
    }

    private void changeText() {
        textSurvey1.setText(SURVEY_TEXT[currentIndex]);
    }
    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioNever.isChecked()){
                    testScore += 0;
                }else if(radioSometimes.isChecked()){
                    testScore += 1;
                } else if (radioOften.isChecked()) {
                    testScore += 2;
                }
                if( radioNever.isChecked() == false && radioSometimes.isChecked() == false && radioOften.isChecked() == false){
                    Toast.makeText(getActivity(), "체크를 하신 후 다음문제로 넘어가실 수 있습니다.", Toast.LENGTH_SHORT).show();
                } else if (radioNever.isChecked() || radioSometimes.isChecked() || radioOften.isChecked()){
                    showNextText();
                }
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = myTTS.setLanguage(Locale.KOREA);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                //음성 톤
                myTTS.setPitch(1.0f);
                //읽는 속도
                myTTS.setSpeechRate(0.7f);
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnVoice:
                speakOut();
                break;
        }
    }

    private void speakOut() {

        String text = textSurvey1.getText().toString();
        myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onDestroy() {
        if (myTTS != null) {
            myTTS.stop();
            myTTS.shutdown();
        }
        super.onDestroy();
    }
}
