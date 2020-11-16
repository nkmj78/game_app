package edu.android.appgame;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    private static String ID;

    private EditText editId, editPass, editPassTest, editName, editBirthNo;
    private static String sex;

    private FirebaseDatabase database;  // 데이터베이스에 접근할 수 있는 진입점 클래스
    private DatabaseReference myRef;    // 데이터베이스의 주소 저장

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editId = findViewById(R.id.editId);
        editPass = findViewById(R.id.editPass);
        editPassTest = findViewById(R.id.editPassTest);
        editName = findViewById(R.id.editName);
        editBirthNo = findViewById(R.id.editBirthNo);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Member");
        mAuth = FirebaseAuth.getInstance();

    } // end onCreate()

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            clearText();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "비밀번호 6자리 이상 입력.",
                                    Toast.LENGTH_SHORT).show();
                            editPass.setText("");
                            editPassTest.setText("");
                        }
                    }
                });
    } // end createAccount()

    private void saveNewMember(String id, String email, String pw, String pw2, String name, String sex, String birth){

        Member member = new Member(ID, email, pw, pw2, name, this.sex, birth);
//        Member member1 = new Member(0,0,0,0,0);

        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = member.toMap();

        childUpdates.put("/" + ID + "/", postValues);
        myRef.updateChildren(childUpdates);

        createAccount(email, pw);

        Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show();
        this.finish();

    } // end saveNewMember()

    public void onClickSignUp(View view) {
        ID = editId.getText().toString().split("@")[0];
        String email = editId.getText().toString();
        String password = editPass.getText().toString();
        String password2 = editPassTest.getText().toString();
        String name = editName.getText().toString();
        String birth = editBirthNo.getText().toString();

        if(ID.equals("") || email.equals("") || password.equals("") || password2.equals("") || name.equals("") || birth.equals("")){
            Toast.makeText(this, "빈칸을 모두 채우세요", Toast.LENGTH_SHORT).show();
        } else {
            if (password.equals(password2)) {
                // 저장 메소드 호출
                saveNewMember(ID, email, password, password2, name, sex, birth);

            } else {
                Toast.makeText(this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                editPass.setText("");
                editPassTest.setText("");
            }
        }
    } // end onClickSignUp()

    private void clearText(){
        editId.setText("");
        editName.setText("");
        editPass.setText("");
        editPassTest.setText("");
        editBirthNo.setText("");
    } // end clearText()

    public void onClickMan(View view) {
        sex = "남자";
    }

    public void onClickWoman(View view) {
        sex = "여자";
    }
} // end class SignUpActivity
