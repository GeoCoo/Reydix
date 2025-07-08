package com.android.tests.network

import android.content.Context
import android.content.res.AssetManager
import com.android.network.intereceptor.MockApiInterceptor
import com.android.network.model.MockEndpoint
import junit.framework.TestCase.assertEquals
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Test
import org.mockito.Mockito
import java.io.ByteArrayInputStream

class MockApiInterceptorTest {

    @Test
    fun `returns 200 and correct body when mock data exists`() {
        val context = Mockito.mock(Context::class.java)
        val assetManager = Mockito.mock(AssetManager::class.java)
        val chain = Mockito.mock(Interceptor.Chain::class.java)
        val request = Request.Builder().url("https://example.com/api/events").build()
        val mockJson = """{"mock":"data"}"""

        Mockito.`when`(chain.request()).thenReturn(request)
        Mockito.`when`(context.assets).thenReturn(assetManager)
        Mockito.`when`(assetManager.open(MockEndpoint.EVENTS.assetFile))
            .thenReturn(ByteArrayInputStream(mockJson.toByteArray()))

        val interceptor = MockApiInterceptor(context)
        val response = interceptor.intercept(chain)

        assertEquals(200, response.code)
    }
}