package kr.hs.dgsw.smartfarm2.network.model.response

import com.google.gson.annotations.SerializedName

data class Module(
    @SerializedName("led_status")
    val ledStatus: Status,

    @SerializedName("water_pump_status")
    val waterStatus: Status,

    @SerializedName("fan_status")
    val fanStatus: Status,
)
