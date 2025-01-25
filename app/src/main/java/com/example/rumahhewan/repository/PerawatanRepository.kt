package com.example.rumahhewan.repository

import com.example.rumahhewan.Service.JenisHewanService
import com.example.rumahhewan.Service.PerawatanService
import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.model.Perawatan
import okio.IOException

interface PerawatanRepository{
    suspend fun getPerawatan(): List<Perawatan>
    suspend fun insertPerawatan(perawatan: Perawatan)
    suspend fun updatePerawatan(idjenishewan: String, perawatan: Perawatan)
    suspend fun deletePerawatan(idjenishewan: String)
    suspend fun getPerawatanById(idjenishewan: String): Perawatan
}

class NetworkPerawatanRepository(private val perawatanApiService: PerawatanService) : PerawatanRepository{
    override suspend fun insertPerawatan(perawatam: Perawatan){
        perawatanApiService.insertPerawatan(perawatam)
    }
    override suspend fun updatePerawatan(idperawatan: String, jenisPerawatan: Perawatan){
        perawatanApiService.updatePerawatan(idperawatan, jenisPerawatan)
    }

    override suspend fun deletePerawatan(idperawatan: String) {
        try {
            val response = perawatanApiService.deletePerawatan(idperawatan)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Perawatan. HTTP Status Code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun getPerawatan(): List<Perawatan> = perawatanApiService.getPerawatan()
    override suspend fun getPerawatanById(idperawatan: String): Perawatan {
        return perawatanApiService.getPerawatanById(idperawatan)
    }
}