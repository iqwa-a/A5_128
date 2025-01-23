package com.example.rumahhewan.repository

import com.example.rumahhewan.Service.DokterService
import com.example.rumahhewan.Service.HewanService
import com.example.rumahhewan.Service.JenisHewanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val hewanRepository: HewanRepository
    val jenishewanRepository: JenisHewanRepository
    val dokterRepository: DokterRepository
}

class HewanContainer: AppContainer {
    private val baseUrl = "http://10.0.2.2/pama5/"

    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy { retrofit.create(HewanService::class.java) }
    override val hewanRepository: HewanRepository by lazy {
        NetworkHewanRepository(hewanService)
    }

    private val jenishewanService: JenisHewanService by lazy { retrofit.create(JenisHewanService::class.java) }
    override val jenishewanRepository: JenisHewanRepository by lazy {
        NetworkJenisHewanRepository(jenishewanService)}

    private val dokterService: DokterService by lazy { retrofit.create(DokterService::class.java) }
    override val dokterRepository: DokterRepository by lazy {
        NetworkDokterRepository(dokterService)}
}