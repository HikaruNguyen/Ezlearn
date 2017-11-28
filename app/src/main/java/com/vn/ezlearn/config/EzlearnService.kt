package com.vn.ezlearn.config

import android.content.Context
import com.google.gson.GsonBuilder
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.modelresult.*
import com.vn.ezlearn.models.HistoryBuyPackage
import com.vn.ezlearn.models.HistoryExam
import com.vn.ezlearn.models.HistoryPayment
import com.vn.ezlearn.network.RxErrorHandlingCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by FRAMGIA\nguyen.duc.manh on 16/02/2017.
 */

interface EzlearnService {
    @get:GET("index.php?r=category")
    val category: Observable<CategoryResult>

    @get:GET("index.php?r=site/get-banners")
    val banners: Observable<BannerResult>

    @get:GET("index.php?r=user/logout")
    val logout: Observable<BaseResult>

    @GET("index.php?r=subjects/get-list-by-cate")
    fun getListExams(@Query("category_id") category_id: Int,
                     @Query("page") page: Int,
                     @Query("per-page") per_page: Int): Observable<ListExamsResult>

    @GET("index.php?r=subjects/get-list-by-cate")
    fun getListDocument(@Query("category_id") category_id: Int,
                        @Query("page") page: Int,
                        @Query("per-page") per_page: Int): Observable<ListDocumentResult>

    @GET("index.php?r=subjects/get-list-free")
    fun getListFreeExams(@Query("page") page: Int,
                         @Query("per-page") per_page: Int): Observable<ListExamsResult>

    @GET("index.php?r=subjects/get-top")
    fun getListTryExams(@Query("is_free") is_free: Int,
                        @Query("page") page: Int,
                        @Query("per-page") per_page: Int): Observable<ListExamsResult>

    @GET("index.php?r=user/login")
    fun getLogin(@Query("username") username: String,
                 @Query("password") password: String): Observable<LoginResult>

    @FormUrlEncoded
    @POST("index.php?r=user/register")
    fun postRegister(@Field("username") username: String, @Field("password") password: String,
                     @Field("first_name") firstName: String, @Field("last_name") lastName: String):
            Observable<LoginResult>

    @GET("index.php?r=subjects/begin-test")
    fun getContentExam(@Query("id") id: Int): Observable<QuestionResult>

    @GET("index.php?r=user/view")
    fun getUserInfo(): Observable<UserInfoResult>

    @GET("index.php?r=user/history-working")
    fun getHistoryExam(@Query("page") page: Int,
                       @Query("per-page") per_page: Int): Observable<HistoryResult<HistoryExam>>

    @GET("index.php?r=user/history-charging")
    fun getHistoryBuyPackage(@Query("page") page: Int, @Query("per-page") per_page: Int):
            Observable<HistoryResult<HistoryBuyPackage>>

    @GET("index.php?r=user/history-buy-card")
    fun getHistoryPayment(@Query("page") page: Int, @Query("per-page") per_page: Int):
            Observable<HistoryResult<HistoryPayment>>

    object Factory {

        fun create(context: Context): EzlearnService {
            // Create Retrofit Adapter
            val gson = GsonBuilder().serializeNulls().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.ENDPOINT)
                    .client(getOkHttp(context))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                    .build()
            return retrofit.create(EzlearnService::class.java)
        }

        private fun getOkHttp(context: Context): OkHttpClient {
            // Config Log
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            // Create Http Client
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                if (!UserConfig.getInstance(context).token.isEmpty()) {
                    builder.addHeader("Authorization", "Bearer "
                            + UserConfig.getInstance(context).token)
                }
                chain.proceed(builder.build())
                        .newBuilder()
                        .build()
            }
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
            httpClient.readTimeout(10, TimeUnit.SECONDS)
            httpClient.writeTimeout(10, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(logging)
            }

            // Config Http Cache
            val cacheSize = 10 * 1024 * 1024 // 10 MiB
            val cache = Cache(context.cacheDir, cacheSize.toLong())
            httpClient.cache(cache)

            return httpClient.build()
        }
    }
}
