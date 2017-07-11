
package com.example.admin.mpassbook.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountDetail {

    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("account_status")
    @Expose
    private String accountStatus;
    @SerializedName("account_open_date")
    @Expose
    private String accountOpenDate;
    @SerializedName("account_branch")
    @Expose
    private String accountBranch;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("type_account")
    @Expose
    private String typeAccount;

    public String getAccountClosingBalance() {
        return accountClosingBalance;
    }

    public void setAccountClosingBalance(String accountClosingBalance) {
        this.accountClosingBalance = accountClosingBalance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @SerializedName("account_closing_balance")
    @Expose
    private String accountClosingBalance;
    @SerializedName("branch")
    @Expose
    private String branch;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    public String getAccountBranch() {
        return accountBranch;
    }

    public void setAccountBranch(String accountBranch) {
        this.accountBranch = accountBranch;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

}
