package com.example.rumahhewan.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rumahhewan.ui.screen.TampilanAwal
import com.example.rumahhewan.ui.screen.halamanutama
import com.example.rumahhewan.ui.view.hewan.DestinasiDetail
import com.example.rumahhewan.ui.view.hewan.DestinasiEntry
import com.example.rumahhewan.ui.view.hewan.DestinasiHome
import com.example.rumahhewan.ui.view.hewan.DetailHewanScreen
import com.example.rumahhewan.ui.view.hewan.EntryHwnScreen
import com.example.rumahhewan.ui.view.hewan.HomeScreen
import com.example.rumahhewan.ui.view.hewan.UpdateHewanScreen

@Composable
fun PengelolaHalamanHewan(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = halamanutama.route,
        modifier = Modifier
    ) {
        // Halaman Awal
        composable(halamanutama.route) {
            TampilanAwal(onMulaiButton = {
                navController.navigate(DestinasiHome.route)
            })
        }

        // Halaman Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { idhewan ->
                    navController.navigate("${DestinasiDetail.route}/$idhewan")
                }
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
            })
        }

        // Halaman Detail Hewan
        composable(
            route = "${DestinasiDetail.route}/{id_hewan}",
            arguments = listOf(navArgument("id_hewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idhewan = backStackEntry.arguments?.getString("id_hewan") ?: return@composable
            DetailHewanScreen(
                idhewan = idhewan,
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
    }
}
