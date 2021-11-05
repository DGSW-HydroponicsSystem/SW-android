package kr.hs.dgsw.smartfarm2.network.model.response

import com.google.gson.annotations.SerializedName

data class GetAllSensor(
    val humidity: Humidity,
    val temp: Temp,
    @SerializedName("led_status")
    val ledStatus: LedStatus,
    @SerializedName("water_status")
    val waterStatus: WaterStatus,
    )
