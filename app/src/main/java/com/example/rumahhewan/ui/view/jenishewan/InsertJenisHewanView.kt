package com.example.rumahhewan.ui.view.jenishewan

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
import com.example.rumahhewan.ui.viewmodel.hewan.InsertHewanEvent
import com.example.rumahhewan.ui.viewmodel.hewan.InsertHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.InsertJenisEvent
import com.example.rumahhewan.ui.viewmodel.jenishewan.InsertJenisHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.InsertJenisState
import com.example.rumahhewan.ui.viewmodel.jenishewan.PenyediaJenisHewanViewModel
import kotlinx.coroutines.launch

object DestinasiInsertjenisEntry: DestinasiNavigasi {
    override val route = "item_jns"
    override val titleRes = "Entry Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJnsScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisHewanViewModel = viewModel(factory = PenyediaJenisHewanViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { innerpadding->
        EntryBody(
            insertJenisState = viewModel.jenisState,
            onSiswaValueChange = viewModel::updateInsertJnsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJns()
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
    insertJenisState: InsertJenisState,
    onSiswaValueChange: (InsertJenisEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertJenisEvent = insertJenisState.insertJenisEvent,
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
    insertJenisEvent: InsertJenisEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertJenisEvent.namajenishewan,
            onValueChange = { onValueChange(insertJenisEvent.copy(namajenishewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertJenisEvent.idjenishewan,
            onValueChange = { onValueChange(insertJenisEvent.copy(idjenishewan = it)) },
            label = { Text("Jenis Hewan ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertJenisEvent.deskripsi,
            onValueChange = { onValueChange(insertJenisEvent.copy(deskripsi = it)) },
            label = { Text("Deskripsi") },
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
