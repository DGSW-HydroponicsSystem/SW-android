package kr.hs.dgsw.smartfarm2.network

import kr.hs.dgsw.smartfarm2.network.api.CropsService
import kr.hs.dgsw.smartfarm2.network.api.SensorService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Server {
    val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl("${Constants.HOST}/v2/")
        .build()


    val cropsApi: CropsService = retrofit.create(CropsService::class.java)
    val sensorApi: SensorService = retrofit.create(SensorService::class.java)
}