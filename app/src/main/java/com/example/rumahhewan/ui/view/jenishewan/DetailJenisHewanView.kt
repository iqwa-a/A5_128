package com.example.rumahhewan.ui.view.jenishewan

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.DetailJenisState
import com.example.rumahhewan.ui.viewmodel.jenishewan.DetailJenisViewModel
import com.example.rumahhewan.ui.viewmodel.jenishewan.PenyediaJenisHewanViewModel
import kotlinx.coroutines.launch

object DestinasiJenisDetail : DestinasiNavigasi {
    override val route = "detail_jns"
    override val titleRes = "Detail Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisScreen(
    navigateBack: () -> Unit,
    idjenishewan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailJenisViewModel = viewModel(factory = PenyediaJenisHewanViewModel.Factory),
    navController: NavHostController
) {
    val jenisState by viewModel.detailJenisState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_jns/$idjenishewan")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Mahasiswa")
            }
        }
    ) { innerPadding ->
        DetailBody(
            detailJenisState = jenisState,
            onDeleteClick = {
                coroutineScope.launch {
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
fun DetailBody(
    detailJenisState: DetailJenisState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailJenisState) {
        is DetailJenisState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailJenisState.Error -> {
            Text(
                text = detailJenisState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailJenisState.Success -> {
            val jenis = detailJenisState.jenis
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailJns(judul = "ID Jenis Hewan", isinya = jenis.idjenishewan)
                ComponentDetailJns(judul = "Nama Jenis Hewan", isinya = jenis.namajenishewan)
                ComponentDetailJns(judul = "Deskripsi", isinya = jenis.deskripsi)
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
fun ComponentDetailJns(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
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