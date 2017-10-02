package com.plasto.dealerapp.reaml;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.plasto.dealerapp.retrofit.getLoginData.response.AreaItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.CityItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.StateItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.SubCategoryItem;
import com.plasto.dealerapp.retrofit.getLoginData.response.TashilItem;
import com.plasto.dealerapp.retrofit.login.response.Data;

import io.realm.Realm;

/**
 * Created by Pat-Win 10 on 05-03-2017.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        //realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.delete(Data.class);
        realm.delete(StateItem.class);
        realm.delete(CityItem.class);
        realm.delete(TashilItem.class);
        realm.delete(AreaItem.class);
        realm.delete(CategoryItem.class);
        realm.delete(SubCategoryItem.class);
        realm.commitTransaction();
    }

}
