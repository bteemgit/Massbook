package com.example.admin.mpassbook.modules.HomePage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.mpassbook.R;
import com.example.admin.mpassbook.retrofit.model.TransactionDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/27/2017.
 */
public class AllTransactionsAdapter extends RecyclerView.Adapter<AllTransactionsAdapter.ViewHolder> {

    private List<TransactionDetail> transList = new ArrayList<>();
    private int rowLayout;
    private Context context;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Trans_date;
        TextView tv_Trans_amount;
        TextView tv_Trans_description;


        public ViewHolder(View v) {
            super(v);

            tv_Trans_date = (TextView) v.findViewById(R.id.tv_TransactionDate);
            tv_Trans_amount = (TextView) v.findViewById(R.id.tv_TransactionAmount);
            tv_Trans_description = (TextView) v.findViewById(R.id.tv_TransactionDescr);


        }
    }

    public AllTransactionsAdapter(List<TransactionDetail> transactionDetails, int rowLayout, Context context) {
        this.transList = transactionDetails;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new AllTransactionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // For setting the material Textdrawable

        holder.tv_Trans_date.setText(transList.get(position).getTransactionDate().substring(0,10));
        if (transList.get(position).getTypeTransaction().equals("Credit")) {
            holder.tv_Trans_amount.setTextColor(context.getResources().getColor(R.color.green));
            holder.tv_Trans_amount.setText("Rs." + transList.get(position).getTransactionAmount() + "");
        }
        if (transList.get(position).getTypeTransaction().equals("Debit")) {
            holder.tv_Trans_amount.setText("Rs." + transList.get(position).getTransactionAmount() + "");
            holder.tv_Trans_amount.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.tv_Trans_description.setText(transList.get(position).getTransactionDescription());


    }

    @Override
    public int getItemCount() {
        if (transList != null) {
            return transList.size();
        }

        return 0;
    }
}