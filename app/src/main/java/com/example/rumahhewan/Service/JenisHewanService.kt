package com.example.rumahhewan.Service

import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Jenis
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface JenisHewanService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("bacajenishewan.php")
    suspend fun getJenisHewan(): List<Jenis>

    @GET("bacaidjenishewan.php")
    suspend fun getJenisHewanById(@Query("id_jenis_hewan") idjenishewan:String): Jenis

    @POST("insertjenishewan.php")
    suspend fun insertJenisHewan(@Body jenisHewan: Jenis)

    @PUT("editjenishewan.php/{id_jenis_hewan}")
    suspend fun updateJenisHewan(@Query("id_jenis_hewan")idjenishewan: String, @Body jenisHewan: Jenis)

    @DELETE("deletejenishewan.php/{id_jenis_hewan}")
    suspend fun deleteJenisHewan(@Query("id_jenis_hewan")idjenishewan: String):retrofit2.Response<Void>

}