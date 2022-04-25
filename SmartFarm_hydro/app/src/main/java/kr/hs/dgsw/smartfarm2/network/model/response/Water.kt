package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class Water(
    @SerializedName("water_pump_status_1")
    val waterPumpStatus1: StatusValue,

    @SerializedName("water_pump_status_2")
    val waterPumpStatus2: StatusValue,
)