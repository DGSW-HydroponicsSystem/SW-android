package kr.hs.dgsw.smartfarm2.repository

import android.util.Log
import io.reactivex.Single
import kr.hs.dgsw.smartfarm2.network.Server.sensorApi
import kr.hs.dgsw.smartfarm2.network.model.response.*
import org.json.JSONObject

class SensorRepository {
    fun getAllSensor(): Single<Response<GetAllSensor>> {
        return sensorApi.getAllSensor().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            Log.e("sadf", "${it.body()}")
            it.body()
        }
    }

    fun getLedState(): Single<Response<Led>> {
        return sensorApi.getLed().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            Log.e("sadf", "${it.body()}")
            it.body()
        }
    }

    fun getWaterState(): Single<Response<Water>> {
        return sensorApi.getWater().map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            Log.e("sadf", "${it.body()}")
            it.body()
        }
    }

    fun getFanState(): Single<Response<Fan>> {
        return sensorApi.getFan().map {
            if (!it.isSuccessful) {
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

    fun controlFan(params: HashMap<String?, Boolean?>): Single<Boolean> {
        return sensorApi.controlFan(params).map {
            if (!it.isSuccessful) {
                val errorBody = JSONObject(it.errorBody().toString())
                throw Throwable(errorBody.getString("message"))
            }
            it.isSuccessful
        }
    }
}