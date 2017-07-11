package com.example.admin.mpassbook;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.mpassbook.global.AppController;
import com.example.admin.mpassbook.global.BaseActivity;
import com.example.admin.mpassbook.global.CircleTransform;
import com.example.admin.mpassbook.modules.HomePage.AllTransactionsAdapter;
import com.example.admin.mpassbook.recyclerview.RecyclerItemClickListener;
import com.example.admin.mpassbook.recyclerview.SimpleDividerItemDecoration;
import com.example.admin.mpassbook.retrofit.ApiClient;
import com.example.admin.mpassbook.retrofit.ApiInterface;
import com.example.admin.mpassbook.retrofit.model.AccountDetail;
import com.example.admin.mpassbook.retrofit.model.AccountResponse;
import com.example.admin.mpassbook.retrofit.model.HomeResponse;
import com.example.admin.mpassbook.retrofit.model.TransactionDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    ImageView imgDrawer;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageViewCustomer;
    TextView tvName;


    LinearLayout linearLayoutTypes,lisearch;
    RelativeLayout rootview;
    TextView tv_all,tv_credit,tv_debit,tv_todate,tv_fromdate,tv_acNo,tv_acType;
    ImageView imageViewTodate,imageViewFromdate;
    private int mYear, mMonth, mDay;
    Button btn_search;
    private List<TransactionDetail> AllTransactionsList = new ArrayList<>();
    private List<AccountDetail> listAc = new ArrayList<>();
    private RecyclerView recyclerViewTransactions;
    String transaction_type="5";
    String toDate="";
    String fromDate="";
    String ac_id="";
    Spinner spinnerAccount;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_navigation);

        linearLayoutTypes=(LinearLayout)findViewById(R.id.li_transactiontypes);
        rootview=(RelativeLayout) findViewById(R.id.activity_search);
        lisearch=(LinearLayout)findViewById(R.id.lisearch);
        tv_acNo=(TextView)findViewById(R.id.acno);
        tv_acType=(TextView)findViewById(R.id.actype);
        spinnerAccount=(Spinner) findViewById(R.id.spinnerAccount);
        tv_all=(TextView)findViewById(R.id.tv_all);
        tv_credit=(TextView)findViewById(R.id.tv_credit);
        tv_debit=(TextView)findViewById(R.id.tv_debit);
        tv_todate=(TextView)findViewById(R.id.tv_todate);
        tv_fromdate=(TextView)findViewById(R.id.tv_fromodate);
        imageViewTodate=(ImageView) findViewById(R.id.imageViewTodate);
        imageViewFromdate=(ImageView) findViewById(R.id.imageViewFromodate);
        btn_search=(Button) findViewById(R.id.btn_search);
        recyclerViewTransactions=(RecyclerView)findViewById(R.id.rv_transactions) ;

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(ContextCompat.getColor(getApplicationContext(), R.color.white)); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(10);
        gd.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
        linearLayoutTypes.setBackgroundDrawable(gd);

        GradientDrawable gdb = new GradientDrawable();
        gdb.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)); // Changes this drawbale to use a single color instead of a gradient
        gdb.setCornerRadius(7);
        gdb.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        btn_search.setBackgroundDrawable(gdb);

        tv_all.setOnClickListener(this);
        tv_credit.setOnClickListener(this);
        tv_debit.setOnClickListener(this);
        imageViewFromdate.setOnClickListener(this);
        imageViewTodate.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        
        getAccountList();
        setupClickListener();


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
        String  name= AppController.getString(getApplicationContext(), "customer_name");
        String  image= AppController.getString(getApplicationContext(), "customer_image");
        Log.d("name",name+image);
        Picasso.with(getApplicationContext()).load(image).resize(dp2px(25), 0).transform(new CircleTransform()).into(imageViewCustomer);
        tvName.setText(name);
        imgDrawer = (ImageView) findViewById(R.id.img_drawer);
        imgDrawer.setOnClickListener(this);
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
                    Intent intent1 = new Intent(SearchActivity.this, LoginActivity.class);
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


    private void getAccountList() {
        final ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        String  id= AppController.getString(getApplicationContext(), "customer_id");
      ApiInterface  apiService =
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
                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_item, manufactureList);
                // Drop down layout style - list view with radio button
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner;
                spinnerAccount.setAdapter(mAdapter);

                tv_acNo.setText(listAc.get(0).getAccountNo());
                tv_acType.setText(listAc.get(0).getTypeAccount());
                ac_id=listAc.get(0).getAccountId();


                tv_acNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerAccount.performClick();
                    }
                });
                spinnerAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if (isService)

                        tv_acNo.setText(spinnerAccount.getSelectedItem().toString());
                        tv_acType.setText(listAc.get(position).getTypeAccount());
                        ac_id=listAc.get(position).getAccountId();

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
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.tv_all:
                tv_all.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                tv_all.setTextColor(getResources().getColor(R.color.white));
                tv_credit.setBackgroundColor(getResources().getColor(R.color.white));
                tv_credit.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_debit.setBackgroundColor(getResources().getColor(R.color.white));
                tv_debit.setTextColor(getResources().getColor(R.color.colorAccent));
                lisearch.setVisibility(View.VISIBLE);
                recyclerViewTransactions.setVisibility(View.GONE);
                transaction_type="";
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(10);
                gd.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                linearLayoutTypes.setBackgroundDrawable(gd);
                break;
            case R.id.tv_credit:
                tv_all.setBackgroundColor(getResources().getColor(R.color.white));
                tv_all.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_credit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                tv_credit.setTextColor(getResources().getColor(R.color.white));
                tv_debit.setBackgroundColor(getResources().getColor(R.color.white));
                tv_debit.setTextColor(getResources().getColor(R.color.colorAccent));
                lisearch.setVisibility(View.VISIBLE);
                recyclerViewTransactions.setVisibility(View.GONE);
                transaction_type="1";
                GradientDrawable gd2 = new GradientDrawable();
                gd2.setCornerRadius(10);
                gd2.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                linearLayoutTypes.setBackgroundDrawable(gd2);
                break;
            case R.id.tv_debit:
                tv_all.setBackgroundColor(getResources().getColor(R.color.white));
                tv_all.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_credit.setBackgroundColor(getResources().getColor(R.color.white));
                tv_credit.setTextColor(getResources().getColor(R.color.colorAccent));
                tv_debit.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                tv_debit.setTextColor(getResources().getColor(R.color.white));
                lisearch.setVisibility(View.VISIBLE);
                recyclerViewTransactions.setVisibility(View.GONE);
                transaction_type="2";
                GradientDrawable gd3 = new GradientDrawable();
                gd3.setCornerRadius(10);
                gd3.setStroke(1, ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                linearLayoutTypes.setBackgroundDrawable(gd3);
                break;

            case R.id.imageViewTodate:
                makeTodate();
                break;
            case R.id.imageViewFromodate:
                makeFromdate();
                break;
            case R.id.btn_search:
                if (transaction_type.equals("5"))
                {
                    Snackbar snackbar = Snackbar
                            .make(rootview, "Select any type of transaction", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }
                else
                getTransaction();
                break;
            case R.id.img_drawer:
                drawer.openDrawer(navigationView);
                break;
        }

    }

    private void makeFromdate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_fromdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" +  year);

                    }
                }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void makeTodate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        tv_todate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" +  year);

                    }
                }, mYear, mMonth, mDay);
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void getTransaction
            () {

        final ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        progressDialog.setMessage("loading");
        progressDialog.show();
        String  id= AppController.getString(getApplicationContext(), "customer_id");
        toDate=tv_todate.getText().toString();
        fromDate=tv_fromdate.getText().toString();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<HomeResponse> call = apiService.SearchTransactionList(id,transaction_type,fromDate,toDate,ac_id);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, retrofit2.Response<HomeResponse> response) {

                progressDialog.hide();
                lisearch.setVisibility(View.GONE);
                recyclerViewTransactions.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    AllTransactionsList = response.body().getTransactionDetails();


                    recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    recyclerViewTransactions.addItemDecoration(new SimpleDividerItemDecoration(SearchActivity.this));
                    recyclerViewTransactions.setAdapter(new AllTransactionsAdapter(AllTransactionsList, R.layout.item_trannsactions, SearchActivity.this));
                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(rootview, "No transactions", Snackbar.LENGTH_LONG);
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();
                }


            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                // Log error here since request failed
                //progressDialog.dismiss();

            }
        });
    }


    private void setupClickListener() {
        recyclerViewTransactions.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String getAccountClosingBalance = AllTransactionsList.get(position).getAccountClosingBalance();
                String getTransactionAmount = AllTransactionsList.get(position).getTransactionAmount();
                String getTransactionId = AllTransactionsList.get(position).getTransactionId();
                String getAccountNo = AllTransactionsList.get(position).getAccountNo();
                String getTransactionDescription = AllTransactionsList.get(position).getTransactionDescription();
                String dateTime=AllTransactionsList.get(position).getTransactionDate();

                Intent intent=new Intent(SearchActivity.this,TransactionDetails.class);
                intent.putExtra("getAccountClosingBalance",getAccountClosingBalance);
                intent.putExtra("getTransactionAmount",getTransactionAmount);
                intent.putExtra("getTransactionId",getTransactionId);
                intent.putExtra("getAccountNo",getAccountNo);
                intent.putExtra("getTransactionDescription",getTransactionDescription);
                intent.putExtra("dateTime",dateTime);
                startActivity(intent);



            }
        }));

    }

}
