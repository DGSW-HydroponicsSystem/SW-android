package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class Led(
    @SerializedName("led_status")
    val ledStatus: Status
)