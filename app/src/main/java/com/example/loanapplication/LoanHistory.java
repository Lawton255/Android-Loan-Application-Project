package com.example.loanapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoanHistory extends AppCompatActivity {
    ArrayList<Loan> loans;
    RecyclerView mRecyclerLoans;
    LoanAdapter adapter;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_history);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait.....");

        loans = new ArrayList<>();
        mRecyclerLoans = findViewById(R.id.mRecyclerLoans);
        mRecyclerLoans.setHasFixedSize(true);
        mRecyclerLoans.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LoanAdapter(this, loans);

        // connect to firebase database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Loans/");
        dialog.show();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loans.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Loan loan = snapshot.getValue(Loan.class);
                    loans.add(loan);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                message("DATABASE LOCKED", "Sorry, we couldn't access the DB. Contact your app developer for assistance");
            }
        });
        mRecyclerLoans.setAdapter(adapter);
    }
    public void message(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}