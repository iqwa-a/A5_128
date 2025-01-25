package com.example.rumahhewan.ui.view.perawatan

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
import com.example.rumahhewan.ui.view.hewan.DetailBody
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.DetailPerawatanState
import com.example.rumahhewan.ui.viewmodel.perawatan.DetailPerawatanViewModel
import com.example.rumahhewan.ui.viewmodel.perawatan.PenyediaPerawatanViewModel
import kotlinx.coroutines.launch

object DestinasiPerawatanDetail : DestinasiNavigasi {
    override val route = "detail_pwt"
    override val titleRes = "Detail Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPerawatanScreen(
    navigateBack: () -> Unit,
    idperawatan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPerawatanViewModel = viewModel(factory = PenyediaPerawatanViewModel.Factory),
    navController: NavHostController
) {
    val perawatanState by viewModel.detailPerawatanState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("update_pwt/$idperawatan")
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Mahasiswa")
            }
        }
    ) { innerPadding ->
        DetailBody(
            detailPerawatanState = perawatanState,
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
    detailPerawatanState: DetailPerawatanState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (detailPerawatanState) {
        is DetailPerawatanState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailPerawatanState.Error -> {
            Text(
                text = detailPerawatanState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailPerawatanState.Success -> {
            val perawatan = detailPerawatanState.perawatan
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailPwt(judul = "ID Perawatan", isinya = perawatan.idperawatan)
                ComponentDetailPwt(judul = "ID Hewan", isinya = perawatan.idhewan)
                ComponentDetailPwt(judul = "ID Dokter", isinya = perawatan.iddokter)
                ComponentDetailPwt(judul = "Tanggal Perawatan", isinya = perawatan.tanggalperawatan)
                ComponentDetailPwt(judul = "Detail Perawatan", isinya = perawatan.detailperawatan)
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
fun ComponentDetailPwt(
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
