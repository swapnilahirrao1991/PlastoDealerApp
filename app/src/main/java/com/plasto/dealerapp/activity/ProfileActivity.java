package com.plasto.dealerapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;

import com.plasto.dealerapp.R;
import com.plasto.dealerapp.retrofit.getLoginData.response.StateItem;
import com.plasto.dealerapp.retrofit.login.response.Data;

import io.realm.Realm;
import io.realm.RealmResults;

public class ProfileActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        RealmResults<Data> userData = realm.where(Data.class).findAll();
        ((AppCompatEditText) findViewById(R.id.textViewUserName)).setText(userData.get(0).getName());
        ((AppCompatEditText) findViewById(R.id.textViewUserEmail)).setText(userData.get(0).getEmail());
        ((AppCompatEditText) findViewById(R.id.textViewUserMobile)).setText(userData.get(0).getMobile());

        String stateId = userData.get(0).getStateId();

        RealmResults<StateItem> results = realm.where(StateItem.class).equalTo("id", stateId).findAll();


        if (results.size() == 0) {
            ((AppCompatEditText) findViewById(R.id.textViewUserOtherMobile)).setText("-");
        } else
            ((AppCompatEditText) findViewById(R.id.textViewUserOtherMobile)).setText(results.get(0).getName());

    }
}
