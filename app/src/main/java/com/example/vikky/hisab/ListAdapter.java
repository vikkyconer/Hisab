package com.example.vikky.hisab;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vikky on 6/29/15.
 */
public class ListAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private String colorName;
    private String colorPosition;
    private static LayoutInflater inflater;
    private ArrayList<ColorList> list;
    private Boolean status;
    ColorList colorList;

    public ListAdapter(Context context, String colorName) {
        this.context = context;
        this.colorName = colorName;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();

            viewHolder.colorNameText = (TextView) view.findViewById(R.id.color_name);
            viewHolder.colorPosition = (TextView) view.findViewById(R.id.color_item_number);
            viewHolder.colorStatus = (ImageView) view.findViewById(R.id.color_status);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) view.getTag();
        colorList = list.get(i);
        viewHolder.colorNameText.setText(colorList.getColorName());
        viewHolder.colorPosition.setText(i + 1 + "");
        if (colorList.getStatus() == true) {
            viewHolder.colorStatus.setImageResource(R.drawable.check);
        } else {
            viewHolder.colorStatus.setImageResource(R.drawable.uncheck);
        }
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        colorList.setStatus(true);

    }

    class ViewHolder {
        TextView colorNameText;
        TextView colorPosition;
        ImageView colorStatus;
    }
}
