package com.example.rumahhewan.ui.view.hewan

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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHewanScreen(
    navigateBack: () -> Unit,
    idhewan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory)
) {
    val hewanState = viewModel.hewanState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idhewan) {
        viewModel.getHewanByIdhewan(idhewan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {

        }
    ) { innerPadding ->
        UpdateBody(
            updateHewanState = hewanState,
            onSiswaValueChange = viewModel::updateUpdateHwnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateHwn()
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
    updateHewanState: UpdateHewanState,
    onSiswaValueChange: (UpdateHewanEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateHewanEvent = updateHewanState.updateHewanEvent,
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
    updateHewanEvent: UpdateHewanEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateHewanEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateHewanEvent.namahewan,
            onValueChange = { onValueChange(updateHewanEvent.copy(namahewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateHewanEvent.jenishewanid,
            onValueChange = { onValueChange(updateHewanEvent.copy(jenishewanid = it)) },
            label = { Text("Jenis Hewan ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateHewanEvent.pemilik,
            onValueChange = { onValueChange(updateHewanEvent.copy(pemilik = it)) },
            label = { Text("Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateHewanEvent.kontakpemilik,
            onValueChange = { onValueChange(updateHewanEvent.copy(kontakpemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateHewanEvent.tanggallahir,
            onValueChange = { onValueChange(updateHewanEvent.copy(tanggallahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateHewanEvent.catatankesehatan,
            onValueChange = { onValueChange(updateHewanEvent.copy(catatankesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
