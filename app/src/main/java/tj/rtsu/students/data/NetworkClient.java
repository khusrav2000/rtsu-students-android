package tj.rtsu.students.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Соединения с сервером.
public class NetworkClient {

    // public static final String BASE_URL = "http://77.95.1.55:7575";
    public static final String BASE_URL = "http://109.74.66.100:3389";
    //public static final String BASE_URL = "http://192.168.0.106:5000";
    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {

        if(retrofit == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
