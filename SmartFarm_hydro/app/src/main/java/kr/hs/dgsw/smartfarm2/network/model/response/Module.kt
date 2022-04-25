package kr.hs.dgsw.smartfarm2.network.model.response

import com.google.gson.annotations.SerializedName

data class Module(
    @SerializedName("led_status")
    val ledStatus: StatusValue,

    @SerializedName("water_pump_status_1")
    val waterStatus1: StatusValue,

    @SerializedName("water_pump_status_2")
    val waterStatus2: StatusValue,

    @SerializedName("fan_status")
    val fanStatus: StatusValue,
)
