package com.example.rumahhewan.ui.view.perawatan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.navigasi.DynamicSelectedTextField
import com.example.rumahhewan.ui.viewmodel.dokter.HomeDokterState
import com.example.rumahhewan.ui.viewmodel.dokter.HomeDokterViewModel
import com.example.rumahhewan.ui.viewmodel.dokter.PenyediaDokterViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.InsertPerawatanEvent
import com.example.rumahhewan.ui.viewmodel.perawatan.InsertPerawatanState
import com.example.rumahhewan.ui.viewmodel.perawatan.InsertPerawatanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.PenyediaPerawatanViewModel
import kotlinx.coroutines.launch

object DestinasiperawatanInsertEntry: DestinasiNavigasi {
    override val route = "perawatan_pwt"
    override val titleRes = "Entry Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPwtScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPerawatanViewModel = viewModel(factory = PenyediaPerawatanViewModel.Factory),
    viewModelHewan: HomeHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory),  // Perbaiki factory
    viewModelDokter: HomeDokterViewModel = viewModel(factory = PenyediaDokterViewModel.Factory)  // Perbaiki factory
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Add your top bar here
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            EntryBody(
                insertPerawatanState = viewModel.perawatanState,
                onSiswaValueChange = viewModel::updateInsertPwtState,
                onSaveClick = {
                    coroutineScope.launch {
                        viewModel.insertPwt()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                viewModelHewan = viewModelHewan,
                viewModelDokter = viewModelDokter
            )
        }
    }
}



@Composable
fun EntryBody(
    insertPerawatanState: InsertPerawatanState,
    onSiswaValueChange: (InsertPerawatanEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModelHewan: HomeHewanViewModel, // ViewModel Hewan
    viewModelDokter: HomeDokterViewModel // ViewModel Dokter
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertPerawatanEvent = insertPerawatanState.insertPerawatanEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelHewan = viewModelHewan,  // Mengirimkan viewModelHewan
            viewModelDokter = viewModelDokter   // Mengirimkan viewModelDokter
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertPerawatanEvent: InsertPerawatanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPerawatanEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelHewan: HomeHewanViewModel, // ViewModel untuk Hewan
    viewModelDokter: HomeDokterViewModel // ViewModel untuk Dokter
) {
    // Ambil state hewan dan dokter dari ViewModel
    val hewanState = viewModelHewan.hwnHewanState
    val dokterState = viewModelDokter.dkrDokterState

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Input untuk ID Perawatan
        OutlinedTextField(
            value = insertPerawatanEvent.idperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(idperawatan = it)) },
            label = { Text("ID Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Dropdown untuk memilih Hewan
        when (hewanState) {
            is HomeHewanState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is HomeHewanState.Error -> Text("Gagal mengambil data hewan", color = MaterialTheme.colorScheme.error)
            is HomeHewanState.Success -> {
                val hewanList = hewanState.hewan
                DynamicSelectedTextField(
                    selectedValue = insertPerawatanEvent.idhewan,
                    options = hewanList.map { it.idhewan },
                    label = "Pilih ID Hewan",
                    onValueChangedEvent = { selectedId -> onValueChange(insertPerawatanEvent.copy(idhewan = selectedId)) }
                )
            }
        }

        // Dropdown untuk memilih Dokter
        when (dokterState) {
            is HomeDokterState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            is HomeDokterState.Error -> Text("Gagal mengambil data dokter", color = MaterialTheme.colorScheme.error)
            is HomeDokterState.Success -> {
                val dokterList = dokterState.dokter
                DynamicSelectedTextField(
                    selectedValue = insertPerawatanEvent.iddokter,
                    options = dokterList.map { it.iddokter },
                    label = "Pilih ID Dokter",
                    onValueChangedEvent = { selectedId -> onValueChange(insertPerawatanEvent.copy(iddokter = selectedId)) }
                )
            }
        }

        // Input untuk Tanggal Perawatan
        OutlinedTextField(
            value = insertPerawatanEvent.tanggalperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(tanggalperawatan = it)) },
            label = { Text("Tanggal Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            placeholder = { Text("YYYY-MM-DD") } // Format tanggal
        )

        // Input untuk Deskripsi Perawatan (opsional)
        OutlinedTextField(
            value = insertPerawatanEvent.detailperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(detailperawatan = it)) },
            label = { Text("Deskripsi Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = false,
            maxLines = 5
        )

        // Tombol Simpan
        Button(
            onClick = { /* Tambahkan logika simpan di sini */ },
            modifier = Modifier.align(Alignment.End),
            enabled = enabled
        ) {
        }
    }
}


