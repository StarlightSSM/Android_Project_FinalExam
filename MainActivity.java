package com.inhatc.android_finalexamproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;

    // 이메일과 비밀번호 가져오기
    EditText edtEmail;
    EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }

    @Override
    public void onClick(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        // Firebase 인증
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Firebase 로그인 성공
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            assert user != null;
                            user.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String idToken = task.getResult().getToken();
                                                // 원하는 작업 수행 (예: 다음 화면으로 이동)
                                                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                Intent intent = new Intent(MainActivity.this, CaptchaActivity.class);
                                                //intent.putExtra("key", value); // 사용자 정보를 다음 화면으로 전달하는 예시
                                                startActivity(intent);

                                            } else {
                                                // idToken 가져오기 실패
                                                Toast.makeText(MainActivity.this, "idToken 가져오기 실패", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // Firebase 로그인 실패
                            Toast.makeText(MainActivity.this, "Firebase 로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
