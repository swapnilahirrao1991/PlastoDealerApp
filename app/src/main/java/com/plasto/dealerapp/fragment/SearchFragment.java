package com.plasto.dealerapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.activity.DealersListActivity;
import com.plasto.dealerapp.adapter.CustomAdapter;
import com.plasto.dealerapp.model.Model;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.cityList.response.CityListResponse;
import com.plasto.dealerapp.retrofit.cityList.response.request.CityListRequest;
import com.plasto.dealerapp.retrofit.getLoginData.response.AreaItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CityItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.GetLoginDataResponse;
import com.plasto.dealerapp.retrofit.getLoginData.response.StateItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.TashilItem;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.searchDealer.request.SearchRequest;
import com.plasto.dealerapp.retrofit.searchDealer.response.DealersItem;
import com.plasto.dealerapp.retrofit.searchDealer.response.SearchDealerResponse;
import com.plasto.dealerapp.retrofit.stateList.request.StateListRequest;
import com.plasto.dealerapp.retrofit.stateList.response.StateListResponse;
import com.plasto.dealerapp.retrofit.tashilList.request.TashilListRequest;
import com.plasto.dealerapp.retrofit.tashilList.request.response.TashilListResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    List<String> statesList = new ArrayList<String>();
    List<String> citiesList = new ArrayList<String>();
    List<String> tahsilsList = new ArrayList<String>();
    List<String> areaList = new ArrayList<String>();
    private ProgressDialog pb;
    SharedPrefUtils prefUtils;
    private ApiInterface anInterface;
    ArrayList<String> sValues = new ArrayList<>();
    ArrayList<String> tValues = new ArrayList<>();
    Spinner spnStates, spnDistrict, spnTahsil, spnArea;

    String selectedCity = "", selectedState = "", selectedTahsil = "";
    Button btnSearch;
    List<Model> mLIST_CITY = new ArrayList<>();
    List<Model> tLIST_CITY = new ArrayList<>();
    ArrayList<String> cValues = new ArrayList<>();
    FloatingActionButton fab;
    private LoginResponse userDetails;
    private GetLoginDataResponse listResponse;
    List<StateItem> sLIST_STATE = new ArrayList<>();
    List<CityItem> sLIST_CITY = new ArrayList<>();
    List<TashilItem> sLIST_TAHSIL = new ArrayList<>();
    List<AreaItem> sLIST_AREA = new ArrayList<>();
    List<String> sSTATESLIST = new ArrayList<String>();
    List<String> sCITIESLIST = new ArrayList<String>();
    List<String> sTAHSILSLIST = new ArrayList<String>();
    List<String> sAREALIST = new ArrayList<String>();
    ArrayAdapter<String> statesAdapter;
    ArrayAdapter<String> districtAdapter;
    ArrayAdapter<String> tahsilAdapter;
    ArrayAdapter<String> areaAdapter;
    Gson gson = new Gson();
    String ngp;
    TextView txtTahsilTitle, txtTitleArea;
    Realm realm;
    Button btnState, btnCity, btnTahsil, btnArea;
    String slectedStateId, slectedCityId, slectedTahsilId, slectedAreaId;
    int nagpurFlag = 1;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        prefUtils = new SharedPrefUtils();
        anInterface = ApiClient.getClient().create(ApiInterface.class);

        pb = new ProgressDialog(getContext(), "Loading...", R.color.colorPrimary);
        pb.setCancelable(false);
        userDetails = gson.fromJson(prefUtils.getFromPrefs(getActivity(), Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);
        ngp = userDetails.getData().getNgp() + "";
        listResponse = gson.fromJson(prefUtils.getFromPrefs(getActivity(), Constants.SP_KEY_USER_CITY_DETAILS_JSON, null), GetLoginDataResponse.class);

        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        // Spinner element
        spnStates = (Spinner) v.findViewById(R.id.spinner_state);
        spnDistrict = (Spinner) v.findViewById(R.id.spinner_district);
        spnTahsil = (Spinner) v.findViewById(R.id.spinner_tashil);
        spnArea = (Spinner) v.findViewById(R.id.spinner_area);
        btnSearch = (Button) v.findViewById(R.id.button_search);
        txtTahsilTitle = (TextView) v.findViewById(R.id.textViewTahsilTitle);
        txtTitleArea = (TextView) v.findViewById(R.id.titleArea);
        btnState = (Button) v.findViewById(R.id.btnState);
        btnCity = (Button) v.findViewById(R.id.btnCity);
        btnTahsil = (Button) v.findViewById(R.id.btnTahsil);
        btnArea = (Button) v.findViewById(R.id.btnArea);
        if (nagpurFlag == Integer.parseInt(ngp)) {
            btnArea.setVisibility(View.VISIBLE);
        }


        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slectedStateId = null;
                slectedCityId = null;
                slectedTahsilId = null;
                slectedAreaId = null;
                btnCity.setText("Select City");
                btnTahsil.setText("Select Tahsil");
                btnArea.setText("Select Area");
                /*Collections.sort(sSTATESLIST, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareToIgnoreCase(rhs);
                    }
                });*/
                statesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, sSTATESLIST);
                statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                showStateAlertListDialog(statesAdapter);
            }
        });

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slectedStateId != null) {
                    slectedCityId = null;
                    slectedTahsilId = null;
                    slectedAreaId = null;
                    btnTahsil.setText("Select Tahsil");
                    btnArea.setText("Select Area");

                    getAllCity(slectedStateId);
                   /* Collections.sort(sCITIESLIST, new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return lhs.compareToIgnoreCase(rhs);
                        }
                    });*/
                    districtAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, sCITIESLIST);
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    showCityAlertListDialog(districtAdapter);
                } else {
                    showToast("Select State");
                }

            }
        });


        btnTahsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slectedCityId != null) {
                    getAllTahsil(slectedCityId);
                  /*  Collections.sort(sTAHSILSLIST, new Comparator<String>() {
                        @Override
                        public int compare(String lhs, String rhs) {
                            return lhs.compareToIgnoreCase(rhs);
                        }
                    });*/
                    tahsilAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, sTAHSILSLIST);
                    tahsilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    showTahsilAlertListDialog(tahsilAdapter);
                } else {
                    showToast("Select City");
                }
            }

        });


        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slectedTahsilId != null) {
                    getAllArea("355");
                    areaAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, sAREALIST);
                    areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    showAreaAlertListDialog(areaAdapter);
                } else {
                    showToast("Select Tahsil");
                }
            }
        });

        getAllList();
        //   getStates();

        // Creating adapter for spinner
        statesAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sSTATESLIST);
        // Drop down layout style - list view with radio button
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStates.setAdapter(statesAdapter);
        //   sCITIESLIST.add(0, "Select City");
        //  sTAHSILSLIST.add(0, "Select Tahsil");
        //  districtAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sCITIESLIST);
        // Drop down layout style - list view with radio button
        //  districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  spnDistrict.setAdapter(districtAdapter);
        Log.d("TAG", "NGP:" + ngp);

        // if (Integer.parseInt(ngp) == 1) {
        //  tahsilAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, sTAHSILSLIST);
        // Drop down layout style - list view with radio button
        //  tahsilAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  spnTahsil.setAdapter(tahsilAdapter);
       /* } else {
            spnTahsil.setVisibility(GONE);
            txtTahsilTitle.setVisibility(GONE);
            selectedTahsil = "";
        }*/
        if (Integer.parseInt(ngp) == 0) {
            txtTitleArea.setVisibility(View.GONE);
            spnArea.setVisibility(View.GONE);
        }
        areaAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, areaList);
        // Drop down layout style - list view with radio button
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnArea.setAdapter(areaAdapter);

        setListeners();

        // if (CommonUtils.isInternetAvailable(getContext()))
        //   getstateList();
        return v;
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showStateAlertListDialog(final ArrayAdapter<String> arrayAdapter) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.ic_search);
        builderSingle.setTitle("Select One Name:-");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                btnState.setText(strName);
                slectedStateId = sLIST_STATE.get(which).getId();
            }
        });
        builderSingle.show();
    }

    private void showCityAlertListDialog(final ArrayAdapter<String> arrayAdapter) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.ic_search);
        builderSingle.setTitle("Select City:");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                btnCity.setText(strName);
                slectedCityId = sLIST_CITY.get(which).getCityId();
            }
        });
        builderSingle.show();
    }

    private void showTahsilAlertListDialog(final ArrayAdapter<String> arrayAdapter) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.ic_search);
        builderSingle.setTitle("Select Tahsil:");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                btnTahsil.setText(strName);
                slectedTahsilId = sLIST_TAHSIL.get(which).getTehsilId();
            }
        });
        builderSingle.show();
    }

    private void showAreaAlertListDialog(final ArrayAdapter<String> arrayAdapter) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
        builderSingle.setIcon(R.drawable.ic_search);
        builderSingle.setTitle("Select Area:");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                btnArea.setText(strName);
                slectedAreaId = sLIST_AREA.get(which).getAreaId();
            }
        });
        builderSingle.show();
    }

    private void getAllList() {
        Log.d("TAG", "State COUNT:" + listResponse.getData().getState().size());
        if (listResponse.getData().getState() != null)
            for (StateItem s : listResponse.getData().getState()) {
                sLIST_STATE.add(s);
                sSTATESLIST.add(s.getName());
            }
    }

    private void getAllCity(String stateId) {
        sCITIESLIST.clear();
        sLIST_CITY.clear();
        if (listResponse.getData().getCity() != null)
            for (CityItem cityItem : listResponse.getData().getCity()) {
                if (stateId.equals(cityItem.getStateId())) {
                    sLIST_CITY.add(cityItem);
                    sCITIESLIST.add(cityItem.getCityName());
                }
            }
    }

    private void getAllTahsil1(String cityId) {
        sLIST_TAHSIL.clear();
        sTAHSILSLIST.clear();
        sTAHSILSLIST.add(0, "Select Tahsil");
        if (listResponse.getData().getTashil() != null)
            for (TashilItem tashilItem : listResponse.getData().getTashil()) {
                if (cityId.equals(tashilItem.getCitiesId())) {
                    sLIST_TAHSIL.add(tashilItem);
                    sTAHSILSLIST.add(tashilItem.getTehsilName());
                }
            }
    }

    private void getAllTahsil(String cityId) {
        sLIST_TAHSIL.clear();
        sTAHSILSLIST.clear();
        RealmResults<TashilItem> subCategoryItems = realm.where(TashilItem.class).equalTo("citiesId", cityId).findAll();
        for (TashilItem c : subCategoryItems) {
            sLIST_TAHSIL.add(c);
            sTAHSILSLIST.add(c.getTehsilName());
        }
    }

    private void getAllArea(String cityId) {
        sLIST_AREA.clear();
        sAREALIST.clear();
        RealmResults<AreaItem> subCategoryItems = realm.where(AreaItem.class).equalTo("cityId", cityId).findAll();
        for (AreaItem c : subCategoryItems) {
            sLIST_AREA.add(c);
            sAREALIST.add(c.getAreaName());
        }
    }

    private void setListeners() {
        spnStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    if (sLIST_STATE.size() > 0) {
                        getAllCity(sLIST_STATE.get(position - 1).getId());
                        districtAdapter.notifyDataSetChanged();
                        selectedState = sLIST_STATE.get(position - 1).getId();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0)
                    if (sLIST_CITY.size() > 0) {
                        selectedCity = sLIST_CITY.get(position - 1).getCityId();

                        getAllTahsil(selectedCity);
                        //tahsilAdapter.notifyDataSetChanged();
                        Log.d("TAG", "City Selected:" + selectedCity);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnTahsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Log.d("TAG", "Selected :" + sLIST_TAHSIL.get(position - 1).getTehsilId());
                    selectedTahsil = sLIST_TAHSIL.get(position - 1).getTehsilId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // searchRequest(selectedState, selectedCity);
                if (slectedStateId == null) {
                    CommonUtils.showSnackBar(getActivity(), "Select State");
                    return;
                }
                if (slectedCityId == null) {
                    CommonUtils.showSnackBar(getActivity(), "Select City");
                    return;
                }
                if (slectedTahsilId == null) {
                    CommonUtils.showSnackBar(getActivity(), "Select Tahsil");
                    return;
                }
                if (ngp.equals("" + nagpurFlag))
                    if (slectedAreaId == null) {
                        CommonUtils.showSnackBar(getActivity(), "Select Area");
                        return;
                    }
                String areaID = "";
                if (ngp.equals("" + nagpurFlag))
                    areaID = slectedAreaId;

                if (CommonUtils.isInternetAvailable(getContext())) {
                    prefUtils.removeFromPrefs(getActivity(), SharedPrefUtils.KEY_SID);
                    prefUtils.removeFromPrefs(getActivity(), SharedPrefUtils.KEY_CID);
                    prefUtils.removeFromPrefs(getActivity(), SharedPrefUtils.KEY_TID);
                    prefUtils.removeFromPrefs(getActivity(), SharedPrefUtils.KEY_AID);
                    nextActivity(slectedStateId
                            , slectedCityId
                            , selectedTahsil, areaID);
                }
            }
        });

    }

    private void searchRequest(String selectedState, String selectedCity) {
        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<SearchDealerResponse> tashilList = anInterface.searchDealersList(
                new SearchRequest(
                        prefUtils.getFromPrefs(getContext(), prefUtils.KEY_IMEI, "")
                        , userDetails.getData().getId()
                        , userDetails.getData().getUserToken()
                        , "21"//selectedState
                        , "3"//selectedCity
                        , "1"
                ));
        tashilList.enqueue(new Callback<SearchDealerResponse>() {
            @Override
            public void onResponse(Call<SearchDealerResponse> call, Response<SearchDealerResponse> response) {
                if (response.isSuccessful()) {


                    ArrayList<DealersItem> dealersItems = new ArrayList<DealersItem>(response.body().getData().getDealers());


                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<SearchDealerResponse> call, Throwable t) {
                pb.dismiss();
            }
        });


    }

    private void nextActivity(String sId, String cId, String tId, String aId) {
        Intent intent = new Intent(getActivity(), DealersListActivity.class);
        // intent.putExtra("LIST", dealersItems);
        intent.putExtra("SID", sId);
        intent.putExtra("CID", cId);
        intent.putExtra("TID", tId);
        intent.putExtra("AID", aId);
        startActivity(intent);
    }

    private void getTahsil(String distId) {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());
        tLIST_CITY.clear();
        Call<TashilListResponse> tashilList = anInterface.getTashilList(
                new TashilListRequest(CommonUtils.getIMEI(getActivity()),
                        userDetails.getData().getId() + "",
                        userDetails.getData().getUserToken(), distId));
        tashilList.enqueue(new Callback<TashilListResponse>() {
            @Override
            public void onResponse(Call<TashilListResponse> call, Response<TashilListResponse> response) {
                if (response.isSuccessful()) {
                    setTashil(response.body());
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<TashilListResponse> call, Throwable t) {
                pb.dismiss();
            }
        });


    }

    private void setTashil(TashilListResponse body) {
        tValues.clear();
        mLIST_TAHSIL.clear();
        //tValues.add("Select Tahsil");
        for (int i = 0; i < body.getData().getTehsils().size(); i++) {
            addTahsilItem(new Model(body.getData().getTehsils().get(i).getTehsilId(),
                    body.getData().getTehsils().get(i).getTehsilName()));
            tValues.add(body.getData().getTehsils().get(i).getTehsilName());
            tLIST_CITY.add(new Model(body.getData().getTehsils().get(i).getTehsilId(), body.getData().getTehsils().get(i).getTehsilName()));

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1, tValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTahsil.setAdapter(adapter);
    }

    private void getStates() {

        String[] states = getResources().getStringArray(R.array.india_states);
        String[] cities = getResources().getStringArray(R.array.cities);
        String[] tahsil = getResources().getStringArray(R.array.tahsil);
        String[] area = getResources().getStringArray(R.array.area);
        for (int i = 0; i < states.length; i++) {
            addState(states[i]);
        }
        for (int i = 0; i < cities.length; i++) {
            citiesList.add(cities[i]);
        }
        for (int i = 0; i < tahsil.length; i++) {
            tahsilsList.add(tahsil[i]);
        }
        for (int i = 0; i < area.length; i++) {
            areaList.add(area[i]);
        }
    }

    private void addState(String state) {
    }


    private void getstateList() {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<StateListResponse> stateList = anInterface.getStateList(
                new StateListRequest(CommonUtils.getIMEI(getActivity()),
                        userDetails.getData().getId() + "",
                        userDetails.getData().getUserToken()));
        stateList.enqueue(new Callback<StateListResponse>() {
            @Override
            public void onResponse(Call<StateListResponse> call, Response<StateListResponse> response) {
                if (response.isSuccessful()) {
                    setStateSpiner(response.body());
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<StateListResponse> call, Throwable t) {
                pb.dismiss();
            }
        });
    }

    List<Model> mLIST_STATE = new ArrayList<>();
    List<Model> mLIST_TAHSIL = new ArrayList<>();

    void addStateItem(Model model) {
        mLIST_STATE.add(model);
    }

    void addTahsilItem(Model model) {
        mLIST_TAHSIL.add(model);
    }

    private void setStateSpiner(StateListResponse body) {

//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < body.getData().getStates().size(); i++) {
            // if (stateSelected.getId() == body.getData().getStates().get(i).getStateId())
            addStateItem(new Model(body.getData().getStates().get(i).getId(), body.getData().getStates().get(i).getName()));
            sValues.add(body.getData().getStates().get(i).getName());
        }
        CustomAdapter customAdapter = new CustomAdapter(getContext(), mLIST_STATE);
        //spnState.setAdapter(customAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1, sValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStates.setAdapter(adapter);
    }


    private void getCityList(String stateId) {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<CityListResponse> stateList = anInterface.getCityList(
                new CityListRequest(CommonUtils.getIMEI(getActivity()),
                        userDetails.getData().getId(),
                        userDetails.getData().getUserToken(),
                        stateId));
        stateList.enqueue(new Callback<CityListResponse>() {
            @Override
            public void onResponse(Call<CityListResponse> call, Response<CityListResponse> response) {
                if (response.isSuccessful()) {
                    setCitySpiner(response.body());
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<CityListResponse> call, Throwable t) {
                pb.dismiss();
            }
        });
    }

    void addCityItem(Model model) {

        mLIST_CITY.add(model);
    }

    private void setCitySpiner(CityListResponse body) {
        cValues.clear();
        mLIST_CITY.clear();
        //  cValues.add("Select District");
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < body.getData().getCities().size(); i++) {
            // if (stateSelected.getId() == body.getData().getCities().get(i).getStateId()) {
            addCityItem(new Model(body.getData().getCities().get(i).getCityId(),
                    body.getData().getCities().get(i).getCityName()));
            cValues.add(body.getData().getCities().get(i).getCityName());
        }  // }
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), mLIST_CITY);
        // spinner.setAdapter(customAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, android.R.id.text1, cValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDistrict.setAdapter(adapter);
    }
}
