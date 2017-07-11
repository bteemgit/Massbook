package com.example.admin.mpassbook.modules.HomePage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mpassbook.R;
import com.example.admin.mpassbook.retrofit.model.AccountDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/31/2017.
 */

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {

    private List<AccountDetail> listAc = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Trans_date;




        public ViewHolder(View v) {
            super(v);

            tv_Trans_date = (TextView) v.findViewById(R.id.textAccountType);


        }
    }

    public AccountListAdapter( List<AccountDetail> listAc, int rowLayout, Context context) {
        this.listAc = listAc;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public AccountListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AccountListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountListAdapter.ViewHolder holder, int position) {
        // For setting the material Textdrawable


        //if (transList.get(position).getTypeTransaction().equals("Credit")) {
        holder.tv_Trans_date.setText(listAc.get(position).getAccountNo());
        //  }


/*
        else {
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(0, 0);
            holder.liitem.setLayoutParams(lp);
            holder.liitem.setVisibility(View.GONE);

        }*/
    }

    @Override
    public int getItemCount() {
        if (listAc != null) {
            return listAc.size();
        }

        return 0;
    }
}
