package edu.ucne.registro_estudiantes.presentation.tareas.list

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_estudiantes.domain.model.Asignatura

@Composable
fun ListAsignaturaScreen(
    viewModel: ListAsignaturaViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListAsignaturaBody(
        state = state,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { viewModel.onEvent(ListAsignaturaUiEvent.Delete(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListAsignaturaBody(
    state: ListAsignaturaUiState,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consulta de Asignaturas") },
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
                    items(state.asignaturas) { asignatura ->
                        AsignaturaCard(
                            asignatura = asignatura,
                            onClick = { onEdit(asignatura.asignaturaId) },
                            onDelete = { onDelete(asignatura.asignaturaId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AsignaturaCard(
    asignatura: Asignatura,
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
                Text(asignatura.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Código: ${asignatura.codigo}")
                Text("Aula: ${asignatura.aula}")
                Text("Créditos: ${asignatura.creditos}")
            }
            TextButton(onClick = { onDelete() }) { Text("Eliminar") }
        }
    }
}

@Preview
@Composable
private fun ListAsignaturaBodyPreview() {
    val state = ListAsignaturaUiState()
    MaterialTheme {
        ListAsignaturaBody(
            state = state,
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {}
        )
    }
}