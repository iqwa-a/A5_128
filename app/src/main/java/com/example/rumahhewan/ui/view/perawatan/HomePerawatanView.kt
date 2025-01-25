package com.example.rumahhewan.ui.view.perawatan

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
import com.example.rumahhewan.model.Perawatan
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.HomePerawatanState
import com.example.rumahhewan.ui.viewmodel.perawatan.HomePerawatanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.PenyediaPerawatanViewModel

object DestinasiPerawatanHome: DestinasiNavigasi {
    override val route = "perawatan"
    override val titleRes = "Home Pwt"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeperawatanScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePerawatanViewModel = viewModel(factory = PenyediaPerawatanViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPwt()
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // Top AppBar added for a proper header
            androidx.compose.material3.TopAppBar(
                title = { Text("Home Perawatan") },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Perawat")
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homePerawatanState = viewModel.pwtPerawatanState,
            retryAction = { viewModel.getPwt() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { viewModel.deletePwt(it.idperawatan); viewModel.getPwt() }
        )
    }
}

@Composable
fun HomeStatus(
    homePerawatanState: HomePerawatanState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Perawatan) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homePerawatanState) {
        is HomePerawatanState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePerawatanState.Success -> {
            Log.d("HomeScreen", "Data Perawat: ${homePerawatanState.perawatan}")
            if (homePerawatanState.perawatan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Perawatan")
                }
            } else {
                PwtLayout(
                    perawatan = homePerawatanState.perawatan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idperawatan) },
                    onDeleteClick = onDeleteClick
                )
            }
        }
        is HomePerawatanState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun PwtLayout(
    perawatan: List<Perawatan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Perawatan) -> Unit,
    onDeleteClick: (Perawatan) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(perawatan) { kontak ->
            PwtCard(
                perawatan = kontak,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kontak) },
                onDeleteClick = { onDeleteClick(kontak) }
            )
        }
    }
}

@Composable
fun PwtCard(
    perawatan: Perawatan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Perawatan) -> Unit
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
                    text = "ID Perawatan: ${perawatan.idperawatan}",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(perawatan) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus"
                    )
                }
            }
            Text(text = "ID Hewan: ${perawatan.idhewan}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "ID Dokter: ${perawatan.iddokter}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tanggal Perawatan: ${perawatan.tanggalperawatan}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Detail Perawatan: ${perawatan.detailperawatan}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
