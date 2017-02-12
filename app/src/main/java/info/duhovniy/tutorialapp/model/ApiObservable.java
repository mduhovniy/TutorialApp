package info.duhovniy.tutorialapp.model;


import android.util.Base64;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiObservable {


    String BASE_URL = "http://duhovniy.info";
    String USER = "maxim";
    String PASS = "12345";

    @GET("api/fragments")
    Observable<List<FragmentModel>> getAllFragments();

    @GET("api/fragments/{id}")
    Observable<FragmentModel> getFragmentById(@Path("id") int id);

    class Factory {
        public static ApiCallable create() {
            String credentials = USER + ":" + PASS;
            final String basic =
                    "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

            // Define the interceptor, add authentication headers
            Interceptor interceptor = chain -> {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", basic)
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            };

            // Add the interceptor to OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(interceptor);
            OkHttpClient client = builder.build();

            // Set the custom client when building adapter
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(ApiCallable.class);
        }
    }
}
