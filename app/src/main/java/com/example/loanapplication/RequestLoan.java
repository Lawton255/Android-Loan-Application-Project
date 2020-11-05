package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.channels.AlreadyBoundException;
import java.text.DecimalFormat;

public class RequestLoan extends AppCompatActivity {
    EditText mTxtAmount, mTxtPeriod;
    Button mBtnProceed;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_loan);

        mTxtAmount = findViewById(R.id.txt_loan_amount);
        mTxtPeriod = findViewById(R.id.txt_period);
        mBtnProceed = findViewById(R.id.btn_request_loan);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Sending");
        dialog.setMessage("Please wait...");

        mBtnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the saving here
                // Receive data from the user

                String loanAmount = mTxtAmount.getText().toString();
                final String loanPeriod = mTxtPeriod.getText().toString();


                if (loanAmount.isEmpty()){
                    mTxtAmount.setError("Please enter amount");
                } else if (loanPeriod.isEmpty()) {
                    mTxtPeriod.setError("Please enter period");
                } else {

                    double newAmount = (Integer.parseInt(loanAmount));
                    double newPeriod = (Integer.parseInt(loanPeriod));
                    double rate = ((15 / 100.0) / 12);
                    double rate1 = (1 - Math.pow((rate + 1), -newPeriod));
                    double monthlyPayment = ((rate * newAmount) / rate1);
                    double totalPayment = monthlyPayment * newPeriod;
                    final String monthlyResult;
                    monthlyResult = (new DecimalFormat("##.##").format(monthlyPayment));
                    final String totalResult;
                    totalResult = (new DecimalFormat("##.##").format(totalPayment));

                    // Connect to database table/child
                    long time = System.currentTimeMillis();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Loans/" + time);
                    dialog.show();
                    Loan loan = new Loan(loanAmount, loanPeriod, monthlyResult, totalResult, String.valueOf(time));
                    ref.setValue(loan).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()) {
                                message("SUCCESS", "You have successful requested for the loan, " +
                                                                    "You will pay:- "+
                                                                        " Monthly(Tshs):" +monthlyResult+
                                                                        " Total(Tshs):" +totalResult+
                                                                           " Period(Months):" +loanPeriod+ "");
                                clear();
                            } else {
                                message("FAILED!!", "Fail to sent your loan request");
                            }
                        }
                    });
                }
            }
        });
    }
    public void message (String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
        });
        alert.create().show();
    }

    public  void clear(){
        mTxtAmount.setText("");
        mTxtPeriod.setText("");
    }

}