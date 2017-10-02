package com.plasto.dealerapp.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pat-Win 10 on 03-01-2017.
 */

public class ApiClient {
    /*******
     *
     * LOCAL HOST
     */
    //public static final String BASE_URL = "http://192.168.0.9:8081/plasto/webservices/";

    /*******
     * Live-Demo
     */
   // public static final String BASE_URL = "http://plasto.purotechnologies.in/webservices/";
    public static final String BASE_URL = "http://app.plasto.in/webservices/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addInterceptor(interceptor)
                    .build();
            // client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
                    .build();
        }
    };
}
