package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class Water(
    @SerializedName("water_pump_status")
    val waterPumpStatus: Status
)