package com.example.rumahhewan.ui.view.jenishewan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanEvent
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.PenyediaJenisHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.UpdateJenisEvent
import com.example.rumahhewan.ui.viewmodel.jenishewan.UpdateJenisHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.UpdateJenisState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisHewanScreen(
    navigateBack: () -> Unit,
    idjenishewan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisHewanViewModel = viewModel(factory = PenyediaJenisHewanViewModel.Factory)
) {
    val jenisState = viewModel.jenisState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idjenishewan) {
        viewModel.getJenisById(idjenishewan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

        }
    ) { innerPadding ->
        UpdateBody(
            updateJenisState = jenisState,
            onSiswaValueChange = viewModel::updateUpdateJnsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJns()
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
    updateJenisState: UpdateJenisState,
    onSiswaValueChange: (UpdateJenisEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateJenisEvent = updateJenisState.updateJenisEvent,
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
    updateJenisEvent: UpdateJenisEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateJenisEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateJenisEvent.namajenishewan,
            onValueChange = { onValueChange(updateJenisEvent.copy(namajenishewan = it)) },
            label = { Text("Nama Jenis Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateJenisEvent.idjenishewan,
            onValueChange = { onValueChange(updateJenisEvent.copy(idjenishewan = it)) },
            label = { Text("ID Jenis Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateJenisEvent.deskripsi,
            onValueChange = { onValueChange(updateJenisEvent.copy(deskripsi = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}