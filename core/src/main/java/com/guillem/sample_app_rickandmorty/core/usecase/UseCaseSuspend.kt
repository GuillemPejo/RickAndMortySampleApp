package com.guillem.sample_app_rickandmorty.core.usecase

fun interface UseCaseSuspend<Params, Return> {
    suspend operator fun invoke(params: Params): Return
}
