package com.example.rumahhewan.ui.view.dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.dokter.DetailDokterViewModel
import com.example.rumahhewan.ui.viewmodel.dokter.PenyediaDokterViewModel
import com.example.rumahhewan.ui.viewmodel.dokter.UpdateDokterEvent
import com.example.rumahhewan.ui.viewmodel.dokter.UpdateDokterState
import com.example.rumahhewan.ui.viewmodel.dokter.UpdateDokterViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanEvent
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateDoktorDetail : DestinasiNavigasi {
    override val route = "detail_dkr"
    override val titleRes = "Detail Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDokterScreen(
    navigateBack: () -> Unit,
    iddokter: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateDokterViewModel = viewModel(factory = PenyediaDokterViewModel.Factory)
) {
    val dokterState = viewModel.dokterState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(iddokter) {
        viewModel.getDokterByIddokter(iddokter)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

        }
    ) { innerPadding ->
        UpdateBody(
            updateDokterState = dokterState,
            onSiswaValueChange = viewModel::updateUpdateDkrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatedkr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}


@Composable
fun UpdateBody(
    updateDokterState: UpdateDokterState,
    onSiswaValueChange: (UpdateDokterEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateDokterEvent = updateDokterState.updateDokterEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    updateDokterEvent: UpdateDokterEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateDokterEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateDokterEvent.namadokter,
            onValueChange = { onValueChange(updateDokterEvent.copy(namadokter = it)) },
            label = { Text("Nama Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateDokterEvent.spesialisasi,
            onValueChange = { onValueChange(updateDokterEvent.copy(spesialisasi = it)) },
            label = { Text("Spesialisasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateDokterEvent.kontak,
            onValueChange = { onValueChange(updateDokterEvent.copy(kontak = it)) },
            label = { Text("Kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

    }
}
