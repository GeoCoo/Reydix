package com.android.network.intereceptor

import android.content.Context
import com.android.common.helpers.readAssetFile
import com.android.network.model.MockEndpoint
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class MockApiInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath

        val endpoint = MockEndpoint.fromPath(path)

        val responseString = endpoint
            ?.let { context.readAssetFile(it.assetFile) }
            ?: "{}"

        return Response.Builder()
            .code(200)
            .message("OK")
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .body(
                responseString
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}