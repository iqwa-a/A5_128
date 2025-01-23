package com.example.rumahhewan.ui.view.hewan

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumahhewan.R
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel

object DestinasiHome: DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Hwn"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getHwn()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Top AppBar added for a proper header
            androidx.compose.material3.TopAppBar(
                title = { Text("Home Hewan") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homeHewanState = viewModel.hwnHewanState,
            retryAction = { viewModel.getHwn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deleteHwn(it.idhewan); viewModel.getHwn() }
        )
    }
}

@Composable
fun HomeStatus(
    homeHewanState: HomeHewanState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeHewanState) {
        is HomeHewanState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeHewanState.Success -> {
            Log.d("HomeScreen", "Data Hewan: ${homeHewanState.hewan}")
            if (homeHewanState.hewan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Hewan")
                }
            } else {
                HwnLayout(
                    hewan = homeHewanState.hewan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idhewan) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomeHewanState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun HwnLayout(
    hewan: List<Hewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Hewan) -> Unit,
    onDeleteClick: (Hewan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(hewan) { kontak ->
            HwnCard(
                hewan = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kontak) },
                onDeleteClick = { onDeleteClick(kontak) }
            )
        }
    }
}

@Composable
fun HwnCard(
    hewan: Hewan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Hewan) -> Unit
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hewan.namahewan,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(hewan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus"
                    )
                }
            }
            Text(text = "Jenis Hewan: ${hewan.jenishewanid}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Pemilik: ${hewan.pemilik}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Kontak: ${hewan.kontakpemilik}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tanggal Lahir: ${hewan.tanggallahir}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Catatan: ${hewan.catatankesehatan}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
