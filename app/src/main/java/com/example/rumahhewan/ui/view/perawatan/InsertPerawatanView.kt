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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
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
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPerawatanViewModel = viewModel(factory = PenyediaPerawatanViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { innerpadding->
        EntryBody(
            insertPerawatanState = viewModel.perawatanState,
            onSiswaValueChange = viewModel::updateInsertPwtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPwt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerpadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertPerawatanState: InsertPerawatanState,
    onSiswaValueChange: (InsertPerawatanEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertPerawatanEvent = insertPerawatanState.insertPerawatanEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertPerawatanEvent: InsertPerawatanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPerawatanEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPerawatanEvent.idperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(idperawatan = it)) },
            label = { Text("ID Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPerawatanEvent.idhewan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(idhewan = it)) },
            label = { Text("ID Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPerawatanEvent.iddokter,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(iddokter = it)) },
            label = { Text("ID Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPerawatanEvent.tanggalperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(tanggalperawatan = it)) },
            label = { Text("Tanggal Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPerawatanEvent.detailperawatan,
            onValueChange = { onValueChange(insertPerawatanEvent.copy(detailperawatan = it)) },
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


