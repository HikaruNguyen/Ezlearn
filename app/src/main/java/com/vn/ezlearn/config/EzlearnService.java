package com.vn.ezlearn.config;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.ezlearn.BuildConfig;
import com.vn.ezlearn.modelresult.BannerResult;
import com.vn.ezlearn.modelresult.BaseResult;
import com.vn.ezlearn.modelresult.CategoryResult;
import com.vn.ezlearn.modelresult.ExamsResult;
import com.vn.ezlearn.modelresult.LoginResult;
import com.vn.ezlearn.network.NullOnEmptyConverterFactory;
import com.vn.ezlearn.network.RxErrorHandlingCallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 16/02/2017.
 */

public interface EzlearnService {
    @GET("index.php?r=category")
    Observable<CategoryResult> getCategory();

    @GET("index.php?r=subjects/get-list-by-cate")
    Observable<ExamsResult> getListExams(@Query("category_id") int category_id,
                                         @Query("page") int page,
                                         @Query("per-page") int per_page);

    @GET("index.php?r=subjects/get-list-free")
    Observable<ExamsResult> getListFreeExams(@Query("page") int page,
                                             @Query("per-page") int per_page);

    @GET("index.php?r=site/get-banners")
    Observable<BannerResult> getBanners();

    @GET("index.php?r=user/login")
    Observable<LoginResult> getLogin(@Query("username") String username,
                                     @Query("password") String password);

    @GET("index.php?r=user/logout")
    Observable<BaseResult> getLogout();

    class Factory {

        public static EzlearnService create(Context context) {
            // Create Retrofit Adapter
            Gson gson = new GsonBuilder().serializeNulls().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.ENDPOINT)
                    .client(getOkHttp(context))
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .build();
            return retrofit.create(EzlearnService.class);
        }

        static OkHttpClient getOkHttp(final Context context) {
            // Config Log
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create Http Client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    if (AppConfig.getInstance(context).getToken() != null
                            && !AppConfig.getInstance(context).getToken().isEmpty()) {
                        builder.addHeader("Authorization", "Bearer "
                                + AppConfig.getInstance(context).getToken());
                    }
                    return chain.proceed(builder.build())
                            .newBuilder()
//                        .addHeader("Cache-Control"
//                                ,String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
                            .build();
                }
            });
//            httpClient.addInterceptor(chain -> {
//                Request request = chain.request().newBuilder()
////                        .addHeader("Accept", "application/json")
////                        .addHeader("X-Math-Api-Key", APIConfig.X_Math_Api_Key)
//                        .build();
//                return chain.proceed(request)
//                        .newBuilder()
////                        .addHeader("Cache-Control"
////                                ,String.format("max-age=%d, only-if-cached, max-stale=%d", 120, 0))
//                        .build();
//            });
            httpClient.connectTimeout(10, TimeUnit.SECONDS);
            httpClient.readTimeout(10, TimeUnit.SECONDS);
            httpClient.writeTimeout(10, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(logging);
            }

            // Config Http Cache
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            httpClient.cache(cache);

            return httpClient.build();
        }
    }
}
