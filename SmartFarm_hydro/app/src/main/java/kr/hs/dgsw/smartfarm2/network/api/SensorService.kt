package kr.hs.dgsw.smartfarm2.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.model.response.*
import retrofit2.http.*

interface SensorService {

    @GET("get_all_sensor/")
    fun getAllSensor(): Single<retrofit2.Response<Response<GetAllSensor>>>

    @GET("water/")
    fun getWater(): Single<retrofit2.Response<Response<Water>>>

    @GET("led/")
    fun getLed(): Single<retrofit2.Response<Response<Led>>>

    @GET("fan/")
    fun getFan(): Single<retrofit2.Response<Response<Fan>>>

    @GET("get_all_module_status/")
    fun getAllModule(): Single<retrofit2.Response<Response<Module>>>

    @FormUrlEncoded
    @POST("water/")
    fun controlWaterPump(
        @FieldMap
        status: HashMap<String?, Boolean?>,
        @FieldMap
        device: HashMap<String?, Int?>,
    ): Single<retrofit2.Response<Void>>

    @FormUrlEncoded
    @POST("led/")
    fun controlLed(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>

    @FormUrlEncoded
    @POST("fan/")
    fun controlFan(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>
}