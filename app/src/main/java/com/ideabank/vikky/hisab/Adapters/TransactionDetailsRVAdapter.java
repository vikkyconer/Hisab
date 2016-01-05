package com.ideabank.vikky.hisab.Adapters;

import android.app.Service;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ideabank.vikky.hisab.R;
import com.ideabank.vikky.hisab.Models.TransactionDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikky on 7/16/15.
 */
public class TransactionDetailsRVAdapter extends RecyclerView.Adapter<TransactionDetailsRVAdapter.TransactionsViewHolder> {

    List<TransactionDetails> transactionDetailsList;
    Context context;
    ArrayList<String> forWhom;

    public TransactionDetailsRVAdapter(List<TransactionDetails> transactionDetailsList, Context context, ArrayList<String> forWhom) {
        this.transactionDetailsList = transactionDetailsList;
        this.context = context;
        this.forWhom = forWhom;
    }

    @Override
    public TransactionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction_details, parent, false);
        TransactionsViewHolder pvh = new TransactionsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TransactionsViewHolder holder, int i) {
        if (transactionDetailsList.get(i).getAmount() > 9999)
            holder.amount.setTextSize(20);
        holder.amount.setText(String.valueOf(transactionDetailsList.get(i).getAmount()));
        holder.whoPaid.setText(transactionDetailsList.get(i).getWhoPaid());
//        Log.i("ForWhomList", forWhom.get(0));
        showForWhomPaid(forWhom, holder);
//        holder.forWhom.setText(transactionDetailsList.get(i).getForWhom());
        holder.description.setText(transactionDetailsList.get(i).getDescription());
    }

    private void showForWhomPaid(ArrayList<String> forWhom, TransactionsViewHolder holder) {
        holder.forWhomPaidContainer.removeAllViews();
        for (int i = 0; i < forWhom.size(); i++) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.names_for_whom_paid, null);
            if (i != forWhom.size() - 1) {
                (view.findViewById(R.id.comma)).setVisibility(View.VISIBLE);
            }
            ((TextView) view.findViewById(R.id.for_whom)).setText(forWhom.get(i));
            holder.forWhomPaidContainer.addView(view);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return transactionDetailsList.size();
    }


    public static class TransactionsViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        CardView cv;
        TextView amount;
        TextView whoPaid;
        TextView forWhom;
        LinearLayout forWhomPaidContainer;
        TextView description;

        TransactionsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            amount = (TextView) itemView.findViewById(R.id.amount);
            whoPaid = (TextView) itemView.findViewById(R.id.who_paid);
            forWhom = (TextView) itemView.findViewById(R.id.for_whom);
            description = (TextView) itemView.findViewById(R.id.description);
            forWhomPaidContainer = (LinearLayout) itemView.findViewById(R.id.for_whom_paid_container);

            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
//            Toast.makeText(context, place.getPlaceName(), Toast.LENGTH_SHORT).show();
            return true;
        }
    }

}
