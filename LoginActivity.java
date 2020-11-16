package edu.android.appgame;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    public static final String LOGIN = "login_success";
    public static final String LOGIN_ID = "login_id";

    private EditText editId, editPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editId = findViewById(R.id.editId);
        editPassword = findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();
    } // end onCreate()

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void onClickLogin(View view) {

        final String email = editId.getText().toString();
        String password = editPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "아이디와 비밀번호를 모두 입력하세요", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, email.split("@")[0] + "님, 환영합니다", Toast.LENGTH_SHORT).show();

                                Log.i(TAG, "getEmail " +mAuth.getCurrentUser().getEmail());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(LOGIN, true); // 로그인 여부 전달
                                intent.putExtra(LOGIN_ID, email.split("@")[0]); // 로그인 아이디 @ 앞 전달
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                LoginActivity.this.finish();

                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "로그인 실패",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    } // end onClickLogin()


    public void onClickBtnSignUp(View view) {
        Intent intent = new Intent (this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onClickBtnHome(View view) {
        finish();
    }




} // end class LoginActivity
