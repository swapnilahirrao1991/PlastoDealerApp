package com.plasto.dealerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.model.Model;

import java.util.List;

/**
 * Created by Pat-Win 10 on 22-01-2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<Model> mLIST;

    public CustomAdapter(Context applicationContext, List<Model> mLIST) {
        this.context = applicationContext;

        this.mLIST = mLIST;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mLIST.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        TextView names = (TextView) view.findViewById(R.id.textView);
        names.setText(mLIST.get(i).getName());
        return view;
    }
}
