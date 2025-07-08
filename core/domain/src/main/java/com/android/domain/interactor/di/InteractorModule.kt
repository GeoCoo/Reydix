package com.android.domain.interactor.di

import com.android.domain.interactor.api.PokemonInteractor
import com.android.domain.interactor.impl.PokemonInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorModule {

    @Binds
    abstract fun bindPokemonsInteractor(impl: PokemonInteractorImpl): PokemonInteractor

}