package com.example.loanapplication;

public class Loan {
    private String loanAmount, loanPeriod, monthlyResult, totalResult, time;
    public Loan(String loanAmount, String loanPeriod, String monthlyResult, String totalResult, String time){
        this.loanAmount = loanAmount;
        this.loanPeriod = loanPeriod;
        this.monthlyResult = monthlyResult;
        this.totalResult = totalResult;
        this.time = time;
    }
    public Loan() {

    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getMonthlyResult() {
        return monthlyResult;
    }

    public void setMonthlyResult(String monthlyResult) {
        this.monthlyResult = monthlyResult;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
