
package com.example.admin.mpassbook.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_fname")
    @Expose
    private String customerFname;
    @SerializedName("customer_profile_image")
    @Expose
    private String customerProfileImage;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getCustomerProfileImage() {
        return customerProfileImage;
    }

    public void setCustomerProfileImage(String customerProfileImage) {
        this.customerProfileImage = customerProfileImage;
    }

}
