package com.example.loanapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LoanCalculator extends AppCompatActivity {
    TextView mViewMonthlyPayment, mViewTotalPayment;
    EditText txtLoanAmount, txtInterestRate, txtMonths;
    Button mBtnCalculatePayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);

        mViewMonthlyPayment = findViewById(R.id.view_monthly);
        mViewTotalPayment = findViewById(R.id.view_total);

        txtLoanAmount = findViewById(R.id.loan_amount_txt);
        txtInterestRate = findViewById(R.id.interest_rate_txt);
        txtMonths = findViewById(R.id.period_txt);

        mBtnCalculatePayments = findViewById(R.id.btn_calculate);

        mBtnCalculatePayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double loanAmount = (Integer.parseInt(txtLoanAmount.getText().toString()));
                double interestRate = (Float.parseFloat(txtInterestRate.getText().toString()));
                double loanPeriod = (Float.parseFloat(txtMonths.getText().toString()));
                double rate = ((interestRate / 100.0) / 12);
                double rate1 = (1-Math.pow((rate + 1), -loanPeriod));

                // double monthlyPayment = (monthlyRate * loan)/(1-Math.pow((1+monthlyRate), -termsInMonths));
                double monthlyPayment = (rate * loanAmount) / rate1;
                double totalPayment = monthlyPayment * loanPeriod;

                mViewMonthlyPayment.setText(new DecimalFormat("##.##").format(monthlyPayment));
                mViewTotalPayment.setText(new DecimalFormat("##.##").format(totalPayment));
            }
        });

    }
}