package com.plasto.dealerapp.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.reaml.RealmController;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.categoryList.request.CategoryRequest;
import com.plasto.dealerapp.retrofit.getLoginData.response.AreaItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CityItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.GetLoginDataResponse;
import com.plasto.dealerapp.retrofit.getLoginData.response.StateItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.SubCategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.TashilItem;
import com.plasto.dealerapp.retrofit.login.request.LoginRequest;
import com.plasto.dealerapp.retrofit.login.response.Data;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.plasto.dealerapp.utils.Constants.PERMISSIONS_REQUEST_READ_PHONE_STATE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    Context context = LoginActivity.this;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private ProgressDialog pb;

    String IMEI_NUMBER;
    ApiInterface apiService;
    private SharedPrefUtils pref = new SharedPrefUtils();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        RealmController.with(this).clearAll();

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.input_email);
        pb = new ProgressDialog(context, "Loading...", R.color.colorPrimary);
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);

            } else {
                IMEI_NUMBER = CommonUtils.getIMEI(LoginActivity.this);
            }
        } else {
            IMEI_NUMBER = CommonUtils.getIMEI(LoginActivity.this);
        }
        Log.d("LOGIN", "IMEI No : " + IMEI_NUMBER);
        pref.saveToPrefs(context, pref.KEY_IMEI, IMEI_NUMBER);
        mPasswordView = (EditText) findViewById(R.id.input_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.btn_login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.btn_login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // Store values at the time of the login attempt.
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                authUser(email, password, IMEI_NUMBER);
            }
        });

        findViewById(R.id.link_signup).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });
        findViewById(R.id.register).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            IMEI_NUMBER = CommonUtils.getIMEI(LoginActivity.this);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            authUser(email, password, IMEI_NUMBER);

        }
    }

    public void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    public Realm getRealm() {
        return realm;
    }

    private void authUser(String email, String password, final String IMEI_NUMBER) {
        pb.show();
        Call<LoginResponse> responseCall = apiService.userLogin(new LoginRequest(password, IMEI_NUMBER, email));
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().isResponse()) {
                        pref.saveToPrefs(context, Constants.SP_KEY_USER_DETAILS_JSON, new Gson().toJson(response.body()));
                        Data userData = response.body().getData();
                        realm.beginTransaction();
                        realm.insert(userData);
                        realm.commitTransaction();

                    /*startActivity(new Intent(context, MainDashboardActivity.class));
                    finish();*/
                        getUserCityList(new CategoryRequest(IMEI_NUMBER, response.body().getData().getId(), response.body().getData().getUserToken()));


                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                }

                pb.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pb.dismiss();
            }
        });

    }


    private void getUserCityList(CategoryRequest request) {
        pb.show();
        Call<GetLoginDataResponse> responseCall = apiService.getUserCityList(request);
        responseCall.enqueue(new Callback<GetLoginDataResponse>() {
            @Override
            public void onResponse(Call<GetLoginDataResponse> call, Response<GetLoginDataResponse> response) {
                if (response.isSuccessful() && response.body().isResponse()) {
                    pref.saveToPrefs(context, Constants.SP_KEY_USER_CITY_DETAILS_JSON, new Gson().toJson(response.body()));

                    List<StateItem> stateItem = response.body().getData().getState();
                    List<CityItem> cityItems = response.body().getData().getCity();
                    List<TashilItem> tashilItems = response.body().getData().getTashil();
                    List<AreaItem> areaItems = response.body().getData().getArea();
                    List<CategoryItem> categoryItems = response.body().getData().getCategory();
                    List<SubCategoryItem> subCategoryItems = response.body().getData().getSubCategory();

                    realm.beginTransaction();
                    realm.copyToRealm(stateItem);
                    realm.copyToRealm(cityItems);
                    realm.copyToRealm(tashilItems);
                    realm.copyToRealm(areaItems);
                    realm.copyToRealm(categoryItems);
                    realm.copyToRealm(subCategoryItems);
                    realm.commitTransaction();
                    startActivity(new Intent(context, MainDashboardActivity.class));
                    finish();
                }
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<GetLoginDataResponse> call, Throwable t) {
                pb.dismiss();
            }
        });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        //  mEmailView.setAdapter(adapter);
    }


}

