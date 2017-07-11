package com.example.admin.mpassbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.mpassbook.global.AppController;
import com.example.admin.mpassbook.retrofit.ApiClient;
import com.example.admin.mpassbook.retrofit.ApiInterface;
import com.example.admin.mpassbook.retrofit.model.CommentResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class TransactionDetails extends AppCompatActivity implements View.OnClickListener {

    TextView tv_closingbalance,tv_accountno,tv_currentbalance,tv_descreption,tv_transactionid,tv_date;

    String closingbalance,amountTransaction,idTransaction,accountNo,descreptionTransaction,dateTime,appId;
    Button btnSubmit;
    EditText editTextComment;

    ImageView imageViewfinish;
    LinearLayout lifade;
    RelativeLayout activity_transaction_details;
    FloatingActionButton fabExpense;
    LinearLayout liback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_transaction_details);
        tv_accountno=(TextView)findViewById(R.id.accountno);
        tv_closingbalance=(TextView)findViewById(R.id.closingbalance);
        tv_currentbalance=(TextView)findViewById(R.id.totalbalance);
        tv_descreption=(TextView)findViewById(R.id.tv_TransactionDescr);
        tv_transactionid=(TextView)findViewById(R.id.transactionid);
        tv_date=(TextView)findViewById(R.id.transactionDate);
        editTextComment=(EditText) findViewById(R.id.edit_comment);
        lifade=(LinearLayout) findViewById(R.id.limiddle);
        activity_transaction_details=(RelativeLayout)findViewById(R.id.activity_transaction_details);
        fabExpense=(FloatingActionButton)findViewById(R.id.fabExpense);

        btnSubmit=(Button) findViewById(R.id.btn_submit);
        imageViewfinish=(ImageView) findViewById(R.id.img_drawer);
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        lifade.startAnimation(animationFadeIn);

        imageViewfinish.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        fabExpense.setOnClickListener(this);

        liback=(LinearLayout)findViewById(R.id.liback);
        liback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        gd.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        btnSubmit.setBackgroundDrawable(gd);

        closingbalance = getIntent().getStringExtra("getAccountClosingBalance");
        amountTransaction = getIntent().getStringExtra("getTransactionAmount");
        idTransaction = getIntent().getStringExtra("getTransactionId");
        accountNo = getIntent().getStringExtra("getAccountNo");
        descreptionTransaction = getIntent().getStringExtra("getTransactionDescription");
        dateTime = getIntent().getStringExtra("dateTime");
        appId = getIntent().getStringExtra("appId");



        tv_accountno.setText(accountNo);
        tv_closingbalance.setText(closingbalance);
        tv_currentbalance.setText(amountTransaction);
        if (descreptionTransaction!=null)
        tv_descreption.setText(descreptionTransaction);
        tv_transactionid.setText(appId);

        tv_date.setText(dateTime);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_drawer:
                finish();
                break;
            case R.id.fabExpense:
                Intent intent=new Intent(TransactionDetails.this,ExpenseManager.class);
                startActivity(intent);
                break;
            case R.id.btn_submit:
                String comment=editTextComment.getText().toString();
                if (editTextComment.getText().toString().length()>0)
                {

                    Comment(comment);
                }
                break;
        }
    }


    public void Comment(String comment)
             {

        final ProgressDialog progressDialog = new ProgressDialog(TransactionDetails.this);

        progressDialog.setMessage("loading");
        progressDialog.show();
        String  id= AppController.getString(getApplicationContext(), "customer_id");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CommentResponse> call = apiService.CommentTransaction(id,comment,idTransaction);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, retrofit2.Response<CommentResponse> response) {

                progressDialog.hide();

                if (response.isSuccessful()) {
                    Snackbar snackbar = Snackbar
                            .make(activity_transaction_details, response.body().getStatus(), Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                   }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(activity_transaction_details, "server error", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }


            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                // Log error here since request failed
                //progressDialog.dismiss();

            }
        });
    }

}
