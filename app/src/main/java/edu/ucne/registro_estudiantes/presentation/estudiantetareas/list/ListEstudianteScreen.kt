package edu.ucne.registro_estudiantes.presentation.estudiantetareas.list

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
import edu.ucne.registro_estudiantes.domain.model.Estudiante

@Composable
fun ListEstudianteScreen(
    viewModel: ListEstudianteViewModel = hiltViewModel(),
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListEstudianteBody(
        state = state,
        onDrawer = onDrawer,
        onCreate = onCreate,
        onEdit = onEdit,
        onDelete = { viewModel.onEvent(ListEstudianteUiEvent.Delete(it)) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListEstudianteBody(
    state: ListEstudianteUiState,
    onDrawer: () -> Unit,
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consulta de Estudiante") },
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
                    items(state.estudiantes) { estudiante ->
                        EstudianteCard(
                            estudiante = estudiante,
                            onClick = { onEdit(estudiante.estudianteId) },
                            onDelete = { onDelete(estudiante.estudianteId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstudianteCard(
    estudiante: Estudiante,
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
                Text(estudiante.nombres, style = MaterialTheme.typography.titleMedium)
                Text("Email: ${estudiante.email}")
                Text("Edad: ${estudiante.edad}")
            }
            TextButton(onClick = { onDelete() }) { Text("Eliminar") }
        }
    }
}

@Preview
@Composable
private fun ListEstudianteBodyPreview() {
    val state = ListEstudianteUiState()
    MaterialTheme {
        ListEstudianteBody(
            state = state,
            onDrawer = {},
            onCreate = {},
            onEdit = {},
            onDelete = {}
        )
    }
}