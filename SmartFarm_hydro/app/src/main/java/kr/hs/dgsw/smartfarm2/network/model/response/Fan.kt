package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class Fan(
    @SerializedName("fan_status")
    val fanStatus: StatusValue
)