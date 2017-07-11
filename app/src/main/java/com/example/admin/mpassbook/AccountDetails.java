package com.example.admin.mpassbook;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.mpassbook.global.AppController;
import com.example.admin.mpassbook.global.BaseActivity;
import com.example.admin.mpassbook.global.CircleTransform;
import com.example.admin.mpassbook.retrofit.ApiClient;
import com.example.admin.mpassbook.retrofit.ApiInterface;
import com.example.admin.mpassbook.retrofit.model.AccountDetail;
import com.example.admin.mpassbook.retrofit.model.AccountResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AccountDetails extends BaseActivity {

    ImageView imgDrawer;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageViewCustomer;
    TextView tvName;
    Spinner spinnerAccount;


    ImageView imageViewCustomerAc;
    TextView tv_acHolderName,tv_acno,tv_actype,tv_acopendate,tv_accreateddate,tv_acstatus,tv_acBalance,tv_branch,tv_ifsc;
    private List<AccountDetail> listAc = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        imageViewCustomerAc=(ImageView)findViewById(R.id.imageViewCustomer);
        tv_acHolderName=(TextView) findViewById(R.id.tv_name);
        tv_acno=(TextView)findViewById(R.id.acno);
        spinnerAccount=(Spinner) findViewById(R.id.spinnerAccount);
        tv_actype=(TextView) findViewById(R.id.tv_actype);
        tv_acopendate=(TextView) findViewById(R.id.tv_acopendate);
        tv_accreateddate=(TextView) findViewById(R.id.tv_accreateddate);
        tv_acstatus=(TextView) findViewById(R.id.tv_acstatus);
        tv_acBalance=(TextView) findViewById(R.id.tv_acbalance);
        tv_ifsc=(TextView) findViewById(R.id.tv_acifsc);
        tv_branch=(TextView) findViewById(R.id.tv_acbranch);
        String  name= AppController.getString(getApplicationContext(), "customer_name");
        String  image= AppController.getString(getApplicationContext(), "customer_image");

        Picasso.with(getApplicationContext()).load(image).resize(dp2px(30), 0).transform(new CircleTransform()).into(imageViewCustomerAc);

        tv_acHolderName.setText(name);

        getAccountList();

        //<include layout="@layout/content_accountdetails" />
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        imageViewCustomer=(ImageView)headerLayout.findViewById(R.id.imageViewCustomer);
        tvName=(TextView)headerLayout.findViewById(R.id.tvName);
        Log.d("name",name+image);
        Picasso.with(getApplicationContext()).load(image).resize(dp2px(25), 0).transform(new CircleTransform()).into(imageViewCustomer);
        tvName.setText(name);
        imgDrawer = (ImageView) findViewById(R.id.img_drawer);
        imgDrawer.setOnClickListener(this);
    }

    private void getAccountList() {
        final ProgressDialog progressDialog = new ProgressDialog(AccountDetails.this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        String  id= AppController.getString(getApplicationContext(), "customer_id");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<AccountResponse> call = apiService.AccountList(id);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, retrofit2.Response<AccountResponse> response) {

                progressDialog.dismiss();
                listAc = response.body().getAccountDetails();
                ArrayList<String> manufactureList = new ArrayList<String>();

                for (int i = 0; i < listAc.size(); i++) {

                    manufactureList.add(listAc.get(i).getAccountNo());
                }
                // Creating adapter for spinner service
                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(AccountDetails.this, android.R.layout.simple_spinner_item, manufactureList);
                // Drop down layout style - list view with radio button
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner;
                spinnerAccount.setAdapter(mAdapter);

                tv_acno.setText(listAc.get(0).getAccountNo());
                tv_actype.setText(listAc.get(0).getTypeAccount());
                tv_acstatus.setText(listAc.get(0).getAccountStatus());
                tv_accreateddate.setText(listAc.get(0).getCreatedDate());
                tv_acopendate.setText(listAc.get(0).getAccountOpenDate());

                tv_branch.setText(listAc.get(0).getBranch());
                tv_acBalance.setText(listAc.get(0).getAccountClosingBalance());
                tv_ifsc.setText(listAc.get(0).getIfsc());



                tv_acno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerAccount.performClick();
                    }
                });
                spinnerAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if (isService)

                        tv_acno.setText(spinnerAccount.getSelectedItem().toString());
                        tv_actype.setText(listAc.get(position).getTypeAccount());

                        tv_acstatus.setText(listAc.get(position).getAccountStatus());
                        tv_accreateddate.setText(listAc.get(position).getCreatedDate());
                        tv_acopendate.setText(listAc.get(position).getAccountOpenDate());
                        tv_branch.setText(listAc.get(position).getBranch());
                        tv_acBalance.setText(listAc.get(position).getAccountClosingBalance());
                        tv_ifsc.setText(listAc.get(position).getIfsc());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Log error here since request failed
                progressDialog.dismiss();

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to Exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent1 = new Intent(AccountDetails.this, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.img_drawer:
                drawer.openDrawer(navigationView);
                break;
        }
    }
}
