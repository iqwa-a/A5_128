package com.example.rumahhewan.ui.view.hewan

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
import kotlinx.coroutines.launch

object DestinasihewanInsertEntry: DestinasiNavigasi {
    override val route = "item_hwn"
    override val titleRes = "Entry Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryHwnScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory)

){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        }
    ) { innerpadding->
        EntryBody(
            insertHewanState = viewModel.hewanState,
            onSiswaValueChange = viewModel::updateInsertHwnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertHwn()
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
    insertHewanState: InsertHewanState,
    onSiswaValueChange: (InsertHewanEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = Modifier.padding(12.dp)
    ) {
        FormInput(
            insertHewanEvent = insertHewanState.insertHewanEvent,
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
    insertHewanEvent: InsertHewanEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertHewanEvent)->Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertHewanEvent.namahewan,
            onValueChange = { onValueChange(insertHewanEvent.copy(namahewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertHewanEvent.jenishewanid,
            onValueChange = { onValueChange(insertHewanEvent.copy(jenishewanid = it)) },
            label = { Text("Jenis Hewan ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertHewanEvent.pemilik,
            onValueChange = { onValueChange(insertHewanEvent.copy(pemilik = it)) },
            label = { Text("Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertHewanEvent.kontakpemilik,
            onValueChange = { onValueChange(insertHewanEvent.copy(kontakpemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertHewanEvent.tanggallahir,
            onValueChange = { onValueChange(insertHewanEvent.copy(tanggallahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertHewanEvent.catatankesehatan,
            onValueChange = { onValueChange(insertHewanEvent.copy(catatankesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
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
