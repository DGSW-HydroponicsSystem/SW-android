package kr.hs.dgsw.smartfarm2.network.model.response

import com.google.gson.annotations.SerializedName

data class Crop(
    @SerializedName("image_url")
    val imageUrl: String,
    val name: String,
    val pk: Int,
    val content: String
)
