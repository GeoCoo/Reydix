package com.android.common.model

import com.google.gson.Gson
import retrofit2.Response


data class NetworkError(
    val message: String
)

fun <T> parseError(response: Response<T>): CommonError =
    Gson().fromJson(
        response.errorBody()?.string(),
        NetworkError::class.java
    ).toCommonError()

fun NetworkError.toCommonError(): CommonError {
    return CommonError(this.message)
}
