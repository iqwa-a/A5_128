package com.example.rumahhewan.repository

import com.example.rumahhewan.Service.HewanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val hewanRepository: HewanRepository
}

class HewanContainer: AppContainer {
    private val baseUrl = "http://192.168.100.67/pama5/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy { retrofit.create(HewanService::class.java) }
    override val hewanRepository: HewanRepository by lazy {
        NetworkHewanRepository(hewanService)
    }
}