package com.vn.ezlearn.config

import android.content.Context
import com.google.gson.GsonBuilder
import com.vn.ezlearn.BuildConfig
import com.vn.ezlearn.modelresult.*
import com.vn.ezlearn.models.*
import com.vn.ezlearn.network.RxErrorHandlingCallAdapterFactory
import com.vn.ezlearn.nganluong.bank.modelResult.CheckOrderResult
import com.vn.ezlearn.nganluong.bank.modelResult.SendOrderResult
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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
    val category: Observable<BaseResult<Category>>

    @get:GET("index.php?r=site/get-banners")
    val banners: Observable<BannerResult>

    @get:GET("index.php?r=user/logout")
    val logout: Observable<CommonResult>

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
                       @Query("per-page") per_page: Int): Observable<BaseListResult<HistoryExam>>

    @GET("index.php?r=user/history-charging")
    fun getHistoryBuyPackage(@Query("page") page: Int, @Query("per-page") per_page: Int):
            Observable<BaseListResult<HistoryBuyPackage>>

    @GET("index.php?r=user/history-buy-card")
    fun getHistoryPayment(@Query("page") page: Int, @Query("per-page") per_page: Int):
            Observable<BaseListResult<HistoryPayment>>

    @GET("index.php?r=site/get-list-package")
    fun getListPackage(): Observable<BaseListResult<Package>>

    @FormUrlEncoded
    @POST("https://www.nganluong.vn/mobile_card.api.post.v2.php")
    fun paymentByScartchCard(@Field("Func") Func: String,
                             @Field("version") version: String,
                             @Field("merchant_id") merchant_id: String,
                             @Field("merchant_account") merchant_account: String,
                             @Field("merchant_password") merchant_password: String,
                             @Field("pin_card") pin_card: String,
                             @Field("card_serial") card_serial: String,
                             @Field("type_card") type_card: String,
                             @Field("ref_code") ref_code: String,
                             @Field("client_fullname") client_fullname: String,
                             @Field("client_email") client_email: String,
                             @Field("client_mobile") client_mobile: String): Observable<ResponseBody>

    @FormUrlEncoded
    @POST("https://www.nganluong.vn/mobile_checkout_api_post.php")
    fun sendOderRequest(@Field("func") Func: String,
                        @Field("version") version: String,
                        @Field("merchant_id") merchant_id: String,
                        @Field("merchant_account") merchant_account: String,
                        @Field("order_code") order_code: String,
                        @Field("total_amount") total_amount: String,
                        @Field("currency") currency: String,
                        @Field("language") language: String,
                        @Field("return_url") return_url: String,
                        @Field("cancel_url") cancel_url: String,
                        @Field("notify_url") notify_url: String,
                        @Field("buyer_fullname") buyer_fullname: String,
                        @Field("buyer_email") buyer_email: String,
                        @Field("buyer_mobile") buyer_mobile: String,
                        @Field("buyer_address") buyer_address: String,
                        @Field("checksum") checksum: String): Observable<SendOrderResult>

    @FormUrlEncoded
    @POST("https://www.nganluong.vn/mobile_checkout_api_post.php")
    fun checkOderRequest(@Field("func") Func: String,
                        @Field("version") version: String,
                        @Field("merchant_id") merchant_id: String,
                        @Field("token_code") token_code: String,
                        @Field("checksum") checksum: String): Observable<CheckOrderResult>

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

            with(httpClient) {
                addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    if (UserConfig.getInstance(context).isLogined()) {
                        builder.addHeader("Authorization", "Bearer "
                                + UserConfig.getInstance(context).token)
                    }
                    chain.proceed(builder.build())
                            .newBuilder()
                            .build()
                }
                connectTimeout(10, TimeUnit.SECONDS)
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    addInterceptor(logging)
                }
                // Config Http Cache
                val cacheSize = 10 * 1024 * 1024 // 10 MiB
                val cache = Cache(context.cacheDir, cacheSize.toLong())
                cache(cache)
            }
            return httpClient.build()
        }
    }
}
