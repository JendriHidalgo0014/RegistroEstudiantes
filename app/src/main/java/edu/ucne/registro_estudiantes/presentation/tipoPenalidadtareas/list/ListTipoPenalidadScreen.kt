package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad

@Composable
fun ListTipoPenalidadScreen(
    viewModel: ListTipoPenalidadViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTipoPenalidadBody(
        state = state,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { viewModel.onEvent(ListTipoPenalidadUiEvent.Delete(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consulta de Tipos de Penalidades") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreate() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(state.tipoPenalidades) { tipoPenalidad ->
                        TipoPenalidadCard(
                            tipoPenalidad = tipoPenalidad,
                            onClick = { onEdit(tipoPenalidad.tipoId) },
                            onDelete = { onDelete(tipoPenalidad.tipoId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TipoPenalidadCard(
    tipoPenalidad: TipoPenalidad,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Text(
                        text = "Nombre: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tipoPenalidad.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row {
                    Text(
                        text = "Descripción: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tipoPenalidad.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row {
                    Text(
                        text = "Puntos de Descuento: ",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = tipoPenalidad.puntosdescuento.toString(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            TextButton(onClick = { onDelete() }) { Text("Eliminar") }
        }
    }
}

@Preview
@Composable
private fun ListTipoPenalidadBodyPreview() {
    val state = ListTipoPenalidadUiState()
    MaterialTheme {
        ListTipoPenalidadBody(
            state = state,
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {}
        )
    }
}