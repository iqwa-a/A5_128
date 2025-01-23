package com.example.rumahhewan.repository

import com.example.rumahhewan.Service.HewanService
import com.example.rumahhewan.Service.JenisHewanService
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Jenis
import okio.IOException

interface JenisHewanRepository{
    suspend fun getJenis(): List<Jenis>
    suspend fun insertJenis(jenisHewan: Jenis)
    suspend fun updateJenis(idjenishewan: String, jenisHewan: Jenis)
    suspend fun deleteJenis(idjenishewan: String)
    suspend fun getJenisById(idjenishewan: String): Jenis
}

class NetworkJenisHewanRepository(private val jenishewanApiService: JenisHewanService) : JenisHewanRepository{
    override suspend fun insertJenis(jenisHewan: Jenis){
        jenishewanApiService.insertJenisHewan(jenisHewan)
    }
    override suspend fun updateJenis(idjenishewan: String, jenisHewan: Jenis){
        jenishewanApiService.updateJenisHewan(idjenishewan, jenisHewan)
    }

    override suspend fun deleteJenis(idjenishewan: String) {
        try {
            val response = jenishewanApiService.deleteJenisHewan(idjenishewan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete JenisHewan. HTTP Status Code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun getJenis(): List<Jenis> = jenishewanApiService.getJenisHewan()
    override suspend fun getJenisById(idjenishewan: String): Jenis {
        return jenishewanApiService.getJenisHewanById(idjenishewan)
    }
}