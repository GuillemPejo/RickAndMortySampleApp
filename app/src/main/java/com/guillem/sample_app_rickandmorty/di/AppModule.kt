package com.guillem.sample_app_rickandmorty.di

import com.guillem.sample_app_rickandmorty.core.utils.Cancellable
import com.guillem.sample_app_rickandmorty.data.repository.CharactersRepositoryImpl
import com.guillem.sample_app_rickandmorty.domain.repository.CharactersRepository
import com.guillem.sample_app_rickandmorty.domain.usecase.GetCharactersUseCase
import com.guillem.sample_app_rickandmorty.domain.usecase.GetCharactersUseCaseImpl
import com.guillem.sample_app_rickandmorty.ui.detail.DetailScreenDestination
import com.guillem.sample_app_rickandmorty.ui.detail.DetailScreenViewModel
import com.guillem.sample_app_rickandmorty.ui.home.HomeScreenDestination
import com.guillem.sample_app_rickandmorty.ui.home.HomeScreenViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    scope<HomeScreenDestination> {
        scopedOf(::HomeScreenViewModel) bind Cancellable::class
    }

    scope<DetailScreenDestination> {
        scopedOf(::DetailScreenViewModel) bind Cancellable::class
    }

    factoryOf(::GetCharactersUseCaseImpl) bind GetCharactersUseCase::class
    factoryOf(::CharactersRepositoryImpl) bind CharactersRepository::class

}
