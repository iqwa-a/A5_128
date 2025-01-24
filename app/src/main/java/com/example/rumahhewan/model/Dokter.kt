package com.example.rumahhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Dokter (
    @SerialName("id_dokter") val iddokter: String,  // Menyesuaikan dengan kolom tanggal_lahir
    @SerialName("nama_dokter") val namadokter: String,  // Menyesuaikan dengan kolom tanggal_lahir
    val spesialisasi: String,  // Menyesuaikan dengan kolom pemilik
    val kontak: String,  // Menyesuaikan dengan kolom pemilik

)