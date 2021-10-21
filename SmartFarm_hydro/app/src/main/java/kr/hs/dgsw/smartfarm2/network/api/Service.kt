package kr.hs.dgsw.smartfarm2.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @GET("get_all_sensor/")
    fun getSensorAll(): Single<Response<GetAllSensor>>

    @FormUrlEncoded
    @POST("control_water/")
    fun controlWaterPump(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<Response<Void>>

    @FormUrlEncoded
    @POST("control_led/")
    fun controlLed(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<Response<Void>>
}