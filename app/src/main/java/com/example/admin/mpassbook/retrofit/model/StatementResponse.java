
package com.example.admin.mpassbook.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatementResponse {

    @SerializedName("statement_url")
    @Expose
    private String statementUrl;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatementUrl() {
        return statementUrl;
    }

    public void setStatementUrl(String statementUrl) {
        this.statementUrl = statementUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
