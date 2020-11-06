package com.example.loanapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    Button mBtnRequestLoan, mBtnPayLoan, mBtnLoanHistory, mBtnLoanCalculator, mBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mBtnRequestLoan = findViewById(R.id.btn_request_loan);
        mBtnPayLoan = findViewById(R.id.btn_pay_loan);
        mBtnLoanHistory = findViewById(R.id.btn_history);
        mBtnLoanCalculator = findViewById(R.id.btn_loan_calc);
        mBtnProfile = findViewById(R.id.btn_profile);

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