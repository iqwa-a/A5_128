package com.example.rumahhewan.ui.view.dokter

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
import com.example.rumahhewan.ui.viewmodel.dokter.DetailDokterState
import com.example.rumahhewan.ui.viewmodel.dokter.DetailDokterViewModel
import com.example.rumahhewan.ui.viewmodel.dokter.PenyediaDokterViewModel
import kotlinx.coroutines.launch

object DestinasiDokterDetail : DestinasiNavigasi {
    override val route = "detail_dkr"
    override val titleRes = "Detail Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailDokterScreen(
    navigateBack: () -> Unit,
    iddokter: String,
    modifier: Modifier = Modifier,
    viewModel: DetailDokterViewModel = viewModel(factory = PenyediaDokterViewModel.Factory),
    navController: NavHostController
) {
    val dokterState by viewModel.detailDokterState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_dkr/$iddokter")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Dokter")
            }
        }
    ) { innerPadding ->
        DetailBody(
            detailDokterState = dokterState,
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
    detailDokterState: DetailDokterState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailDokterState) {
        is DetailDokterState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailDokterState.Error -> {
            Text(
                text = detailDokterState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailDokterState.Success -> {
            val dokter = detailDokterState.dokter
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailDkr(judul = "ID Dokter", isinya = dokter.iddokter)
                ComponentDetailDkr(judul = "Nama Dokter", isinya = dokter.namadokter)
                ComponentDetailDkr(judul = "Spesialisasi", isinya = dokter.spesialisasi)
                ComponentDetailDkr(judul = "Kontak", isinya = dokter.kontak)
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
fun ComponentDetailDkr(
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