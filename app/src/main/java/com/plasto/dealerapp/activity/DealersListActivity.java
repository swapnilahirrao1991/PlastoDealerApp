package com.plasto.dealerapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.adapter.DealersAdapter;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.searchDealer.request.SearchRequest;
import com.plasto.dealerapp.retrofit.searchDealer.response.DealersItem;
import com.plasto.dealerapp.retrofit.searchDealer.response.SearchDealerResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.GPSTracker;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealersListActivity extends AppCompatActivity {
    DealersAdapter adapter;
    List<DealersItem> dealersList;
    private ProgressDialog pb;
    RecyclerView recyclerView;
    private LoginResponse userDetails;
    SharedPrefUtils prefUtils = new SharedPrefUtils();
    private ApiInterface anInterface;
    String selectedState;
    String selectedCity;
    String tasilId, areaID;
    public GPSTracker gps = new GPSTracker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealers_list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        userDetails = new Gson().fromJson(prefUtils.getFromPrefs(this, Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);
        anInterface = ApiClient.getClient().create(ApiInterface.class);
        pb = new ProgressDialog(this, "Loading...", R.color.colorPrimary);
        pb.setCancelable(false);

        if (getIntent().getStringExtra("SID") != null && getIntent().getStringExtra("CID") != null &&
                getIntent().getStringExtra("TID") != null && getIntent().getStringExtra("AID") != null) {
            prefUtils.saveToPrefs(this, SharedPrefUtils.KEY_SID, getIntent().getStringExtra("SID"));
            prefUtils.saveToPrefs(this, SharedPrefUtils.KEY_CID, getIntent().getStringExtra("CID"));
            prefUtils.saveToPrefs(this, SharedPrefUtils.KEY_TID, getIntent().getStringExtra("TID"));
            prefUtils.saveToPrefs(this, SharedPrefUtils.KEY_AID, getIntent().getStringExtra("AID"));
        }
       /* selectedState = getIntent().getStringExtra("SID");
        selectedCity = getIntent().getStringExtra("CID");
        tasilId = getIntent().getStringExtra("TID");
        areaID = getIntent().getStringExtra("AID");*/

        selectedState = prefUtils.getFromPrefs(this, SharedPrefUtils.KEY_SID, "");
        selectedCity = prefUtils.getFromPrefs(this, SharedPrefUtils.KEY_CID, "");
        tasilId = prefUtils.getFromPrefs(this, SharedPrefUtils.KEY_TID, "");
        areaID = prefUtils.getFromPrefs(this, SharedPrefUtils.KEY_AID, "");

        //recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        initializeData();
        adapter = new DealersAdapter(this, dealersList);
        //set adapter
        recyclerView.setAdapter(adapter);
        searchRequest(selectedState, selectedCity, tasilId, areaID);
    }


    private void initializeData() {
        dealersList = new ArrayList<>();

    }


    private void searchRequest(String selectedState, String selectedCity, String tasilId, String areaId) {
        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<SearchDealerResponse> tashilList = anInterface.searchDealersList(
                new SearchRequest(
                        CommonUtils.getIMEI(DealersListActivity.this)
                        , userDetails.getData().getId()
                        , userDetails.getData().getUserToken()
                        , selectedState//selectedState
                        , selectedCity//selectedCity
                        , tasilId
                ));
        tashilList.enqueue(new Callback<SearchDealerResponse>() {
            @Override
            public void onResponse(Call<SearchDealerResponse> call, Response<SearchDealerResponse> response) {
                if (response.isSuccessful() && response.body().isResponse()) {
                    //  ArrayList<DealersItem> dealersItems = new ArrayList<DealersItem>(response.body().getData().getDealers());
                    dealersList = response.body().getData().getDealers();
                    setAdapter(dealersList);

                } else {
                    Toast.makeText(DealersListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<SearchDealerResponse> call, Throwable t) {
                pb.dismiss();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filter("");
                    // adapter.reset(dealersList);
                } else if (!newText.isEmpty()) {
                    adapter.filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    private void setAdapter(List<DealersItem> dealersList) {
        if (dealersList.size() == 0) {
            CommonUtils.showSnackBar(this, "No dealer Found");
        } else {
            Collections.sort(dealersList, new Comparator<DealersItem>() {
                @Override
                public int compare(DealersItem lhs, DealersItem rhs) {
                    return lhs.getName().compareToIgnoreCase(rhs.getName());
                }
            });

            adapter = new DealersAdapter(this, dealersList);
            //set adapter
            recyclerView.setAdapter(adapter);
        }

    }
}
