package com.example.rumahhewan.ui.view.dokter

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
import com.example.rumahhewan.ui.viewmodel.dokter.InsertDokterEvent
import com.example.rumahhewan.ui.viewmodel.dokter.InsertDokterState
import com.example.rumahhewan.ui.viewmodel.dokter.InsertDokterViewModel
import com.example.rumahhewan.ui.viewmodel.dokter.PenyediaDokterViewModel
import kotlinx.coroutines.launch

object DestinasidokterInsertEntry: DestinasiNavigasi {
    override val route = "item_dkt"
    override val titleRes = "Entry Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDkrScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertDokterViewModel = viewModel(factory = PenyediaDokterViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { innerpadding->
        EntryBody(
            insertDokterState = viewModel.dokterState,
            onSiswaValueChange = viewModel::updateInsertDkrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertDkr()
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
    insertDokterState: InsertDokterState,
    onSiswaValueChange: (InsertDokterEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertDokterEvent = insertDokterState.insertDokterEvent,
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
    insertDokterEvent: InsertDokterEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertDokterEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertDokterEvent.namadokter,
            onValueChange = { onValueChange(insertDokterEvent.copy(namadokter = it)) },
            label = { Text("Nama Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertDokterEvent.spesialisasi,
            onValueChange = { onValueChange(insertDokterEvent.copy(spesialisasi = it)) },
            label = { Text("Spesialisasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertDokterEvent.kontak,
            onValueChange = { onValueChange(insertDokterEvent.copy(kontak = it)) },
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
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
