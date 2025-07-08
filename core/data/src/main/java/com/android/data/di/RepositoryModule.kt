package com.android.data.di

import com.android.data.api.PokemonsRepository
import com.android.data.impl.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonsRepository(impl: PokemonRepositoryImpl): PokemonsRepository

}