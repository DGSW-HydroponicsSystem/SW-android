package kr.hs.dgsw.smartfarm2.repository

import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.Server
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import org.json.JSONObject

class MainRepository {
    fun getAllSensor(): Single<GetAllSensor> {
        return Server.retrofit.getSensorAll().map {
            if (!it.isSuccessful){
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.body()
        }
    }
    fun controlLed(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return Server.retrofit.controlLed(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }

    fun controlPump(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return Server.retrofit.controlWaterPump(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }
}