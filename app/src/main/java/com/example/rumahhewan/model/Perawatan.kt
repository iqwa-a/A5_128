package com.example.rumahhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Perawatan (
    @SerialName("id_perawatan") val idperawatan: String,  // Menyesuaikan dengan kolom id_hewan di DB
    @SerialName("id_hewan") val idhewan: String,  // Menyesuaikan dengan kolom id_hewan di DB
    @SerialName("id_dokter") val iddokter: String,  // Menyesuaikan dengan kolom nama_hewan
    @SerialName("tanggal_perawatan") val tanggalperawatan: String,  // Menyesuaikan dengan kolom jenis_hewan_id
    @SerialName("detail_perawatan") val detailperawatan: String,  // Menyesuaikan dengan kolom kontak_pemilik
)