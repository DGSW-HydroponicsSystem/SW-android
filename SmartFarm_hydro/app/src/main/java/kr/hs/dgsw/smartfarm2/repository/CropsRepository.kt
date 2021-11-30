package kr.hs.dgsw.smartfarm2.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.Server
import kr.hs.dgsw.smartfarm2.network.Server.cropsApi
import kr.hs.dgsw.smartfarm2.network.model.response.C
import kr.hs.dgsw.smartfarm2.network.model.response.Crop
import kr.hs.dgsw.smartfarm2.network.model.response.Crops
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import org.json.JSONObject

class CropsRepository {
    fun getAllCrops(): Single<Response<C>> {
        return cropsApi.getAllCrops().map {
            if(!it.isSuccessful){
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun updateCrops(pk: Int): Single<Response<Any>>{
        return cropsApi.updateCrops(pk).map {
            if(!it.isSuccessful){
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }

    fun getCurrentCrops(): Single<Response<Crop>>{
        return cropsApi.getCurrentCrops().map {
            if(!it.isSuccessful){
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
}