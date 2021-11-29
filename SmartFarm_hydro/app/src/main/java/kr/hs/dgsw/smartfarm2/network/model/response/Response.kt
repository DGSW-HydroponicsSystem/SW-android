package kr.hs.dgsw.smartfarm2.network.model.response

data class Response<T>(
    val status: Int,
    val detail: String,
    val data: T,
)
