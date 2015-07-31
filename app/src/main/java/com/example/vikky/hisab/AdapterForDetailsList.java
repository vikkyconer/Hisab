package com.example.vikky.hisab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vikky on 7/14/15.
 */
public class AdapterForDetailsList extends BaseAdapter {
    Context context;
    ArrayList<ExpenditureModel> expenditureModels;
    public static LayoutInflater layoutInflater = null;

    public AdapterForDetailsList(Context context, ArrayList<ExpenditureModel> expenditureModels) {
        this.context = context;
        this.expenditureModels = expenditureModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ComputeFragment.count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;

        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.transaction_details_list_item, null);
            holder = new Holder();
            holder.whoHasToPay = (TextView) view.findViewById(R.id.who_has_to_pay);
            holder.amount = (TextView) view.findViewById(R.id.amount);
            holder.whomToPay = (TextView) view.findViewById(R.id.whom_to_pay);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.whoHasToPay.setText(expenditureModels.get(position).getWhoHasToPay());
        holder.amount.setText(String.valueOf(expenditureModels.get(position).getAmount()));
        holder.whomToPay.setText(expenditureModels.get(position).getWhomToPay());
        return view;
    }

    public class Holder {
        public TextView whoHasToPay, whomToPay, amount;


    }
}
