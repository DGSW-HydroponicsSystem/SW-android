package kr.hs.dgsw.smartfarm2.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.model.response.*
import retrofit2.http.*

interface SensorService {

    @Headers("x-www-form-urlencoded")
    @GET("get_all_sensor/")
    fun getAllSensor(): Single<retrofit2.Response<Response<GetAllSensor>>>

    @Headers("x-www-form-urlencoded")
    @GET("water/")
    fun getWater(): Single<retrofit2.Response<Response<Water>>>

    @Headers("x-www-form-urlencoded")
    @GET("led/")
    fun getLed(): Single<retrofit2.Response<Response<Led>>>

    @Headers("x-www-form-urlencoded")
    @GET("fan/")
    fun getFan(): Single<retrofit2.Response<Response<Fan>>>

    @Headers("x-www-form-urlencoded")
    @GET("get_all_module_status")
    fun getAllStatus(): Single<retrofit2.Response<Response<Module>>>

    @Headers("x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("water/")
    fun controlWaterPump(
        @FieldMap
        status: HashMap<String?, Boolean?>,
        @FieldMap
        device: HashMap<String?, Int?>,
    ): Single<retrofit2.Response<Void>>

    @Headers("x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("led/")
    fun controlLed(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>

    @Headers("x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("fan/")
    fun controlFan(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>
}