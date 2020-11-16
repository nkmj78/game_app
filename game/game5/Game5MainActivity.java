package edu.android.appgame.game.game5;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import edu.android.appgame.R;

public class Game5MainActivity extends AppCompatActivity {
    private static final String IMAGEVIEW_TAG = "드래그 이미지";
    static final String CORRECT_SCORE = "correct_score";
    static final String TOTAL_Q = "total_question";
    private TextView textNum1, textNum2, textresult, textSign1, textSign2, textNum3, textSign;
    private static final String TAG = "tag";
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnMul, btnPlus, btn, btnMinus, btnDelete;

    private EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8;
    private String sign2 = "";
    private String sign3 = "";
    private int x1 = 0;
    private int y1 = 0;
    private int z1 = 0;
    private int result;
    private int count = 0; // 맞힌 갯수
    private int total = 0; // 전체 문제 수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game5_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorDarkBlue)));
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorDarkBlue));

        textNum1 = findViewById(R.id.textNum1);
        textNum2 = findViewById(R.id.textNum2);
        textNum3 = findViewById(R.id.textNum3);
        textresult = findViewById(R.id.textresult);
        textSign1 = findViewById(R.id.textSign1);
        textSign2 = findViewById(R.id.textsign2);
        textSign = findViewById(R.id.textsign);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btn = findViewById(R.id.btn9);
        btnMul = findViewById(R.id.btnMul);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnDelete = findViewById(R.id.btnDelete);

        edit1 = findViewById(R.id.editNum1);
        edit1.setInputType(0);
        edit2 = findViewById(R.id.editSign1);
        edit2.setInputType(0);
        edit3 = findViewById(R.id.editNum2);
        edit3.setInputType(0);
        edit4 = findViewById(R.id.editSign2);
        edit4.setInputType(0);
        edit5 = findViewById(R.id.editNum3);
        edit5.setInputType(0);
        edit6 = findViewById(R.id.edit);
        edit6.setInputType(0);
        edit7 = findViewById(R.id.editNum4);
        randomNumber();
    }  //end onCreate


    // x1, y1, z1 숫자 랜덤 돌리기
    public void randomNumber() {
        total++;
        Random rnd = new Random();
        int x1 = rnd.nextInt(9) + 1;
        textNum1.setText(String.valueOf(x1));
        int y1 = rnd.nextInt(99) + 1;
        textNum2.setText(String.valueOf(y1));
        int z1 = rnd.nextInt(9) + 1;
        textNum3.setText(String.valueOf(z1));
        int index = rnd.nextInt(2);
        int index2 = rnd.nextInt(2);
        String[] sign = {"+", "-", "x"};
        result = 0;
        switch (index) {
            case 0:
                sign2 = sign[index];
                while (true) {
                    if (index2 == index) {
                        index2 = rnd.nextInt(2);
                    } else {
                        break;
                    }
                }
                Log.i(TAG, "index2 = " + index2);
                switch (index2) {
                    case 1:
                        sign3 = sign[index2];
                        result = x1 + y1 - z1;
                        break;
                    case 2:
                        sign3 = sign[index2];
                        result = x1 + y1 * z1;
                        break;
                }
                break;
            case 1:
                sign2 = sign[index];
                while (true) {
                    if (index2 == index) {
                        index2 = rnd.nextInt(2);
                    } else {
                        break;
                    }
                }
                switch (index2) {
                    case 0:
                        sign3 = sign[index2];
                        result = x1 - y1 + z1;
                        break;
                    case 2:
                        sign3 = sign[index2];
                        result = x1 - y1 * z1;
                        break;
                }

                break;
            case 2:
                sign2 = sign[index];
                while (true) {
                    if (index2 == index) {
                        index2 = rnd.nextInt(2);
                    } else {
                        break;
                    }
                }
                switch (index2) {
                    case 0:
                        sign3 = sign[index2];
                        result = x1 * y1 + z1;
                        break;
                    case 1:
                        sign3 = sign[index2];
                        result = x1 * y1 - z1;
                        break;
                }
                break;
            default:
        }
        Log.i(TAG, "x1 =" + x1);
        Log.i(TAG, "y1 =" + y1);
        Log.i(TAG, "z1 =" + z1);
        Log.i(TAG, "result =" + result);
        textSign1.setText(sign2);
        textSign.setText(sign3);

        textresult.setText(String.valueOf(result));
        ButtonClick();

    }        //end randomNumber

    private String getNumberString(View view, ImageButton[] buttons) {
        int index = 0;
        for (; index < buttons.length; index++) {
            if (view == buttons[index]) {
                break;
            }
        }
        return String.valueOf(index);
    }

    // 버튼 클릭시 그 숫자가 edittext로 들어감
    // 부호 넣는 자리에는 부호만 넣고, 숫자 넣는 자리에는 숫자만 넣게
    private void ButtonClick() {
        final Button[] button = new Button[15];
        final Button[] numbutton = {btn0, btn1, btn2, btn3, btn4,
                btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btn, btnMul, btnDelete};

        for (int i = 0; i < numbutton.length; i++) {
            button[i] = numbutton[i];

        }
        for (int i = 0; i < numbutton.length; i++) {
            final int index;
            index = i;
            button[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a, b, c, d;
                    if (edit1.isFocused() == true) {
                        if (v == btnMul || v == btnPlus || v == btn || v == btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 숫자만 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit1.setText("");
                        }  else {
                            a = edit1.getText().toString() + button[index].getText();
                            edit1.setText(a);
                        } if (v == btnDelete) {
                            edit1.setText("");
                        }


                    } else if (edit2.isFocused() == true) {
                        if (v != btnMul && v != btnPlus && v != btn && v != btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 부호를 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit2.setText("");
                        } else {
                            b = (String) button[index].getText();
                            edit2.setText(b);
                        }
                        if (v == btnDelete) {
                            edit2.setText("");
                        }

                    } else if (edit3.isFocused() == true) {
                        if (v == btnMul || v == btnPlus || v == btn || v == btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 숫자만 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit3.setText("");
                        } else {
                            a = edit3.getText().toString() + button[index].getText();
                            edit3.setText(a);
                        }
                        if (v == btnDelete) {
                            edit3.setText("");
                        }

                    } else if (edit4.isFocused() == true) {
                        if (v != btnMul && v != btnPlus && v != btn && v != btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 부호를 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit4.setText("");
                        } else {
                            b = (String) button[index].getText();
                            edit4.setText(b);
                        }
                        if (v == btnDelete) {
                            edit4.setText("");
                        }
                    } else if (edit5.isFocused() == true) {
                        if (v == btnMul || v == btnPlus || v == btn || v == btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 숫자만 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit5.setText("");
                        } else {
                            a = edit5.getText().toString() + button[index].getText();
                            edit5.setText(a);
                        }
                        if (v == btnDelete) {
                            edit5.setText("");
                        }
                    } else if (edit7.isFocused() == true) {
                        if (v == btnMul || v == btnPlus || v == btn || v == btnMinus) {
                            Toast.makeText(Game5MainActivity.this, "이 자리에는 숫자만 넣어주세요!", Toast.LENGTH_SHORT).show();
                            edit7.setText("");
                        } else {
                            a = edit7.getText().toString() + button[index].getText();
                            edit7.setText(a);
                        }
                        if (v == btnDelete) {
                            edit7.setText("");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "edittext를 선택하세요^_^", Toast.LENGTH_SHORT).show();
                    }


                }

            });

        }
        //end ButtonClick
    }

    public void btnCheck(View view) {
        if (edit1.getText().toString().equals(String.valueOf(x1))
                || edit2.getText().toString().equals(sign2)
                || edit3.getText().toString().equals(String.valueOf(y1))
                || edit4.getText().toString().equals(sign3)
                || edit5.getText().toString().equals(z1)
                || edit7.getText().toString().equals(String.valueOf(result))) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("!!!!!!!정답입니다!!!!!!");
            alertDialog.setMessage("다음 문제로 넘어가시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    randomNumber();
                                    clearText();
                                }
                            })


                    .setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // TODO
                                }
                            });
            alertDialog.create();
            alertDialog.show();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("오답입니다ㅠㅠ");
            alertDialog.setMessage("다음 문제로 넘어가시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    randomNumber();
                                    clearText();
                                }
                            })


                    .setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    // TODO
                                }
                            });
            alertDialog.create();
            alertDialog.show();
        }

    }

     // end btnCheck

    private void clearText() {
        edit1.setText("");
        edit2.setText("");
        edit3.setText("");
        edit4.setText("");
        edit5.setText("");
        edit7.setText("");
    } // end clearText

    public void onClickbtnStop(View view) {
        Intent intent = new Intent (this, Game5ScoreActivity.class);
        intent.putExtra(CORRECT_SCORE, count);
        intent.putExtra(TOTAL_Q, total);
        startActivity(intent);
        this.finish();
    }


}



