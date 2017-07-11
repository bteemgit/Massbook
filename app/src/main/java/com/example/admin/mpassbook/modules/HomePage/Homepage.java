package com.example.admin.mpassbook.modules.HomePage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mpassbook.LoginActivity;
import com.example.admin.mpassbook.R;
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
public class Homepage extends BaseActivity
        implements  NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imgDrawer;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView imageViewCustomer;
    TextView tvName;
    private List<AccountDetail> listAc = new ArrayList<>();
    public TextView toolbartext;
    public Toolbar mToolbar;
    TextView tv_acNo, tv_acType;
    Spinner spinnerAccount;
    String ac_id;
    AllFragment allFragment, creditFragment, debitFragment;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbartext=(TextView)findViewById(R.id.actype);
        getAccountList(viewPager);
// get Action Bar
        this.getSupportActionBar();
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
        imageViewCustomer = (ImageView) headerLayout.findViewById(R.id.imageViewCustomer);
        tvName = (TextView) headerLayout.findViewById(R.id.tvName);
        String name = AppController.getString(getApplicationContext(), "customer_name");
        String image = AppController.getString(getApplicationContext(), "customer_image");
        Log.d("name", name + image);
        Picasso.with(getApplicationContext()).load(image).resize(dp2px(25), 0).transform(new CircleTransform()).into(imageViewCustomer);
        tvName.setText(name);
        imgDrawer = (ImageView) findViewById(R.id.img_drawer);
        imgDrawer.setOnClickListener(this);
        //toolbar items
        tv_acType = (TextView) findViewById(R.id.actype);
        spinnerAccount = (Spinner) findViewById(R.id.spinnerAccount);
        tv_acNo = (TextView) findViewById(R.id.acno);

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
                    Intent intent1 = new Intent(Homepage.this, LoginActivity.class);
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
            // super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_drawer:
                drawer.openDrawer(navigationView);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getAccountList(final ViewPager viewPager) {
        final ProgressDialog progressDialog = new ProgressDialog(Homepage.this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getApplicationContext(), "customer_id");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<AccountResponse> call = apiService.AccountList(id);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, retrofit2.Response<AccountResponse> response) {

                progressDialog.dismiss();
                listAc = response.body().getAccountDetails();

                allFragment = new AllFragment("", listAc.get(0).getAccountId());
                creditFragment = new AllFragment("1", listAc.get(0).getAccountId());
                debitFragment = new AllFragment("2", listAc.get(0).getAccountId());
                setupViewPager(viewPager, listAc);
                //    updateEndlessRecyclerView();
                ArrayList<String> manufactureList = new ArrayList<String>();

                for (int i = 0; i < listAc.size(); i++) {
                    manufactureList.add(listAc.get(i).getAccountNo());
                }
                // Creating adapter for spinner service
                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(Homepage.this, android.R.layout.simple_spinner_item, manufactureList);
                // Drop down layout style - list view with radio button
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner;
                spinnerAccount.setAdapter(mAdapter);
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
                        ac_id = listAc.get(position).getAccountId();

                        allFragment.setManufactureSelectedAcoonutId(ac_id);
                        creditFragment.setManufactureSelectedAcoonutId(ac_id);
                        debitFragment.setManufactureSelectedAcoonutId(ac_id);




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



    private void setupViewPager(ViewPager viewPager, List<AccountDetail> listAc) {
        Homepage.ViewPagerAdapter adapter = new Homepage.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(allFragment, "All");
        adapter.addFrag(creditFragment, "Credit");
        adapter.addFrag(debitFragment, "Debit");
        viewPager.setAdapter(adapter);
    }

}
