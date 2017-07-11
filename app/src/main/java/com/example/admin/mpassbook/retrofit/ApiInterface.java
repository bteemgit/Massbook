package com.example.admin.mpassbook.retrofit;


import com.example.admin.mpassbook.retrofit.model.AccountResponse;
import com.example.admin.mpassbook.retrofit.model.CommentResponse;
import com.example.admin.mpassbook.retrofit.model.HomeResponse;
import com.example.admin.mpassbook.retrofit.model.LoginResponse;
import com.example.admin.mpassbook.retrofit.model.StatementResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

   /* @FormUrlEncoded
    @POST("main/forgot-password")
    Call<LoginResponse> ForgotPasscode(@Field("username") String username);


    @GET("vehicle")
    Call<VechicleListResponse> VehicleList();

    @GET("service-categories")
    Call<CatagoriesListresponse> CatagoriesList(@Query("expand") String services);*/
    //http://dev.bteem.com/ICH/Menu_api/menu?category_id=15
   @FormUrlEncoded
   @POST("service/login")
   Call<LoginResponse> Singin(@Field("pin") String pin, @Field("user_id") String user_id);

   @FormUrlEncoded
   @POST("service/user_account_details")
   Call<HomeResponse> TransactionList(@Field("customer_id") String user_id,
                                      @Field("account_id") String ac_id,
                                      @Field("transaction_type") String transaction);

   @FormUrlEncoded
   @POST("service/user_account_details")
   Call<HomeResponse> SearchTransactionList(@Field("customer_id") String user_id ,
                                            @Field("transaction_type") String type,
                                            @Field("from_date") String fromdate,
                                            @Field("to_date") String todate,
                                            @Field("account_id") String ac_id);

   @FormUrlEncoded
   @POST("service/get_user_accounts")
   Call<AccountResponse> AccountList(@Field("customer_id") String user_id);

   @FormUrlEncoded
   @POST("service/add_transaction_comment")
   Call<CommentResponse> CommentTransaction(@Field("customer_id") String user_id, @Field("transaction_comment") String comment,
                                            @Field("transaction_id") String transaction_id);

   @FormUrlEncoded
   @POST("service/get_account_statement_pdf")
   Call<StatementResponse> ViewStatement(@Field("customer_id") String user_id, @Field("account_id") String account,
                                          @Field("transaction_type") String transaction_id);

   @FormUrlEncoded
   @POST("service/get_account_statement_pdf")
   Call<CommentResponse> EmailStatement(@Field("customer_id") String user_id, @Field("account_id") String account,
                                          @Field("transaction_type") String transaction_id);
}
