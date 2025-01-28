package com.example.rumahhewan.ui.navigasi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rumahhewan.ui.screen.Destinasiawal
import com.example.rumahhewan.ui.screen.TampilanAwal
import com.example.rumahhewan.ui.view.dokter.DestinasiDokterDetail
import com.example.rumahhewan.ui.view.dokter.DestinasiDokterHome
import com.example.rumahhewan.ui.view.dokter.DestinasidokterInsertEntry
import com.example.rumahhewan.ui.view.dokter.DetailDokterScreen
import com.example.rumahhewan.ui.view.dokter.EntryDkrScreen
import com.example.rumahhewan.ui.view.dokter.HomedokterScreen
import com.example.rumahhewan.ui.view.dokter.UpdateDokterScreen
import com.example.rumahhewan.ui.view.hewan.DestinasiHewanDetail
import com.example.rumahhewan.ui.view.hewan.DestinasiHewanHome
import com.example.rumahhewan.ui.view.hewan.DestinasiUpdateHewan
import com.example.rumahhewan.ui.view.hewan.DestinasihewanInsertEntry
import com.example.rumahhewan.ui.view.hewan.DetailHewanScreen
import com.example.rumahhewan.ui.view.hewan.EntryHwnScreen
import com.example.rumahhewan.ui.view.hewan.HomehewanScreen
import com.example.rumahhewan.ui.view.hewan.UpdateHewanScreen
import com.example.rumahhewan.ui.view.jenishewan.DestinasiInsertjenisEntry
import com.example.rumahhewan.ui.view.jenishewan.DestinasiJenisDetail
import com.example.rumahhewan.ui.view.jenishewan.DestinasiJenisHome
import com.example.rumahhewan.ui.view.jenishewan.DestinasiUpdatejenisEntry
import com.example.rumahhewan.ui.view.jenishewan.DetailJenisScreen
import com.example.rumahhewan.ui.view.jenishewan.EntryJnsScreen
import com.example.rumahhewan.ui.view.jenishewan.HomeJenisScreen
import com.example.rumahhewan.ui.view.jenishewan.UpdateJenisHewanScreen
import com.example.rumahhewan.ui.view.perawatan.DestinasiPerawatanDetail
import com.example.rumahhewan.ui.view.perawatan.DestinasiPerawatanHome
import com.example.rumahhewan.ui.view.perawatan.DestinasiUpdatePerawatan
import com.example.rumahhewan.ui.view.perawatan.DestinasiperawatanInsertEntry
import com.example.rumahhewan.ui.view.perawatan.DetailPerawatanScreen
import com.example.rumahhewan.ui.view.perawatan.EntryPwtScreen
import com.example.rumahhewan.ui.view.perawatan.HomeperawatanScreen
import com.example.rumahhewan.ui.view.perawatan.UpdatePerawatanScreen


@Composable
fun PengelolaHalamanHewan(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Destinasiawal.route,
        modifier = Modifier
    ) {
        // Halaman Awal
        composable(Destinasiawal.route) {
            TampilanAwal(
                onpasienButton = { navController.navigate(DestinasiHewanHome.route) },
                onjenishewanButton = { navController.navigate(DestinasiJenisHome.route) },
                ondokterButton = { navController.navigate(DestinasiDokterHome.route) },
                onperawatanButton = { navController.navigate(DestinasiPerawatanHome.route) }
            )
        }

        // ===========================================
        // Bagian Hewan
        // ===========================================

        // Halaman Home Hewan
        composable(DestinasiHewanHome.route) {
            HomehewanScreen(
                navigateToItemEntry = { navController.navigate(DestinasihewanInsertEntry.route) },
                onDetailClick = { id_hewan ->
                    navController.navigate("${DestinasiHewanDetail.route}/$id_hewan")
                }
            )
        }

        // Halaman Detail Hewan
        composable(
            route = "${DestinasiHewanDetail.route}/{id_hewan}",
            arguments = listOf(navArgument("id_hewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_hewan = backStackEntry.arguments?.getString("id_hewan") ?: return@composable
            DetailHewanScreen(
                idhewan = id_hewan,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }

        // Halaman Insert Hewan
        composable(DestinasihewanInsertEntry.route) {
            EntryHwnScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        // Halaman Update Hewan
        composable(
            route = DestinasiUpdateHewan.route,
            arguments = listOf(navArgument("id_hewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idhewan = backStackEntry.arguments?.getString("id_hewan") ?: return@composable
            UpdateHewanScreen(
                idhewan = idhewan,
                navigateBack = { navController.navigateUp() }
            )
        }


        // ===========================================
        // Bagian Jenis Hewan
        // ===========================================

        // Halaman Home Jenis Hewan
        composable(DestinasiJenisHome.route) {
            HomeJenisScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertjenisEntry.route) },
                onDetailClick = { idjenishewan ->
                    navController.navigate("${DestinasiJenisDetail.route}/$idjenishewan")
                }
            )
        }

        // Halaman Detail Jenis Hewan
        composable(
            route = "${DestinasiJenisDetail.route}/{id_jenis_hewan}",
            arguments = listOf(navArgument("id_jenis_hewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idjenishewan = backStackEntry.arguments?.getString("id_jenis_hewan") ?: return@composable
            DetailJenisScreen(
                idjenishewan = idjenishewan,
                navigateBack = { navController.navigateUp() },
                navController = navController
            )
        }

        // Halaman Insert Jenis Hewan
        composable(DestinasiInsertjenisEntry.route) {
            EntryJnsScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        // Halaman Update Jenis Hewan
        composable(
            route = DestinasiUpdatejenisEntry.route,
            arguments = listOf(navArgument("id_jenis_hewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idjenishewan = backStackEntry.arguments?.getString("id_jenis_hewan") ?: return@composable
            UpdateJenisHewanScreen(
                idjenishewan = idjenishewan,
                navigateBack = { navController.navigateUp() }
            )
        }


        // ===========================================
        // Bagian Dokter
        // ===========================================

        // Halaman Home Dokter
        composable(DestinasiDokterHome.route) {
            HomedokterScreen(
                navigateToItemEntry = { navController.navigate(DestinasidokterInsertEntry.route) },
                onDetailClick = { id_dokter ->
                    navController.navigate("${DestinasiDokterDetail.route}/$id_dokter")
                }
            )
        }

        // Halaman Detail Dokter
        composable(
            route = "${DestinasiDokterDetail.route}/{id_dokter}",
            arguments = listOf(navArgument("id_dokter") { type = NavType.StringType })
        ) { backStackEntry ->
            val iddokter = backStackEntry.arguments?.getString("id_dokter").orEmpty()
            if (iddokter.isNotBlank()) {
                DetailDokterScreen(
                    iddokter = iddokter,
                    navigateBack = { navController.navigateUp() },
                    navController = navController
                )
            } else {
                // Handle missing or invalid ID
                Text("Invalid Dokter ID", modifier = Modifier.fillMaxSize())
            }
        }

        // Halaman Insert Dokter
        composable(DestinasidokterInsertEntry.route) {
            EntryDkrScreen(
                navigateBack = { navController.navigateUp() }
            )
        }

        // Halaman Update Dokter
        composable(
            route = "update_dkr/{id_dokter}",
            arguments = listOf(navArgument("id_dokter") { type = NavType.StringType })
        ) { backStackEntry ->
            val id_dokter = backStackEntry.arguments?.getString("id_dokter").orEmpty()
            if (id_dokter.isNotBlank()) {
                UpdateDokterScreen(
                    iddokter = id_dokter,
                    navigateBack = { navController.navigateUp() }
                )
            } else {
                // Handle missing or invalid ID
                Text("Invalid Dokter ID", modifier = Modifier.fillMaxSize())
            }
        }
        // ===========================================
        // Bagian Perawawt
        // ===========================================

        // Halaman Home perawatan
        composable(DestinasiPerawatanHome.route) {
            HomeperawatanScreen(
                navigateToItemEntry = { navController.navigate(DestinasiperawatanInsertEntry.route) },
                onDetailClick = { id_perawatan ->
                    navController.navigate("${DestinasiPerawatanDetail.route}/$id_perawatan")
                }
            )

        }
        // Detail Perawatan
        composable("${DestinasiPerawatanDetail.route}/{id_perawatan}") { backStackEntry ->
            val idPerawatan = backStackEntry.arguments?.getString("id_perawatan") ?: ""
            DetailPerawatanScreen(
                navigateBack = { navController.popBackStack() },
                idperawatan = idPerawatan,
                navController = navController
            )
        }

// Insert Perawatan
        composable(DestinasiperawatanInsertEntry.route) {
            EntryPwtScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

// Update Perawatan
        composable(DestinasiUpdatePerawatan.route) { backStackEntry ->
            val idPerawatan = backStackEntry.arguments?.getString("id_perawatan") ?: ""
            UpdatePerawatanScreen(
                navigateBack = { navController.popBackStack() },
                idperawatan = idPerawatan
            )
        }

    }
}
