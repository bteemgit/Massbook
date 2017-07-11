package com.example.admin.mpassbook.modules.HomePage;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.admin.mpassbook.retrofit.model.CommentResponse;
import com.example.admin.mpassbook.retrofit.model.HomeResponse;
import com.example.admin.mpassbook.retrofit.model.StatementResponse;
import com.example.admin.mpassbook.retrofit.model.TransactionDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by admin on 5/25/2017.
 */
public class AllFragment extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerViewAllTransactions;
    private List<TransactionDetail> AllTransactionsList = new ArrayList<>();
    TextView tv_currentBalance, tv_lastupdated;
    Toolbar toolbar;
    TextView tv_acNo, tv_acType;
    //  TextView tv_acNoToolbar, tv_acTypetoolbar;
    Spinner spinnerAccount;
    private List<AccountDetail> listAc = new ArrayList<>();
    String ac_id = "";
    TextView textViewViewstatement, textViewEmailStatement;
    RecyclerView recyclerViewAccountType;
    AccountListAdapter accountListAdapter;
    private String mText;
    String manufactureSelectedAcoonutId;
    ProgressDialog progressDialog;
    Dialog mBottomSheetDialog;
    ApiInterface apiService;
    LinearLayout liBottomShhet, liacno;
    String getTransactionId;
    ImageView imgDropdown;

    WebView webView;
    AccountDetail manufactureListAcoonut;

    private View view;
    public AllFragment(String text, String accountId) {
        mText = text;
        manufactureSelectedAcoonutId = accountId;
    }


    public String getText() {
        return mText;
    }

    public AccountDetail getManufactureList() {
        return manufactureListAcoonut;
    }

    public void setManufactureSelectedAcoonutId(String selectedAcoonutId){
        manufactureSelectedAcoonutId = selectedAcoonutId;
        getTransaction(manufactureSelectedAcoonutId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_all, container, false);
            recyclerViewAllTransactions = (RecyclerView) view.findViewById(R.id.rv_transactions);
            tv_currentBalance = (TextView) view.findViewById(R.id.totalbalance);
            tv_lastupdated = (TextView) view.findViewById(R.id.updateddate);
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            webView = (WebView) view.findViewById(R.id.webview);
            imgDropdown = (ImageView) view.findViewById(R.id.drpdown);
            imgDropdown.setOnClickListener(this);
            tv_acType = (TextView) view.findViewById(R.id.actype);
            spinnerAccount = (Spinner) view.findViewById(R.id.spinnerAccount);
            progressDialog = new ProgressDialog(getActivity());
            apiService = ApiClient.getClient().create(ApiInterface.class);
            tv_acNo = (TextView) view.findViewById(R.id.acno);
            tv_acNo.setOnClickListener(this);
            textViewViewstatement = (TextView) view.findViewById(R.id.tv_viewsatement);
            textViewEmailStatement = (TextView) view.findViewById(R.id.tv_emailstatement);
            liBottomShhet = (LinearLayout) view.findViewById(R.id.litop);
            liacno = (LinearLayout) view.findViewById(R.id.liacno);
            liBottomShhet.setOnClickListener(this);
            liacno.setOnClickListener(this);
            textViewViewstatement.setOnClickListener(this);
            textViewEmailStatement.setOnClickListener(this);
            tv_acType.setOnClickListener(this);
            // getAccountList();
            setupClickListener();
            ArrayList<String> manufactureList = new ArrayList<String>();
            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, manufactureList);
            // Drop down layout style - list view with radio button
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner;
            spinnerAccount.setAdapter(mAdapter);



        } else {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void onPause() {
        super.onPause();

    }
    public void onResume() {
        super.onResume();


    }

    private void setupClickListener() {
        recyclerViewAllTransactions.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String getAccountClosingBalance = AllTransactionsList.get(position).getAccountClosingBalance();
                String getTransactionAmount = AllTransactionsList.get(position).getTransactionAmount();
                getTransactionId = AllTransactionsList.get(position).getTransactionId();
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
    private void updateEndlessRecyclerView() {
        //accountListAdapter = new AccountListAdapter(manufactureListAcoonut, R.layout.item_account, getContext());
        recyclerViewAccountType.setAdapter(accountListAdapter);
        // progressDialog.dismiss();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_viewsatement:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Do you need the printout of transactions?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printout();
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
                break;
            case R.id.tv_emailstatement:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(false);
                builder1.setMessage("Do you need to sent the printout of transactions?");
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailStatement();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user select "No", just cancel this dialog and continue with app
                        dialog.cancel();
                    }
                });
                AlertDialog alert1 = builder1.create();
                alert1.show();
                break;
            case R.id.litop:
                showbootom();
                break;
            case R.id.acno:
                showbootom();
                break;
            case R.id.liacno:
                showbootom();
                break;
            case R.id.drpdown:
                //showbootom();
                break;

        }
    }


    private void showbootom() {
        final LayoutInflater li = LayoutInflater.from(getActivity());
        final View view = li.inflate(R.layout.bottomsheet, null);
        mBottomSheetDialog = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        // mBottomSheetDialog.setCancelable(true);
        recyclerViewAccountType = (RecyclerView) mBottomSheetDialog.findViewById(R.id.recyclerViewAccount);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewAccountType.setLayoutManager(layoutManager);
        recyclerViewAccountType.setHasFixedSize(true);
        updateEndlessRecyclerView();

        recyclerViewAccountType.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                recyclerViewAccountType.setVisibility(View.GONE);
                getTransaction(ac_id);
                mBottomSheetDialog.dismiss();

            }
        }));
        // mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);


        mBottomSheetDialog.show();
    }

    public void getTransaction
            (String acId) {


        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<HomeResponse> call = apiService.TransactionList(id, acId, mText);
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, retrofit2.Response<HomeResponse> response) {
                progressDialog.hide();
                AllTransactionsList.clear();
                if (response.isSuccessful()) {
                    AllTransactionsList = response.body().getTransactionDetails();
                    tv_currentBalance.setText(response.body().getBasicAccountDetails().getCurrentBalance());
                    tv_lastupdated.setText("Last Updation: " + response.body().getBasicAccountDetails().getLastUpdated() + "");
                    recyclerViewAllTransactions.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerViewAllTransactions.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                    recyclerViewAllTransactions.setAdapter(new AllTransactionsAdapter(AllTransactionsList, R.layout.item_trannsactions, getActivity()));

                }

            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                // Log error here since request faile
                progressDialog.dismiss();

            }
        });
    }


    private boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


    public void SentEmail() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CommentResponse> call = apiService.CommentTransaction(id, ac_id, getTransactionId);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, retrofit2.Response<CommentResponse> response) {

                progressDialog.hide();

                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Sent to your email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                // Log error here since request failed
                //progressDialog.dismiss();

            }
        });
    }

    public void printout() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<StatementResponse> call = apiService.ViewStatement(id, ac_id, mText);
        call.enqueue(new Callback<StatementResponse>() {
            @Override
            public void onResponse(Call<StatementResponse> call, retrofit2.Response<StatementResponse> response) {

                progressDialog.hide();

                if (response.isSuccessful()) {
                    //Toast.makeText(getActivity(), "Sent to your email", Toast.LENGTH_SHORT).show();
                    String url = response.body().getStatementUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);

                } else {
                    Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<StatementResponse> call, Throwable t) {
                // Log error here since request failed
                //progressDialog.dismiss();

            }
        });
    }


    public void emailStatement() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage("loading");
        progressDialog.show();
        String id = AppController.getString(getActivity(), "customer_id");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CommentResponse> call = apiService.EmailStatement(id, ac_id, mText);
        call.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, retrofit2.Response<CommentResponse> response) {

                progressDialog.hide();

                if (response.isSuccessful()) {
                    String statusMessage = response.body().getStatus();
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "server error", Toast.LENGTH_SHORT).show();

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
