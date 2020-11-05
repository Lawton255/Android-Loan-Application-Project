package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText mEdtEmail, mEdtPassword;
    Button mBtnLogin;
    TextView mTxtRegistration;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtEmail = findViewById(R.id.txt_myemail);
        mEdtPassword = findViewById(R.id.txt_mypassword);
        mBtnLogin = findViewById(R.id.btn_login);
        mTxtRegistration = findViewById(R.id.txt_registration);
        progressBar = findViewById(R.id.logprogressBar);
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEdtEmail.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEdtEmail.setError("Email is required.");
                    return;
                }
                if (password.length() < 6){
                    mEdtPassword.setError("Password must be >= 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this, "Logged in successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    }else {
                        Toast.makeText(Login.this, "Error occured !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    }
                });

            }
        });

        mTxtRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });

    }
}