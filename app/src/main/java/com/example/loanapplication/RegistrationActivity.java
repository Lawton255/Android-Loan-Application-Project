package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class RegistrationActivity extends AppCompatActivity {
    EditText mEdtSname, mEdtEmail, mEdtPhone, mEdtPassword;
    Button mBtnRegister;
    TextView mTxtLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mEdtSname = findViewById(R.id.txt_sname);
        mEdtEmail = findViewById(R.id.txt_email);
        mEdtPhone = findViewById(R.id.txt_phone);
        mEdtPassword = findViewById(R.id.txt_password);
        mBtnRegister = findViewById(R.id.btn_register);
        mTxtLogin = findViewById(R.id.txt_view_log);
        progressBar = findViewById(R.id.regprogressBar);
        fAuth = FirebaseAuth.getInstance();


        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = mEdtSname.getText().toString().trim();
                String email = mEdtEmail.getText().toString().trim();
                String phone = mEdtPhone.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();



                if (fullName.isEmpty()){
                    mEdtSname.setError("Enter Full name.");
                }else if (email.isEmpty()){
                    mEdtEmail.setError("Enter email.");
                } else if (phone.length() < 10){
                    mEdtPhone.setError("Phone number must be greater than 10 numbers");
                }else if (password.length() < 6){
                    mEdtPassword.setError("Password must be >= to 6 characters");
                }else {

                   progressBar.setVisibility(View.VISIBLE);

                    // register the user in firebase
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "User successful created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            }else {
                                Toast.makeText(RegistrationActivity.this, "Error occured !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}