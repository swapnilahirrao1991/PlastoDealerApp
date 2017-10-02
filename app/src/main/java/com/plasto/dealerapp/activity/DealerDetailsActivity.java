package com.plasto.dealerapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.searchDealer.response.DealersItem;
import com.plasto.dealerapp.retrofit.submitVisit.request.SubmitVisitRequest;
import com.plasto.dealerapp.retrofit.submitVisit.request.SubmitVisitResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.GPSTracker;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.location.LocationManager.NETWORK_PROVIDER;

public class DealerDetailsActivity extends AppCompatActivity {
    private DealersItem dealersItem;
    private Context context = DealerDetailsActivity.this;
    TextView txtName, txtAddress, txtMobNo;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    String visitMobile = "";
    private ApiInterface anInterface;
    private ProgressDialog pb;
    private LoginResponse userDetails;
    Gson gson = new Gson();
    SharedPrefUtils prefUtils;
    AppCompatEditText edtDesc;

    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtDesc = (AppCompatEditText) findViewById(R.id.edit_discription);
        gpsTracker = new GPSTracker(context);
        getLocationPermission();
        pb = new ProgressDialog(DealerDetailsActivity.this, "Loading...", R.color.colorPrimary);
        prefUtils = new SharedPrefUtils();

        anInterface = ApiClient.getClient().create(ApiInterface.class);
        userDetails = gson.fromJson(prefUtils.getFromPrefs(this, Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);

        Intent i = getIntent();
        String DATA = i.getStringExtra("DATA");
        dealersItem = new Gson().fromJson(DATA, DealersItem.class);
        //  Toast.makeText(context, "In DealaerDetails:" + dealersItem.getMobile(), Toast.LENGTH_SHORT).show();
        init();
        setData(dealersItem);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visitMobile = (String) listView.getItemAtPosition(position);

            }
        });
        FindLocation();

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoc(gpsTracker);
                if (gpsTracker.canGetLocation()) {
                    String stringLatitude = String.valueOf(gpsTracker.getLatitude());
                    String stringLongitude = String.valueOf(gpsTracker.getLongitude());
                    Log.d("GPS", "LAT : " + stringLatitude + " LON:" + stringLongitude);
                    //   Toast.makeText(context, "" + "LAT : " + stringLatitude + "\nLON:" + stringLongitude, Toast.LENGTH_SHORT).show();


                    if (CommonUtils.isInternetAvailable(DealerDetailsActivity.this)) {
                        pb.show();
                        Call<SubmitVisitResponse> submitVisit = anInterface
                                .submitVisit(new SubmitVisitRequest(visitMobile
                                        , CommonUtils.getIMEI(DealerDetailsActivity.this)
                                        , userDetails.getData().getId()
                                        , userDetails.getData().getUserToken()
                                        , stringLatitude
                                        , stringLongitude
                                        , dealersItem.getDealrId()
                                        , edtDesc.getText().toString().trim()));

                        submitVisit.enqueue(new Callback<SubmitVisitResponse>() {
                            @Override
                            public void onResponse(Call<SubmitVisitResponse> call, Response<SubmitVisitResponse> response) {
                                if (response.isSuccessful())
                                    Toast.makeText(DealerDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                pb.dismiss();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<SubmitVisitResponse> call, Throwable t) {
                                pb.dismiss();
                            }
                        });
                    }
                } else {
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gpsTracker.showSettingsAlert();
                    return;
                }
            }
        });

    }

    public void FindLocation() {
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                updateLocation(location);
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 0, 0, locationListener);

    }


    void updateLocation(Location location) {
        gpsTracker.onLocationChanged(location);
    }

    private void getLocationPermission() {
        Activity activity = this;
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED) {
            // Proceed with your code execution
            CommonUtils.showSnackBar(activity, "Granted");
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }


        } else {
            CommonUtils.showSnackBar(activity, " NOT Granted");

            // Uhhh I guess we have to ask for permission
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getLoc(GPSTracker gpsTracker) {
        if (gpsTracker.canGetLocation()) {
            String stringLatitude = String.valueOf(gpsTracker.getLatitude());
            String stringLongitude = String.valueOf(gpsTracker.getLongitude());
            Log.d("GPS", "LAT : " + stringLatitude + " LON:" + stringLongitude);
            //  Toast.makeText(context, "" + "LAT : " + stringLatitude + "\nLON:" + stringLongitude, Toast.LENGTH_SHORT).show();

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    private void setData(DealersItem dealersItem) {
        txtName.setText(dealersItem.getName());
        txtAddress.setText(dealersItem.getAddress());
        txtMobNo.setText(dealersItem.getMobile());
        if (!dealersItem.getMobile().equals("0") && !dealersItem.getMobile().equals("0")) {
            arrayList.add(dealersItem.getMobile());
        }
        if (!dealersItem.getOtherMobile().equals("") && !dealersItem.getOtherMobile().equals("0")) {
            arrayList.add(dealersItem.getOtherMobile());
        }
        if (!dealersItem.getOtherMobile2().equals("") && !dealersItem.getOtherMobile2().equals("0")) {
            arrayList.add(dealersItem.getOtherMobile2());
        }
        if (!dealersItem.getOtherMobile3().equals("") && !dealersItem.getOtherMobile3().equals("0")) {
            arrayList.add(dealersItem.getOtherMobile3());
        }
        if (!dealersItem.getOtherMobile4().equals("") && !dealersItem.getOtherMobile4().equals("0")) {
            arrayList.add(dealersItem.getOtherMobile4());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(adapter);

    }

    private void init() {
        txtName = (TextView) findViewById(R.id.textViewName);
        txtAddress = (TextView) findViewById(R.id.textViewAddress);
        txtMobNo = (TextView) findViewById(R.id.textViewMobileNo);
        listView = (ListView) findViewById(R.id.listView);
    }

}
