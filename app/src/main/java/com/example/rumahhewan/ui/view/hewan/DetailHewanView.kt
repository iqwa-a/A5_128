package com.example.rumahhewan.ui.view.hewan

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rumahhewan.ui.navigasi.CustomeTopAppBarr
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import kotlinx.coroutines.launch

object DestinasiHewanDetail : DestinasiNavigasi {
    override val route = "detail_hwn"
    override val titleRes = "Detail Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHewanScreen(
    navigateBack: () -> Unit,
    idhewan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory),
    navController: NavHostController
) {
    val hewanState by viewModel.detailHewanState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomeTopAppBarr(
                title = DestinasiHewanDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_hwn/$idhewan")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Mahasiswa")
            }
        }
    ) { innerPadding ->
        // Menerapkan padding dari Scaffold ke DetailBody
        DetailBody(
            detailHewanState = hewanState,
            onDeleteClick = {
                coroutineScope.launch {
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding) // Ini memastikan konten tidak tertumpuk dengan TopAppBar
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBody(
    detailHewanState: DetailHewanState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailHewanState) {
        is DetailHewanState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        is DetailHewanState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier.fillMaxSize()
            ) {
                Text(
                    text = detailHewanState.message,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        is DetailHewanState.Success -> {
            val hewan = detailHewanState.hewan
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailHwn(judul = "ID Hewan", isinya = hewan.idhewan)
                ComponentDetailHwn(judul = "Nama Hewan", isinya = hewan.namahewan)
                ComponentDetailHwn(judul = "Jenis Hewan", isinya = hewan.jenishewanid)
                ComponentDetailHwn(judul = "Pemilik", isinya = hewan.pemilik)
                ComponentDetailHwn(judul = "Kontak Pemilik", isinya = hewan.kontakpemilik)
                ComponentDetailHwn(judul = "Tanggal Lahir", isinya = hewan.tanggallahir)
                ComponentDetailHwn(judul = "Catatan Kesehatan", isinya = hewan.catatankesehatan)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onDeleteClick,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Hapus")
                }
            }
        }
    }
}

@Composable
fun ComponentDetailHwn(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
