package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class GetAllSensor(
    @SerializedName("humidity_1")
    val humidity1: Value,
    @SerializedName("humidity_2")
    val humidity2: Value,
    @SerializedName("temp_1")
    val temp1: Value,
    @SerializedName("temp_2")
    val temp2: Value,
    @SerializedName("sunlight_1")
    val sunlight1: StatusInt,
    @SerializedName("sunlight_2")
    val sunlight2: StatusInt,
    @SerializedName("water_temp_1")
    val waterTemp1: StatusInt,
    @SerializedName("water_temp_2")
    val waterTemp2: StatusInt,
    @SerializedName("water_level")
    val waterLevel: StatusInt,
    @SerializedName("water_ph")
    val waterPh: StatusFloat
)