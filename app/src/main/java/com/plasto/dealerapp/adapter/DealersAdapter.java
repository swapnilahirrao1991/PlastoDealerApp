package com.plasto.dealerapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.activity.DealerDetailsActivity;
import com.plasto.dealerapp.activity.EditDealerActivity;
import com.plasto.dealerapp.retrofit.searchDealer.response.DealersItem;
import com.plasto.dealerapp.utils.GPSTracker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by vrs on 3/9/15.
 */
public class DealersAdapter extends RecyclerView.Adapter<DealersAdapter.ViewHolder> {
    List<DealersItem> list = new ArrayList<>();
    List<DealersItem> searchList;
    Context context;

    public DealersAdapter(Context context, List<DealersItem> list) {
        this.list = list;
        this.context = context;
        this.searchList = new ArrayList<>();
        this.searchList.addAll(list);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public DealersItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dealer_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.list = getItem(position);
        holder.mLinearMobNo.setVisibility(View.GONE);
        holder.cardtitle.setText(list.get(position).getName());
        holder.txtCity.setText(list.get(position).getAddress());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  GPSTracker gpsTracker = new GPSTracker(context);
                // getLoc(gpsTracker);
                //Toast.makeText(context, "" + list.get(position).toString(), Toast.LENGTH_SHORT).show();
                // holder.mLinearMobNo.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                String strData = gson.toJson(list.get(position));
                showConfirmDialog(list.get(position).getName(), strData);
            }
        });
        holder.btnAttendance
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gson gson = new Gson();
                        String strData = gson.toJson(list.get(position));
                        context.startActivity(new Intent(context, EditDealerActivity.class)
                                .putExtra("DEALER_DATA", strData));

                    }
                });

    }

    private void showConfirmDialog(String name, final String strData) {
       /* AlertDialog ad = new AlertDialog.Builder(context).create();
        ad.setCancelable(false); // This blocks the 'BACK' button
        //  ad.setMessage(Html.fromHtml("Confrim to set attendance for dealer : \n" + "<b>" + name + "<b>" + "."));
        ad.se("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, DealerDetailsActivity.class).putExtra("DATA", strData));
                dialog.dismiss();
            }
        });
        ad.show();*/
        // dialog.dismiss();
        // context.startActivity(new Intent(context, DealerDetailsActivity.class).putExtra("DATA", strData));
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setCancelable(true);
        // set dialog message
        alertDialogBuilder
                .setMessage(Html.fromHtml("Confrim to set attendance for dealer:<br>" + "<b>" + name + "<b>" + "."))
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        context.startActivity(new Intent(context,
                                DealerDetailsActivity.class)
                                .putExtra("DATA", strData));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do something if you need
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void getLoc(GPSTracker gpsTracker) {
        if (gpsTracker.canGetLocation()) {
            String stringLatitude = String.valueOf(gpsTracker.getLatitude());
            String stringLongitude = String.valueOf(gpsTracker.getLongitude());
            Log.d("GPS", "LAT : " + stringLatitude + " LON:" + stringLongitude);
            Toast.makeText(context, "" + "LAT : " + stringLatitude + "\nLON:" + stringLongitude, Toast.LENGTH_SHORT).show();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    public void reset(List<DealersItem> l) {
        list.addAll(l);
        notifyDataSetChanged();
    }


    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(searchList);
        } else {
            for (DealersItem s : searchList) {
                if (s.getMobile().toLowerCase(Locale.getDefault()).contains(charText) || s.getAddress().toLowerCase(Locale.getDefault()).contains(charText) || s.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardimage;
        TextView cardtitle;
        TextView txtCity;
        DealersItem list;
        ImageButton btnAttendance;
        ImageButton btnEdit;
        LinearLayout mLinearMobNo;
        RecyclerView recyclerView;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardimage = (ImageView) itemView.findViewById(R.id.cardimage);
            cardtitle = (TextView) itemView.findViewById(R.id.cardtitle);
            txtCity = (TextView) itemView.findViewById(R.id.textView_city);
            btnAttendance = (ImageButton) itemView.findViewById(R.id.button_attendance);
            mLinearMobNo = (LinearLayout) itemView.findViewById(R.id.layout_visit);
            btnEdit = (ImageButton) itemView.findViewById(R.id.edit);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}

 
