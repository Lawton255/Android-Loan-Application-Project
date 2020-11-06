package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {
    EditText mEdtSname, mEdtEmail, mEdtPhone, mEdtPassword;
    Button mBtnRegister;
    TextView mTxtLogin;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;

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
        fStore = FirebaseFirestore.getInstance();


        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = mEdtSname.getText().toString();
                final String email = mEdtEmail.getText().toString().trim();
                final String phone = mEdtPhone.getText().toString();
                String password = mEdtPassword.getText().toString().trim();


                if (TextUtils.isEmpty(fullName)){
                    mEdtEmail.setError("Enter Full name.");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    mEdtEmail.setError("Email is required.");
                    return;
                }
                if (phone.length() < 10){
                    mEdtEmail.setError("Phone number must be greater than 10 numbers");
                    return;
                }
                if (password.length() < 6){
                    mEdtEmail.setError("Password must be >= to 6 characters");
                    return;
                }


                   progressBar.setVisibility(View.VISIBLE);

                    // register the user in firebase
                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "User successful created.", Toast.LENGTH_SHORT).show();
                                userID = fAuth.getCurrentUser().getUid(); // call the userID from stored data
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("fName", fullName);
                                user.put("email", email);
                                user.put("phone", phone);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("TAG", "onsuccess: user profile is created for" + userID);
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                            }else {
                                Toast.makeText(RegistrationActivity.this, "Error occured !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

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