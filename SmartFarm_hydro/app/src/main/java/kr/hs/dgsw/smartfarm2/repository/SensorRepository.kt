package kr.hs.dgsw.smartfarm2.repository

import android.util.Log
import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.Server
import kr.hs.dgsw.smartfarm2.network.Server.sensorApi
import kr.hs.dgsw.smartfarm2.network.model.response.GetAllSensor
import kr.hs.dgsw.smartfarm2.network.model.response.Response
import org.json.JSONObject

class SensorRepository {
    fun getAllSensor(): Single<Response<GetAllSensor>> {
        return sensorApi.getAllSensor().map{
            if (!it.isSuccessful){
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            Log.e("sadf", "${it.body()}")
            it.body()
        }
    }

    fun controlLed(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return sensorApi.controlLed(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }

    fun controlPump(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return sensorApi.controlWaterPump(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }
}