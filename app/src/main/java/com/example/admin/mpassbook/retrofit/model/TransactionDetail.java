
package com.example.admin.mpassbook.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionDetail {

    @SerializedName("app_transaction_id")
    @Expose
    private String appTransactionId;

    public String getAppTransactionId() {
        return appTransactionId;
    }

    public void setAppTransactionId(String appTransactionId) {
        this.appTransactionId = appTransactionId;
    }

    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("transaction_type")
    @Expose
    private String transactionType;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_description")
    @Expose
    private String transactionDescription;
    @SerializedName("account_closing_balance")
    @Expose
    private String accountClosingBalance;
    @SerializedName("transaction_date")
    @Expose
    private String transactionDate;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("type_transaction")
    @Expose
    private String typeTransaction;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getAccountClosingBalance() {
        return accountClosingBalance;
    }

    public void setAccountClosingBalance(String accountClosingBalance) {
        this.accountClosingBalance = accountClosingBalance;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

}
