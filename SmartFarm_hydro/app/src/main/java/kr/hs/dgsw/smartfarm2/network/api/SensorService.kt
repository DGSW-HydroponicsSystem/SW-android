package kr.hs.dgsw.smartfarm2.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SensorService {

    @GET("get_all_sensor/")
    fun getAllSensor(): Single<retrofit2.Response<Response<GetAllSensor>>>

    @FormUrlEncoded
    @POST("control_water/")
    fun controlWaterPump(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>

    @FormUrlEncoded
    @POST("control_led/")
    fun controlLed(
        @FieldMap
        params: HashMap<String?, Boolean?>,
    ): Single<retrofit2.Response<Void>>
}