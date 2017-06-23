package com.firebase.sample.demo.test.adapter;

/**
 * Created by Sushant.Patekar on 6/20/2017.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.firebase.sample.demo.test.R;
import com.firebase.sample.demo.test.model.Perosn;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends BaseAdapter {
    // private final filterList mData;/
    private List<Perosn> effectList;
    private ArrayList<Perosn> filterList;

    public MyAdapter(List<Perosn> map) {
        effectList = map;
        filterList = new ArrayList<Perosn>();
        filterList.addAll(effectList);
        //filterList=map;

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return effectList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        } else {
            result = convertView;
        }


        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(effectList.get(position).getFirstName());
        ((TextView) result.findViewById(android.R.id.text2)).setText(effectList.get(position).getLastName());

        return result;
    }

    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        effectList.clear();
        if (charText.length() == 0) {
            effectList.addAll(filterList);

        } else {
            for (Perosn personDetail : filterList) {
                if (charText.length() != 0 && personDetail.getFirstName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    effectList.add(personDetail);
                } else if (charText.length() != 0 && personDetail.getLastName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    effectList.add(personDetail);
                }
            }
        }
        notifyDataSetChanged();
    }
}