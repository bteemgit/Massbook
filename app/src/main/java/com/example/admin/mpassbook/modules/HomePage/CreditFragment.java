package com.example.admin.mpassbook.modules.HomePage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mpassbook.R;
import com.example.admin.mpassbook.TransactionDetails;
import com.example.admin.mpassbook.global.AppController;
import com.example.admin.mpassbook.recyclerview.RecyclerItemClickListener;
import com.example.admin.mpassbook.recyclerview.SimpleDividerItemDecoration;
import com.example.admin.mpassbook.retrofit.ApiClient;
import com.example.admin.mpassbook.retrofit.ApiInterface;
import com.example.admin.mpassbook.retrofit.model.AccountDetail;
import com.example.admin.mpassbook.retrofit.model.AccountResponse;
import com.example.admin.mpassbook.retrofit.model.HomeResponse;
import com.example.admin.mpassbook.retrofit.model.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.admin.mpassbook.global.BaseActivity.isConnectingToInternet;

/**
 * Created by admin on 5/25/2017.
 */
public class CreditFragment extends Fragment {
    private RecyclerView recyclerViewAllTransactions;
    private List<TransactionDetail> AllTransactionsList = new ArrayList<>();
    TextView tv_currentBalance, tv_lastupdated;
    Toolbar toolbar;
    TextView tv_acNo, tv_acType;
    Spinner spinnerAccount;
    private List<AccountDetail> listAc = new ArrayList<>();
    private String ac_id_credit = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        recyclerViewAllTransactions = (RecyclerView) view.findViewById(R.id.rv_transactions);
        tv_currentBalance = (TextView) view.findViewById(R.id.totalbalance);
        tv_lastupdated = (TextView) view.findViewById(R.id.updateddate);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }


        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tv_acType = (TextView) toolbar.findViewById(R.id.actype);
        spinnerAccount = (Spinner) toolbar.findViewById(R.id.spinnerAccount);
        tv_acNo = (TextView) toolbar.findViewById(R.id.acno);
        getAccountList();

        setupClickListener();
        //INSERT CUSTOM CODE HERE
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Boolean a = isConnectingToInternet(getActivity());
        if (a) {


        }


    }

    private void getAccountList() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");
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
                ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, manufactureList);
                // Drop down layout style - list view with radio button
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner;
                spinnerAccount.setAdapter(mAdapter);

                tv_acNo.setText(listAc.get(0).getAccountNo());
                tv_acType.setText(listAc.get(0).getTypeAccount());
                ac_id_credit = listAc.get(0).getAccountId();
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
                getTransaction(ac_id_credit);

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
                        ac_id_credit = listAc.get(position).getAccountId();


                        getTransaction(ac_id_credit);

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

    private void setupClickListener() {
        recyclerViewAllTransactions.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String getAccountClosingBalance = AllTransactionsList.get(position).getAccountClosingBalance();
                String getTransactionAmount = AllTransactionsList.get(position).getTransactionAmount();
                String getTransactionId = AllTransactionsList.get(position).getTransactionId();
                String getAccountNo = AllTransactionsList.get(position).getAccountNo();
                String getTransactionDescription = AllTransactionsList.get(position).getTransactionDescription();
                String dateTime = AllTransactionsList.get(position).getTransactionDate();
                String appId = AllTransactionsList.get(position).getAppTransactionId();

                Intent intent = new Intent(getActivity(), TransactionDetails.class);
                intent.putExtra("getAccountClosingBalance", getAccountClosingBalance);
                intent.putExtra("getTransactionAmount", getTransactionAmount);
                intent.putExtra("getTransactionId", getTransactionId);
                intent.putExtra("getAccountNo", getAccountNo);
                intent.putExtra("getTransactionDescription", getTransactionDescription);
                intent.putExtra("dateTime", dateTime);
                intent.putExtra("appId", appId);
                startActivity(intent);


            }
        }));

    }

    public void getTransaction
            (String acId) {
        AllTransactionsList.clear();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());


        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<HomeResponse> call = apiService.TransactionList(id, acId, "1");
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, retrofit2.Response<HomeResponse> response) {
                progressDialog.hide();
                if (response.isSuccessful()) {

                    AllTransactionsList = response.body().getTransactionDetails();

                    tv_currentBalance.setText(response.body().getBasicAccountDetails().getCurrentBalance());
                    tv_lastupdated.setText("Last Updation: " + response.body().getBasicAccountDetails().getLastUpdated() + "");
                    recyclerViewAllTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewAllTransactions.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                    recyclerViewAllTransactions.setAdapter
                            (new CreditTransactionsAdapter(AllTransactionsList, R.layout.item_trannsactions, getActivity()));

                }

            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                // Log error here since request failed
                //progressDialog.dismiss();

            }
        });
    }
}
