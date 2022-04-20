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
    @SerializedName("led_status")
    val ledStatus: Status,
    @SerializedName("water_pump_status")
    val waterPumpStatus: Status,
    @SerializedName("sunlight")
    val sunlight: StatusInt,
    @SerializedName("water_temp")
    val waterTemp: StatusInt,
    @SerializedName("water_level")
    val waterLevel: StatusInt,
    @SerializedName("water_ph")
    val waterPh: StatusFloat
)