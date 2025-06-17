package com.example.greenbite

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class App: Application() {
    companion object {
        lateinit var db: AppDatabase
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).baseUrl("http://192.168.1.10:8000/api/").build()
//            .baseUrl("http://10.0.2.2:8000/api/").build()
//            .baseUrl("http://192.168.1.12:8000/api/").build() tino

        val retrofitService = retrofit.create(WebService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(applicationContext)
    }
}