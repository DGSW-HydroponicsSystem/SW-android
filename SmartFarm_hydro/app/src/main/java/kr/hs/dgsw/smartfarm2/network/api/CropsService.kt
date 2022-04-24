package kr.hs.dgsw.smartfarm2.network.api

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.model.response.C
import kr.hs.dgsw.smartfarm2.network.model.response.Crop
import kr.hs.dgsw.smartfarm2.network.model.response.Crops
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Query

interface CropsService {
    @Headers("x-www-form-urlencoded")
    @GET("user/crops/all/")
    fun getAllCrops(): Single<retrofit2.Response<Response<C>>>

    @Headers("x-www-form-urlencoded")
    @PUT("user/crops/")
    fun updateCrops(@Query("pk") pk: Int): Single<retrofit2.Response<Response<Any>>>

    @Headers("x-www-form-urlencoded")
    @GET("user/crops/")
    fun getCurrentCrops(): Single<retrofit2.Response<Response<Crop>>>
}