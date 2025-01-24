package com.example.rumahhewan.Service

import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.model.Hewan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DokterService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("bacadokter.php")
    suspend fun getDokter(): List<Dokter>

    @GET("bacaiddokter.php")
    suspend fun getDokterById(@Query("id_dokter") iddokter:String): Dokter

    @POST("insertdokter.php")
    suspend fun insertDokter(@Body dokter: Dokter)

    @PUT("editdokter.php")
    suspend fun updateDokter(@Query("id_dokter")iddokter: String, @Body dokter: Dokter)

    @DELETE("deletedokter.php/{id_dokter}")
    suspend fun deleteDokter(@Query("id_dokter")iddokter: String):retrofit2.Response<Void>

}