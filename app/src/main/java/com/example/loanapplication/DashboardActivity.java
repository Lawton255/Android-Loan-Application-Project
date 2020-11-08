package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    Button mBtnRequestLoan, mBtnPayLoan, mBtnLoanHistory, mBtnLoanCalculator, mBtnProfile, mBtnVerifyEmail;
    TextView ViewVerifyEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mBtnRequestLoan = findViewById(R.id.btn_request_loan);
        mBtnPayLoan = findViewById(R.id.btn_pay_loan);
        mBtnLoanHistory = findViewById(R.id.btn_history);
        mBtnLoanCalculator = findViewById(R.id.btn_loan_calc);
        mBtnProfile = findViewById(R.id.btn_profile);
        mBtnVerifyEmail = findViewById(R.id.btn_verifyemail);
        ViewVerifyEmail = findViewById(R.id.verifymail_view);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();
        if (!user.isEmailVerified()){
            mBtnVerifyEmail.setVisibility(View.VISIBLE);
            ViewVerifyEmail.setVisibility(View.VISIBLE);

            mBtnVerifyEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Send verification link to user's email
                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DashboardActivity.this, "Verification Email has been sent. ", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }

        mBtnRequestLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RequestLoan.class));
            }
        });

        mBtnPayLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PayLoan.class));
            }
        });

        mBtnLoanHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoanHistory.class));
            }
        });

        mBtnLoanCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoanCalculator.class));
            }
        });

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
    }
}