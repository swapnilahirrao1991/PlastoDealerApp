package com.plasto.dealerapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.register.request.RegisterRequest;
import com.plasto.dealerapp.retrofit.register.response.RegisterResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {


    EditText edtName, edtMobile, edtMsg, edtEmail;
    private ApiInterface apiInterface;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        edtName = (EditText) findViewById(R.id.registerName);
        edtMobile = (EditText) findViewById(R.id.registerMobileNo);
        edtMsg = (EditText) findViewById(R.id.registerMessage);
        edtEmail = (EditText) findViewById(R.id.registerEmail);
        pb = new ProgressDialog(this, "Loading...", R.color.colorPrimary);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().toString().isEmpty()) {
                    CommonUtils.showSnackBar(RegisterActivity.this, "Enter Name");
                    return;
                }
                if (edtEmail.getText().toString().isEmpty()) {
                    CommonUtils.showSnackBar(RegisterActivity.this, "Enter Email id");
                    return;
                }

                if (!CommonUtils.isValidEmail(edtEmail.getText().toString())) {
                    CommonUtils.showSnackBar(RegisterActivity.this, "Invalid Email id");
                    return;
                }
                if (edtMobile.getText().toString().isEmpty()) {
                    CommonUtils.showSnackBar(RegisterActivity.this, "Enter Name");
                    return;
                }
                if (edtMsg.getText().toString().isEmpty()) {
                    CommonUtils.showSnackBar(RegisterActivity.this, "Enter Name");
                    return;
                }

                if (CommonUtils.isInternetAvailable(RegisterActivity.this)) {
                    submitRegistration(new RegisterRequest(edtName.getText().toString().trim(),
                            edtMobile.getText().toString().trim(),
                            edtMsg.getText().toString().trim(), CommonUtils.getIMEI(RegisterActivity.this),
                            edtEmail.getText().toString().trim()));
                }
            }
        });


    }

    private void submitRegistration(RegisterRequest registerRequest) {
        pb.show();
        Call<RegisterResponse> call = apiInterface.submitRegistration(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body().isResponse()) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else
                    CommonUtils.showSnackBar(RegisterActivity.this, response.body().getMessage());
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                pb.dismiss();
            }
        });
    }

}
