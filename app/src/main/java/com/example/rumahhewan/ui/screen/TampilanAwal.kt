package com.example.rumahhewan.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rumahhewan.R
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.ui.navigasi.DestinasiNavigasi
import com.example.rumahhewan.ui.view.hewan.HwnCard
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanState
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.PenyediaHewanViewModel

object Destinasiawal : DestinasiNavigasi {
    override val route = "detail_hwn"
    override val titleRes = "Detail Hewan"
}

@Composable
fun TampilanAwal(
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaHewanViewModel.Factory),
    onDetailClick: (Hewan) -> Unit = {},
    onDeleteClick: (Hewan) -> Unit = {},
    onpasienButton: () -> Unit,
    onjenishewanButton: () -> Unit,
    ondokterButton: () -> Unit,
    onperawatanButton: () -> Unit
) {
    val hewanState by remember { mutableStateOf(viewModel.hwnHewanState) }

    LaunchedEffect(Unit) {
        viewModel.getHwn()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFB2EBF2), Color.White)))
    ) {
        when (hewanState) {
            is HomeHewanState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            is HomeHewanState.Success -> {
                val hewanList = (hewanState as HomeHewanState.Success).hewan
                Column {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    // Daftar Hewan
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(hewanList) { hewan ->
                            HwnCard(
                                hewan = hewan,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.medium)
                                    .shadow(8.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .clickable { onDetailClick(hewan) },
                                onDeleteClick = { viewModel.deleteHwn(hewan.idhewan) }
                            )
                        }
                    }
                }
            }
            is HomeHewanState.Error -> {
                Text(
                    text = "Terjadi kesalahan. Silakan coba lagi.",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Tombol Navigasi di Bagian Bawah
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationButton(
                imageResId = R.drawable.pet,
                description = "Pasien",
                onClick = onpasienButton
            )
            NavigationButton(
                imageResId = R.drawable.dog2,
                description = "Jenis Hewan",
                onClick = onjenishewanButton
            )
            NavigationButton(
                imageResId = R.drawable.dokter,
                description = "Dokter",
                onClick = ondokterButton
            )
            NavigationButton(
                imageResId = R.drawable.perawat,
                description = "Perawat",
                onClick = onperawatanButton
            )
        }
    }
}

@Composable
fun NavigationButton(
    imageResId: Int,
    description: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = description,
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}







