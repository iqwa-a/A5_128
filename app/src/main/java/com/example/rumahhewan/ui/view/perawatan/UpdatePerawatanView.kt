package com.example.rumahhewan.ui.view.perawatan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanEvent
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.PenyediaPerawatanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.UpdatePerawatanEvent
import com.example.rumahhewan.ui.viewmodel.perawatan.UpdatePerawatanState
import com.example.rumahhewan.ui.viewmodel.perawatan.UpdatePerawatanViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePerawatan : DestinasiNavigasi {
    override val route = "update_pwt/{id_perawatan}"
    override val titleRes = "Update Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePerawatanScreen(
    navigateBack: () -> Unit,
    idperawatan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePerawatanViewModel = viewModel(factory = PenyediaPerawatanViewModel.Factory)
) {
    val perawatanState = viewModel.perawatanState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idperawatan) {
        viewModel.getPerawatanByIdperawatan(idperawatan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

        }
    ) { innerPadding ->
        UpdateBody(
            updatePerawatanState = perawatanState,
            onSiswaValueChange = viewModel::updateUpdatePwtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePwt()
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
    updatePerawatanState: UpdatePerawatanState,
    onSiswaValueChange: (UpdatePerawatanEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updatePerawatanEvent = updatePerawatanState.updatePerawatanEvent,
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
    updatePerawatanEvent: UpdatePerawatanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePerawatanEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePerawatanEvent.idperawatan,
            onValueChange = { onValueChange(updatePerawatanEvent.copy(idperawatan = it)) },
            label = { Text("ID Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePerawatanEvent.idhewan,
            onValueChange = { onValueChange(updatePerawatanEvent.copy(idhewan = it)) },
            label = { Text("ID Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePerawatanEvent.iddokter,
            onValueChange = { onValueChange(updatePerawatanEvent.copy(iddokter = it)) },
            label = { Text("ID Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePerawatanEvent.tanggalperawatan,
            onValueChange = { onValueChange(updatePerawatanEvent.copy(tanggalperawatan = it)) },
            label = { Text("Tanggal Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePerawatanEvent.detailperawatan,
            onValueChange = { onValueChange(updatePerawatanEvent.copy(detailperawatan = it)) },
            label = { Text("Detail Perawatan") },
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
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}