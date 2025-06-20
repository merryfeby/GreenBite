package com.example.greenbite

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.midtrans.sdk.uikit.external.UiKitApi

class App: Application() {
    companion object {
        lateinit var db: AppDatabase
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
//        ).baseUrl("http://192.168.0.170:8000/api/").build()
        ).baseUrl("http://10.0.2.2:8000/api/").build()
//            .baseUrl("http://192.168.1.12:8000/api/").build() tino

        val retrofitService = retrofit.create(WebService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(applicationContext)
        UiKitApi.Builder()
            .withMerchantClientKey("SB-Mid-client-VuELTZ1_OZATDAnJ")
            .withContext(this)
            .withMerchantUrl("http://10.0.2.2:8000/api/midtrans/callback/")
            .enableLog(true)
            .build()
    }
}