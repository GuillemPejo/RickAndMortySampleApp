package com.guillem.sample_app_rickandmorty.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.guillem.sample_app_rickandmorty.data.local.AppDatabase
import com.guillem.sample_app_rickandmorty.data.remote.ApiEndpoints
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediatorTest{

    private lateinit var apiEndpoints: ApiEndpoints
    private lateinit var appDatabase: AppDatabase
    private lateinit var remoteMediator: CharactersRemoteMediator

    @Before
    fun setUp() {
        apiEndpoints = mockk()
        appDatabase = mockk(relaxed = true)
        remoteMediator = CharactersRemoteMediator(apiEndpoints, appDatabase, "Rick")
    }

    @Test
    fun `RemoteMediator initialize returns correct action`() = runBlocking {
        val result = remoteMediator.initialize()
        assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
    }
}