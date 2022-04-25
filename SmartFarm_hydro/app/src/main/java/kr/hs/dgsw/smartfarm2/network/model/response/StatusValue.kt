package kr.hs.dgsw.smartfarm2.network.model.response

import com.google.gson.annotations.SerializedName

data class StatusValue(
    @SerializedName("value")
    val value: Boolean,
)
