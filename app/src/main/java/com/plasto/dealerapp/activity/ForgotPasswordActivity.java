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
import com.plasto.dealerapp.retrofit.forgotPassword.request.ForgotPwRequest;
import com.plasto.dealerapp.retrofit.forgotPassword.response.ForgotPwResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtEmailId;
    private ApiInterface anInterface;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pb = new ProgressDialog(this, "Loading...", R.color.colorPrimary);
        anInterface =
                ApiClient.getClient().create(ApiInterface.class);

        edtEmailId = (EditText) findViewById(R.id.editTextEmail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmailId.getText().toString();
                if (CommonUtils.isValidEmail(email)) submitEmail(email);
                else CommonUtils.showSnackBar(ForgotPasswordActivity.this, "Invalid Email-id");
            }
        });

    }

    private void submitEmail(String email) {
        pb.show();
        Call<ForgotPwResponse> forgotPwResponseCall = anInterface.submitForgotPwEmail(new ForgotPwRequest(email, CommonUtils.getIMEI(ForgotPasswordActivity.this)));
        forgotPwResponseCall.enqueue(new Callback<ForgotPwResponse>() {
            @Override
            public void onResponse(Call<ForgotPwResponse> call, Response<ForgotPwResponse> response) {
                if (response.isSuccessful() && response.body().isResponse()) {
                    finish();
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    CommonUtils.showSnackBar(ForgotPasswordActivity.this, response.body().getMessage());
                }

                pb.dismiss();
            }

            @Override
            public void onFailure(Call<ForgotPwResponse> call, Throwable t) {
                pb.dismiss();
            }
        });
    }
}
