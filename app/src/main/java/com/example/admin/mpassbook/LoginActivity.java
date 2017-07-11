package com.example.admin.mpassbook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.admin.mpassbook.global.AppController;
import com.example.admin.mpassbook.global.BaseActivity;
import com.example.admin.mpassbook.modules.HomePage.Homepage;
import com.example.admin.mpassbook.retrofit.ApiClient;
import com.example.admin.mpassbook.retrofit.ApiInterface;
import com.example.admin.mpassbook.retrofit.model.LoginResponse;

import me.philio.pinentry.PinEntryView;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BaseActivity  {

    //private EditText mPinFifthDigitEditText;
    private EditText mPinHiddenEditText;
    private Button buttonConfirm;
    private ApiInterface apiService;
    private ProgressDialog progressDialog;
    LinearLayout pin_content_layout;
    PinEntryView pinEntry;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.login);
        mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);
        buttonConfirm = (Button) findViewById(R.id.btn_confirm);
        pin_content_layout=(LinearLayout)findViewById(R.id.pin_content_layout);
        buttonConfirm.setOnClickListener(this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        apiService = ApiClient.getClient().create(ApiInterface.class);//retrofit
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        gd.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        buttonConfirm.setBackgroundDrawable(gd);
        pinEntry = (PinEntryView) findViewById(R.id.pin_entry_border);
        if (pinEntry != null) {
            /*pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.length()==4) {
                        Sigin(pinEntry.getText().toString());
                    } else {
                        Toast.makeText(LoginActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });*/
        }
    }


    /**
     * Initialize EditText fields.
     */



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_confirm:
                if (pinEntry != null)
                    Sigin(pinEntry.getText().toString());
                    break;
        }

    }


    //api call for singin
    public void Sigin(String pin) {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        progressDialog.setMessage("loading");
        progressDialog.show();
        Call<LoginResponse> call = apiService.Singin(pin, "9846386736");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                        Intent intent = new Intent(LoginActivity.this, Homepage.class);

                        AppController.setString(getApplicationContext(), "customer_name", response.body().getCustomerFname());
                        AppController.setString(getApplicationContext(), "customer_id", response.body().getCustomerId());
                        AppController.setString(getApplicationContext(), "customer_image", response.body().getCustomerProfileImage());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(pin_content_layout, "Enter correct pin", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });

    }


    @Override
    public void onBackPressed() {


        moveTaskToBack(true);

    }
}
