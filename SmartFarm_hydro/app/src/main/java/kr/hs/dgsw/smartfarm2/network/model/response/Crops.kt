package kr.hs.dgsw.smartfarm2.network.model.response


import com.google.gson.annotations.SerializedName

data class Crops(
    val image: String,
    val name: String,
    val pk: Int
)