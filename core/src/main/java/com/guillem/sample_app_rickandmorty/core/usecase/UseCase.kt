package com.guillem.sample_app_rickandmorty.core.usecase

fun interface UseCase<Params, Return> {
    operator fun invoke(params: Params): Return
}
