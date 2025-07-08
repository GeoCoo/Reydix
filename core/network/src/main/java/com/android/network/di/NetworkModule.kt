package com.android.network.di

import android.content.Context
import com.android.network.intereceptor.MockApiInterceptor
import com.android.network.model.Qualifiers.Mock
import com.android.network.model.Qualifiers.Real
import com.android.network.service.baseUrl
import com.android.network.service.mockBaseUrl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Provides
    @Singleton
    @Real
    fun provideRealOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideMockApiInterceptor(
        @ApplicationContext context: Context,
    ): MockApiInterceptor = MockApiInterceptor(context)

    @Provides
    @Singleton
    @Mock
    fun provideMockOkHttpClient(
        mockApiInterceptor: MockApiInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(mockApiInterceptor)
            .build()

    private fun createRetrofit(
        baseUrl: String,
        client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

    @Provides
    @Singleton
    @Real
    fun provideRealRetrofit(
        @Real client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        createRetrofit(baseUrl, client, converterFactory)

    @Provides
    @Singleton
    @Mock
    fun provideMockRetrofit(
        @Mock client: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        createRetrofit(mockBaseUrl, client, converterFactory)
}