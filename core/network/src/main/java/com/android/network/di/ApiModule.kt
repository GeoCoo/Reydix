package com.android.network.di
import com.android.network.api.ApiClient
import com.android.network.impl.ApiClientImpl
import com.android.network.model.Qualifiers.Mock
import com.android.network.model.Qualifiers.Real
import com.android.network.service.ApiService
import com.android.network.service.MockService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiProvidesModule {

    @Provides
    @Singleton
    fun provideApiService(
        @Real retrofit: Retrofit
    ): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideMockApiService(
        @Mock retrofit: Retrofit
    ): MockService =
        retrofit.create(MockService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiBindsModule {
    @Binds
    @Singleton
    abstract fun bindApiClient(impl: ApiClientImpl): ApiClient
}