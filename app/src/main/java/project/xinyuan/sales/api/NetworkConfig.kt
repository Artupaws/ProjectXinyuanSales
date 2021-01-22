package project.xinyuan.sales.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.xinyuan.sales.helper.Constants
import project.xinyuan.sales.helper.SharedPreferencesHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkConfig {

    private lateinit var sharedPref:SharedPreferencesHelper

    private fun getInterceptor():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }

    fun getConnectionXinyuanBearer(context: Context):NetworkService{
        sharedPref = SharedPreferencesHelper(context)
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_XINYUAN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${sharedPref.getValueString(Constants.TOKEN_LOGIN)}")
                            .addHeader("Accept", "application/json")
                            .addHeader("apikey", Constants.API_KEY)
                            .build()
                    chain.proceed(request)
                }.build())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getConnectionXinyuan():NetworkService{
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_XINYUAN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("apikey", Constants.API_KEY)
                            .build()
                    chain.proceed(request)
                }.build())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

}