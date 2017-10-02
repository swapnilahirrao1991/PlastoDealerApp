package com.plasto.dealerapp.appilication;

import android.app.Application;
import android.os.SystemClock;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;

/**
 * Created by Pat-Win 10 on 12-01-2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));
// Initialize Realm
        Realm.init(this);
    }


}
