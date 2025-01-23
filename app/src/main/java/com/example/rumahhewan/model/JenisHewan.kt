package com.example.rumahhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Jenis(
        @SerialName("id_jenis_hewan") val idjenishewan: String,  // Menyesuaikan dengan kolom id_hewan di DB
        @SerialName("nama_jenis_hewan") val namajenishewan: String,  // Menyesuaikan dengan kolom nama_hewan
        val deskripsi: String,  // Menyesuaikan dengan kolom pemilik
)