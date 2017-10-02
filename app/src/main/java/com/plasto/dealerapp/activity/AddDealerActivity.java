package com.plasto.dealerapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.adapter.CustomAdapter;
import com.plasto.dealerapp.model.Model;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.addDealer.request.AddDealerRequest;
import com.plasto.dealerapp.retrofit.addDealer.response.AddDealerResponse;
import com.plasto.dealerapp.retrofit.categoryList.request.CategoryRequest;
import com.plasto.dealerapp.retrofit.categoryList.request.response.CategoryResponse;
import com.plasto.dealerapp.retrofit.cityList.response.CityListResponse;
import com.plasto.dealerapp.retrofit.cityList.response.request.CityListRequest;
import com.plasto.dealerapp.retrofit.getLoginData.response.AreaItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CityItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.GetLoginDataResponse;
import com.plasto.dealerapp.retrofit.getLoginData.response.StateItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.SubCategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.TashilItem;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.stateList.request.StateListRequest;
import com.plasto.dealerapp.retrofit.stateList.response.StateListResponse;
import com.plasto.dealerapp.retrofit.subCategory.request.SubCategoryRequest;
import com.plasto.dealerapp.retrofit.subCategory.request.response.SubCategoryResponse;
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

import static android.R.attr.value;

public class AddDealerActivity extends AppCompatActivity implements View.OnClickListener {
    private String STATE = "";
    private Context context = AddDealerActivity.this;
    private Button btnSubmit;
    private LoginResponse userDetails;
    SharedPrefUtils prefUtils = new SharedPrefUtils();
    private ProgressDialog pb;
    private Spinner spinner, spnState, spnCategory, spnSubCategory, spnTashil;
    private ApiInterface anInterface;
    private GetLoginDataResponse listResponse;
    private List<AreaItem> sLIST_AREA = new ArrayList<>();
    private List<String> sAREALIST = new ArrayList<String>();
    EditText edtName, edtEmail, edtAddress, edtMob, edtMob1, edtMob2, edtMob3, edtMob4;
    EditText edtState, edtCity, edtCategory, edtSubCategory, edtTahsil;
    EditText edtArea;
    Model stateSelected;
    List<Model> mLIST_STATE = new ArrayList<>();
    List<Model> mLIST_CITY = new ArrayList<>();
    List<Model> mLIST_CATEGORY = new ArrayList<>();
    List<Model> mLIST_SUB_CATEGORY = new ArrayList<>();
    List<Model> mLIST_TASHIL = new ArrayList<>();

    ArrayList<String> sValues = new ArrayList<>();
    ArrayList<String> cValues = new ArrayList<>();
    ArrayList<String> catValues = new ArrayList<>();
    ArrayList<String> subCatValues = new ArrayList<>();
    ArrayList<String> tashilValues = new ArrayList<>();

    private String selectedCat = "0";
    private String selectedSubCat = "0";
    private String selectedTashil = "0";

    private String selectedArea = "0";
    private String selectedState = "0";
    private String selectedCity = "0";
    //  private Spinner spnArea;
    // private int nagpurFlag = 1;
    // private String ngp;

    void addStateItem(Model model) {
        mLIST_STATE.add(model);
    }

    void addCityItem(Model model) {
        mLIST_CITY.add(model);
    }

    void addCategoryItem(Model model) {
        mLIST_CATEGORY.add(model);
    }

    void addSubCategoryItem(Model model) {
        mLIST_SUB_CATEGORY.add(model);
    }

    void addTashilItem(Model model) {
        mLIST_TASHIL.add(model);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        init();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        userDetails = new Gson().fromJson(prefUtils.getFromPrefs(context, Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);
        listResponse = new Gson().fromJson(prefUtils.getFromPrefs(context, Constants.SP_KEY_USER_CITY_DETAILS_JSON, null), GetLoginDataResponse.class);


        btnSubmit.setOnClickListener(this);
        spinnerListeners();
        getStateList();
        getCategoryList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    List<String> sSTATESLIST = new ArrayList<String>();
    List<StateItem> sLIST_STATE = new ArrayList<>();

    List<String> sCATEGORY = new ArrayList<String>();
    List<CategoryItem> sLIST_CATEGORY = new ArrayList<>();

    List<String> sSUBCATEGORY = new ArrayList<String>();
    List<SubCategoryItem> sLIST_SUBCATEGORY = new ArrayList<>();

    private void getStateList() {
        RealmResults<StateItem> all = realm.where(StateItem.class).findAll();
        sSTATESLIST.add("Select State");
        for (int i = 0; i < all.size(); i++) {
            sLIST_STATE.add(all.get(i));
            sSTATESLIST.add(all.get(i).getName());
        }
    }

    private void getCategoryList() {
        RealmResults<CategoryItem> catAll = realm.where(CategoryItem.class).findAll();
        sCATEGORY.add("Select Category");
        for (int i = 0; i < catAll.size(); i++) {
            sLIST_CATEGORY.add(catAll.get(i));
            sCATEGORY.add(catAll.get(i).getCategory());
        }
    }


    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    Realm realm;

    public Realm getRealm() {
        return realm;
    }

    ArrayAdapter<String> statesAdapter;

    private void spinnerListeners() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int i, long arg3) {
                if (i > 0) {
                    Log.d("CITY ", "Selected city::" + mLIST_CITY.size());
                    selectedCity = mLIST_CITY.get(i - 1).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //  stateSelected.setState(mLIST_STATE.get(i).getId());
                if (position > 0) {
                    if (sLIST_STATE.size() > 0) {
                        selectedState = sLIST_STATE.get(position - 1).getId();
                        getAllCity(sLIST_STATE.get(position - 1).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnTashil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //  stateSelected.setState(mLIST_STATE.get(i).getId());
                if (position > 0) {
                    if (mLIST_TASHIL.size() > 0) {
                        selectedTashil = mLIST_TASHIL.get(position - 1).getId();
                        // getAllCity(sLIST_STATE.get(position - 1).getId());
                        selectedArea = "0";
                        getAllArea("355");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    selectedCat = sLIST_CATEGORY.get(i - 1).getId();
                getSubCategoriesList(selectedCat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spnSubCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    if (mLIST_SUB_CATEGORY.size() > 0)
                        selectedSubCat = mLIST_SUB_CATEGORY.get(i - 1).getId();
                    // getSubCategoriesList(selectedCat);

                    //getSubCategories(selectedCat + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnState.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (sValues.size() == 0) {   // getstateList();
                    statesAdapter = new ArrayAdapter<String>(AddDealerActivity.this, android.R.layout.simple_spinner_item, sSTATESLIST);
                    statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnState.setAdapter(statesAdapter);
                }
                return false;
            }
        });
        spnSubCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if ((selectedCat + "").equals("0")) {
                    Toast.makeText(context, "Select Category", Toast.LENGTH_SHORT).show();
                } else {
                    //  getSubCategories(selectedCat + "");
                }
                return false;
            }
        });
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               /* if (stateSelected.getId().equals("0"))
                    Toast.makeText(context, "Select State", Toast.LENGTH_SHORT).show();
                else*/
                //getCityList(stateSelected.getId());

                getAllCity(selectedState);
                return false;
            }
        });

        spnCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
               /* if (catValues.size() == 0)
                    getCategoryList();*/

                if (catValues.size() == 0) {   // getstateList();
                    cAdapter = new ArrayAdapter<String>(AddDealerActivity.this, android.R.layout.simple_spinner_item, sCATEGORY);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnCategory.setAdapter(cAdapter);
                }
                return false;
            }
        });
        spnTashil.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // if (tashilValues.size() == 0)
                getTashil(selectedCity);
                return false;
            }
        });

    }

    List<String> sCITIESLIST = new ArrayList<String>();
    List<CityItem> sLIST_CITY = new ArrayList<>();

    private void getAllCity(String stateId) {
        sCITIESLIST.clear();
        sLIST_CITY.clear();
        /*sCITIESLIST.add(0, "Select City");
        if (listResponse.getData().getCity() != null)
            for (CitysItem cityItem : listResponse.getData().getCity()) {
                if (stateId.equals(cityItem.getStateId())) {
                    sLIST_CITY.add(cityItem);
                    sCITIESLIST.add(cityItem.getCityName());
                }
            }*/

        RealmResults<CityItem> cityItems = realm.where(CityItem.class).equalTo("stateId", stateId).findAll();

        Log.d("CITY", "#State:" + stateId + "->City List :" + cityItems.size());
        sCITIESLIST.add(0, "Select City");
        for (CityItem c : cityItems) {
            mLIST_CITY.add(new Model(c.getCityId(), c.getCityName()));
            sCITIESLIST.add(c.getCityName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, sCITIESLIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void getAllArea(String cityId) {
        sLIST_AREA.clear();
        sAREALIST.clear();
        RealmResults<AreaItem> subCategoryItems = realm.where(AreaItem.class).equalTo("cityId", cityId).findAll();
        for (AreaItem c : subCategoryItems) {
            sLIST_AREA.add(c);
            sAREALIST.add(c.getAreaName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, sAREALIST);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spnArea.setAdapter(adapter);
    }

    private void getSubCategoriesList(String catId) {
        sSUBCATEGORY.clear();
        mLIST_SUB_CATEGORY.clear();

        RealmResults<SubCategoryItem> subCategoryItems = realm.where(SubCategoryItem.class).equalTo("cateId", catId).findAll();

        Log.d("CITY", "#catId:" + catId + "->subCategoryItems List :" + subCategoryItems.size());
        sSUBCATEGORY.add(0, "Select Sub-Category");
        for (SubCategoryItem c : subCategoryItems) {
            mLIST_SUB_CATEGORY.add(new Model(c.getId(), c.getCategory()));
            sSUBCATEGORY.add(c.getCategory());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, sSUBCATEGORY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSubCategory.setAdapter(adapter);
    }

    private void getTashil(String cityId) {
        tashilValues.clear();
        mLIST_TASHIL.clear();
        RealmResults<TashilItem> tashilItems = realm.where(TashilItem.class).equalTo("citiesId", cityId).findAll();

        Log.d("CITY", "#State:" + cityId + "->tashilItems List :" + tashilItems.size());
        tashilValues.add(0, "Select Tahsil");
        for (TashilItem t : tashilItems) {
            addTashilItem(new Model(t.getTehsilId(), t.getTehsilName()));
            tashilValues.add(t.getTehsilName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, tashilValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTashil.setAdapter(adapter);
    }

    private void setTashil(TashilListResponse body) {
        for (int i = 0; i < body.getData().getTehsils().size(); i++) {
            addSubCategoryItem(new Model(body.getData().getTehsils().get(i).getTehsilId(), body.getData().getTehsils().get(i).getTehsilName()));
            tashilValues.add(body.getData().getTehsils().get(i).getTehsilName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, tashilValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTashil.setAdapter(adapter);
    }

    private void getCategories() {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<CategoryResponse> stateList = anInterface.getCategoryList(
                new CategoryRequest(CommonUtils.getIMEI(AddDealerActivity.this),
                        userDetails.getData().getId() + "",
                        userDetails.getData().getUserToken()));
        stateList.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    // setStateSpiner(response.body());
                    setCategory(response.body());
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                pb.dismiss();
            }
        });

    }

    private void getSubCategories(String s) {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<SubCategoryResponse> stateList = anInterface.getSubCategoryList(
                new SubCategoryRequest(CommonUtils.getIMEI(AddDealerActivity.this), s + "",
                        userDetails.getData().getId() + "",
                        userDetails.getData().getUserToken()));
        stateList.enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                if (response.isSuccessful()) {
                    // setStateSpiner(response.body());
                    setSubCategory(response.body());
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {
                pb.dismiss();
            }
        });

    }

    ArrayAdapter<String> sCadapter;

    private void setSubCategory(SubCategoryResponse body) {
        for (int i = 0; i < body.getData().getSubCategorys().size(); i++) {
            addSubCategoryItem(new Model(body.getData().getSubCategorys().get(i).getId(), body.getData().getSubCategorys().get(i).getCategory()));
            subCatValues.add(body.getData().getSubCategorys().get(i).getCategory());
        }
        sCadapter = new ArrayAdapter<>(AddDealerActivity.this,
                android.R.layout.simple_spinner_item, android.R.id.text1, subCatValues);
        sCadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnSubCategory.setAdapter(sCadapter);

    }

    ArrayAdapter<String> cAdapter;

    private void setCategory(CategoryResponse body) {


        for (int i = 0; i < body.getData().getCategory().size(); i++) {
            addCategoryItem(new Model(body.getData().getCategory().get(i).getId(), body.getData().getCategory().get(i).getCategory()));
            catValues.add(body.getData().getCategory().get(i).getCategory());
        }

        cAdapter = new ArrayAdapter<String>(AddDealerActivity.this,
                android.R.layout.simple_spinner_item, android.R.id.text1, catValues);
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    private void getstateList() {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<StateListResponse> stateList = anInterface.getStateList(
                new StateListRequest(CommonUtils.getIMEI(AddDealerActivity.this),
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

    private void getCityList(String stateId) {

        pb.show();
        Log.d("TOKEN", "TOKEN : " + userDetails.getData().getUserToken());

        Call<CityListResponse> stateList = anInterface.getCityList(
                new CityListRequest(CommonUtils.getIMEI(AddDealerActivity.this),
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

    private void setStateSpiner(StateListResponse body) {
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < body.getData().getStates().size(); i++) {
            // if (stateSelected.getId() == body.getData().getStates().get(i).getStateId())
            addStateItem(new Model(body.getData().getStates().get(i).getId(), body.getData().getStates().get(i).getName()));
            sValues.add(body.getData().getStates().get(i).getName());
        }
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), mLIST_STATE);
        //spnState.setAdapter(customAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, sValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnState.setAdapter(adapter);
    }

    private void setCitySpiner(CityListResponse body) {
        //  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 0; i < body.getData().getCities().size(); i++) {
            // if (stateSelected.getId() == body.getData().getCities().get(i).getStateId()) {
            addCityItem(new Model(body.getData().getCities().get(i).getCityId(),
                    body.getData().getCities().get(i).getCityName()));
            cValues.add(body.getData().getCities().get(i).getCityName());
        }  // }
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), mLIST_CITY);
        // spinner.setAdapter(customAdapter);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, android.R.id.text1, cValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void init() {
        edtName = (EditText) findViewById(R.id.editviewName);
        edtEmail = (EditText) findViewById(R.id.email);


        edtArea = (EditText) findViewById(R.id.area);
        edtMob = (EditText) findViewById(R.id.editviewMobileNo);
        edtMob1 = (EditText) findViewById(R.id.editviewMobileOtherOne);
        edtMob2 = (EditText) findViewById(R.id.editviewMobileOtherTwo);
        edtMob3 = (EditText) findViewById(R.id.editviewMobileOtherThree);
        edtMob4 = (EditText) findViewById(R.id.editviewMobileOtherFour);
        edtAddress = (EditText) findViewById(R.id.address);

        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        spinner = (Spinner) findViewById(R.id.spinner);
        spnState = (Spinner) findViewById(R.id.spinner_state);
        spnTashil = (Spinner) findViewById(R.id.spinner_tashil);
        spnCategory = (Spinner) findViewById(R.id.spinner_category);
        spnSubCategory = (Spinner) findViewById(R.id.spinner_subCategory);
        stateSelected = new Model("0", "");
        pb = new ProgressDialog(context, "Loading...", R.color.colorPrimary);

        anInterface = ApiClient.getClient().create(ApiInterface.class);
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);
        // spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonSubmit) {

            if (edtName.getText().toString().isEmpty()) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter name");
                return;
            }
           /* if (edtEmail.getText().toString().isEmpty()) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter email");
                return;
            }*/
            if (edtMob.getText().toString().isEmpty()) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter Mobile no.");
                return;
            }
            if (!validateOther(edtMob)) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter valid Mobile no.");
                return;
            }
            if (!validateOther(edtMob1)) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter valid Mobile no. 1");
                return;
            }
            if (!validateOther(edtMob2)) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter valid Mobile no. 2");
                return;
            }
            if (!validateOther(edtMob3)) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter valid Mobile no. 3");
                return;
            }





            if (edtAddress.getText().toString().isEmpty()) {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Enter Address");
                return;
            }
            if (selectedState == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select State");
                return;
            }
            if (selectedCity == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select City");
                return;
            }
            if (selectedCat == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select Category");
                return;
            }
            if (selectedSubCat == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select sub Category");
                return;
            }
            if (selectedTashil == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select Tahsil");
                return;
            }

            String area = edtArea.getText().toString();

            /*if (nagpurFlag == Integer.parseInt(ngp) && selectedArea == "0") {
                CommonUtils.showSnackBar(AddDealerActivity.this, "Select Area");
                return;
            }*/

            String email = edtEmail.getText().toString();
            AddDealerRequest dealerRequest = new AddDealerRequest(edtMob2.getText().toString(),
                    edtMob4.getText().toString(),
                    edtMob3.getText().toString(),
                    edtAddress.getText().toString(),
                    selectedTashil,
                    edtMob.getText().toString(),
                    edtMob1.getText().toString(),
                    selectedCat,
                    selectedSubCat,
                    edtName.getText().toString().trim(),
                    CommonUtils.getIMEI(AddDealerActivity.this),
                    userDetails.getData().getId(),
                    userDetails.getData().getUserToken(),
                    selectedState,
                    email,
                    selectedCity, area);


            // Log.d("ADD", "add:" + dealerRequest.toString());
             submitData(dealerRequest);
            //NavUtils.navigateUpFromSameTask(this);
            //   NavUtils.navigateUpTo(this, new Intent(AddDealerActivity.this, MainDashboardActivity.class));
            //  Toast.makeText(context, "Added new dealer ", Toast.LENGTH_SHORT).show();
            // submitData(dealerRequest);
        }
    }

    private boolean validateOther(EditText edt) {
        if (edt.getText().toString().isEmpty() || edt.getText().toString().length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    private void submitData(AddDealerRequest dealerRequest) {
        pb.show();
        Call<AddDealerResponse> addDealerResponseCall = anInterface.addDealer(dealerRequest);
        addDealerResponseCall.enqueue(new Callback<AddDealerResponse>() {
            @Override
            public void onResponse(Call<AddDealerResponse> call, Response<AddDealerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isResponse())
                        backActivity();
                    Toast.makeText(AddDealerActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<AddDealerResponse> call, Throwable t) {
                pb.dismiss();
            }
        });

    }

    private void backActivity() {
        NavUtils.navigateUpTo(this, new Intent(AddDealerActivity.this, MainDashboardActivity.class));

        // startActivity(new Intent(context, DashboardActivity.class));
        //  finish();
    }

    void showListDialog() {
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(AddDealerActivity.this);
        alertdialogbuilder.setTitle("Select ");
        alertdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   String selectedText = Arrays.asList(value).get(which);

                //  textview.setText(selectedText);
            }
        });
        AlertDialog dialog = alertdialogbuilder.create();
        dialog.show();
    }
}





