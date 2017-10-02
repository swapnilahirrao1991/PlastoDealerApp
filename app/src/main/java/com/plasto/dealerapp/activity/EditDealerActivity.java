package com.plasto.dealerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.editdealer.request.EditDealerRequest;
import com.plasto.dealerapp.retrofit.editdealer.response.EditDealerResponse;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.searchDealer.response.DealersItem;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDealerActivity extends AppCompatActivity {
    private DealersItem dealersItem;
    private EditText edtName;
    private EditText edtAddress, edtArea;
    private EditText edtMobileNo, edtMobileNo1, edtMobileNo2, edtMobileNo3, edtMobileNo4;
    private LoginResponse userDetails;
    Gson gson = new Gson();
    SharedPrefUtils prefUtils;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dealer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        String DATA = i.getStringExtra("DEALER_DATA");

        prefUtils = new SharedPrefUtils();
        dealersItem = new Gson().fromJson(DATA, DealersItem.class);
        userDetails = gson.fromJson(prefUtils.getFromPrefs(EditDealerActivity.this, Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);

        init();
        setData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //  startActivity(new Intent(EditDealerActivity.this, DealersListActivity.class));
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        edtName = (EditText) findViewById(R.id.input_name);
        edtAddress = (EditText) findViewById(R.id.editTextAddress);
        edtArea = (EditText) findViewById(R.id.editTextArea);
        edtMobileNo = (EditText) findViewById(R.id.editMobileNo);
        edtMobileNo1 = (EditText) findViewById(R.id.editMobileNo1);
        edtMobileNo2 = (EditText) findViewById(R.id.editMobileNo2);
        edtMobileNo3 = (EditText) findViewById(R.id.editMobileNo3);
        edtMobileNo4 = (EditText) findViewById(R.id.editMobileNo4);
        pb = new ProgressDialog(EditDealerActivity.this, "Loading...", R.color.colorPrimary);
        pb.setCancelable(false);

        findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddress.getText().toString().isEmpty()) {
                    Toast.makeText(EditDealerActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtArea.getText().toString().isEmpty()) {
                    Toast.makeText(EditDealerActivity.this, "Enter Area", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtMobileNo.getText().toString().isEmpty()) {
                    Toast.makeText(EditDealerActivity.this, "Enter Mobile No.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidNo(edtMobileNo, 10)) {
                    Toast.makeText(EditDealerActivity.this, "Invalid Mobile No.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidNo(edtMobileNo1, 10)) {
                    Toast.makeText(EditDealerActivity.this, "Invalid Mobile No. 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidNo(edtMobileNo2, 10)) {
                    Toast.makeText(EditDealerActivity.this, "Invalid Mobile No. 2", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidNo(edtMobileNo3, 10)) {
                    Toast.makeText(EditDealerActivity.this, "Invalid Mobile No. 3", Toast.LENGTH_SHORT).show();
                    return;
                }


                EditDealerRequest req = new EditDealerRequest();
                req.setId(dealersItem.getDealrId());
                req.setDealerId(dealersItem.getDealrId());
                req.setDealrId(dealersItem.getDealrId());
                req.setUserToken(userDetails.getData().getUserToken());
                req.setAddress(edtAddress.getText().toString().trim());
                req.setArea(edtArea.getText().toString().trim());
                req.setImei(CommonUtils.getIMEI(EditDealerActivity.this));
                req.setMobile(edtMobileNo.getText().toString().trim());
                req.setOtherMobile(edtMobileNo1.getText().toString().trim());
                req.setOtherMobile2(edtMobileNo2.getText().toString().trim());
                req.setOtherMobile3(edtMobileNo3.getText().toString().trim());
                req.setOtherMobile4(edtMobileNo4.getText().toString().trim());

                if (CommonUtils.isInternetAvailable(EditDealerActivity.this)) {

                    ApiInterface anInterface = ApiClient.getClient().create(ApiInterface.class);
                    pb.show();

                    Call<EditDealerResponse> editDealerCall = anInterface.editDealer(req);
                    editDealerCall.enqueue(new Callback<EditDealerResponse>() {
                        @Override
                        public void onResponse(Call<EditDealerResponse> call, Response<EditDealerResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().isResponse()) {
                                    Intent i = new Intent(EditDealerActivity.this, DealersListActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                                Toast.makeText(EditDealerActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            pb.dismiss();
                        }

                        @Override
                        public void onFailure(Call<EditDealerResponse> call, Throwable t) {
                            Toast.makeText(EditDealerActivity.this, "Please try again...", Toast.LENGTH_SHORT).show();
                            pb.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void setData() {
        edtName.setText(dealersItem.getName());
        edtName.setEnabled(false);
        edtAddress.setText(dealersItem.getAddress());
        edtArea.setText(dealersItem.getArea_name());
        edtMobileNo.setText(dealersItem.getMobile());
        edtMobileNo1.setText(dealersItem.getOtherMobile());
        edtMobileNo2.setText(dealersItem.getOtherMobile2());
        edtMobileNo3.setText(dealersItem.getOtherMobile3());
        edtMobileNo4.setText(dealersItem.getOtherMobile4());


    }

    private boolean isValidNo(EditText edt, int max) {
        if (edt.getText().toString().isEmpty() || edt.getText().toString().length() == 10) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditDealerActivity.this, DealersListActivity.class));
        finish();
    }
}
