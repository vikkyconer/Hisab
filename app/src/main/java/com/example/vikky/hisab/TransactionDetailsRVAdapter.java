package com.example.vikky.hisab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vikky on 7/16/15.
 */
public class TransactionDetailsRVAdapter extends RecyclerView.Adapter<TransactionDetailsRVAdapter.TransactionsViewHolder> {

    List<TransactionDetails> transactionDetailsList;
    Context context;

    public TransactionDetailsRVAdapter(List<TransactionDetails> transactionDetailsList, Context context) {
        this.transactionDetailsList = transactionDetailsList;
        this.context = context;
    }

    @Override
    public TransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction_details, parent, false);
        TransactionsViewHolder pvh = new TransactionsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TransactionsViewHolder holder, int i) {
        holder.amount.setText(transactionDetailsList.get(i).getAmount());
        holder.whoPaid.setText(transactionDetailsList.get(i).getWhoPaid());
        holder.forWhom.setText(transactionDetailsList.get(i).getForWhom());
        holder.description.setText(transactionDetailsList.get(i).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return transactionDetailsList.size();
    }


    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView amount;
        TextView whoPaid;
        TextView forWhom;
        TextView description;

        TransactionsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            amount = (TextView) itemView.findViewById(R.id.amount);
            whoPaid = (TextView) itemView.findViewById(R.id.who_paid);
            forWhom = (TextView) itemView.findViewById(R.id.for_whom);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

}
