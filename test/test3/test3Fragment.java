package edu.android.appgame.test.test3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import edu.android.appgame.R;


public class test3Fragment extends Fragment implements View.OnClickListener, TextToSpeech.OnInitListener {
    private static final String[]  SURVEY_TEXT= {
            "1. 당신의 삶에 대체로 만족하십니까?",
            "2. 활동이나 관심거리가 많이 줄었습니까?",
            "3. 삶이 공허하다고 느끼십니까?",
            "4. 지루하거나 따분할 때가 많습니까?",
            "5. 앞날이 희망적이라고 생각하십니까?",
            "6. 떨쳐버릴 수 없는 생각들 때문에 괴롭습니까?",
            "7. 대체로 활기차게 사시는 편입니까?",
            "8. 당신에게 좋지 않은 일이 생길 것 같아 걱정스럽습니까?",
            "9. 대체로 행복하다고 느끼십니까?",
            "10. 아무 것도 할 수 없을 것 같은 무력감이 자주 듭니까?",
            "11. 불안해지거나 안절부절 못할 때가 자주 있습니까?",
            "12. 외출하는 것보다 그냥 집안에 있는 것이 더 좋습니까?",
            "13. 앞날에 대한 걱정을 자주 하십니까?",
            "14. 다른 사람들보다 기억력에 문제가 더 많다고 느끼십니까?",
            "15. 살아있다는 사실이 기쁘십니까?",
            "16. 기분이 가라앉거나 울적할 때가 자주 있습니까?",
            "17. 요즘 자신이 아무 쓸모없는 사람처럼 느끼십니까?",
            "18. 지난 일에 대해 걱정을 많이 하십니까?",
            "19. 산다는 것이 매우 신나고 즐겁습니까?",
            "20. 새로운 일을 시작하는 것이 어렵습니까?",
            "21. 생활에 활력이 넘치십니까?",
            "22. 당신의 처지가 절망적이라고 느끼십니까?",
            "23. 다른 사람들이 대체로 당신보다 낫다고 느끼십니까?",
            "24. 사소한 일에도 속상할 때가 많습니까?",
            "25. 울고 싶을 때가 자주 있습니까?",
            "26. 집중하기가 어렵습니까?",
            "27. 아침에 기분 좋게 일어나십니까?",
            "28. 사람들과 어울리는 자리를 피하는 편이십니까?",
            "29. 쉽게 결정하는 편이십니까?",
            "30. 예전처럼 정신이 맑습니까?"

    };
    public static final String TOTAL_SCORE="total_score";
    private int currentIndex = 0;
    private  static  final  String KEY_INDEX="current_index";
    private static final String TAG = "TextToSpeechDemo";
    private static final int MY_DATA_CHECK_CODE = 1234;
    private TextToSpeech myTTS;
    private TextView textSurvey;
    private RadioButton radioYes,radioNo;
    private Button btnNext, btnVoice;
    private int testScore=0;

    public test3Fragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container2, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_test3_fragment,container2,false);
        textSurvey= view.findViewById(R.id.textSurvey);
        btnNext=  view.findViewById(R.id.btnNext);
        radioYes = view.findViewById(R.id.radioYes);
        radioNo =  view.findViewById(R.id.radioNever);
        btnVoice = view.findViewById(R.id.btnVoice);
        btnVoice.setOnClickListener(this);
        myTTS = new TextToSpeech(getActivity(), this);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        if(savedInstanceState != null){
            currentIndex =savedInstanceState.getInt(KEY_INDEX);
            chageText();
        }
        textSurvey.setText(SURVEY_TEXT[0]);
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
            chageText();
            if(radioYes.isChecked()){
                radioYes.setChecked(false);
            }else if(radioNo.isChecked()){
                radioNo.setChecked(false);
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
                                    Intent intent = new Intent(getActivity(), test3Result.class);
                                    intent.putExtra(TOTAL_SCORE, testScore);
                                    startActivity(intent);
                                }

                            });
            alertDialog.create();
            alertDialog.show();
        }
    }
    private void chageText() {
        textSurvey.setText(SURVEY_TEXT[currentIndex]);
    }
    @Override
    public void onStart() {
        super.onStart();
        View view=getView();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioYes.isChecked()){
                    testScore += 0;
                }else if(radioNo.isChecked()){
                    testScore += 1;
                }
                //textresult.setText(testScore+ "");
                if( radioYes.isChecked() == false && radioNo.isChecked() == false){
                    Toast.makeText(getActivity(), "체크를 하신 후 다음문제로 넘어가실 수 있습니다.", Toast.LENGTH_SHORT).show();
                } else if (radioNo.isChecked() || radioYes.isChecked()){
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


        String text = textSurvey.getText().toString();
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
