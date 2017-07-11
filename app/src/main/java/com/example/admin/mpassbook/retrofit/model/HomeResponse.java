
package com.example.admin.mpassbook.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeResponse {

    @SerializedName("basic_account_details")
    @Expose
    private BasicAccountDetails basicAccountDetails;
    @SerializedName("transaction_details")
    @Expose
    private List<TransactionDetail> transactionDetails = null;

    public BasicAccountDetails getBasicAccountDetails() {
        return basicAccountDetails;
    }

    public void setBasicAccountDetails(BasicAccountDetails basicAccountDetails) {
        this.basicAccountDetails = basicAccountDetails;
    }

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

}
