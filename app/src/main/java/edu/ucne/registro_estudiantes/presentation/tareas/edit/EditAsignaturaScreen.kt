package edu.ucne.registro_estudiantes.presentation.tareas.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditAsignaturaScreen(
    asignaturaId: Int?,
    onDrawer: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditAsignaturaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(asignaturaId, state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        } else {
            viewModel.onEvent(EditAsignaturaUiEvent.Load(asignaturaId))
        }
    }

    EditAsignaturaBody(state, viewModel::onEvent, onDrawer, goBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditAsignaturaBody(
    state: EditAsignaturaUiState,
    onEvent: (EditAsignaturaUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nueva Asignatura" else "Modificar") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.codigo,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CodigoChanged(it)) },
                label = { Text("Código") },
                isError = state.codigoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.codigoError != null) {
                Text(
                    state.codigoError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditAsignaturaUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.aula,
                onValueChange = { onEvent(EditAsignaturaUiEvent.AulaChanged(it)) },
                label = { Text("Aula") },
                isError = state.aulaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.aulaError != null) {
                Text(
                    state.aulaError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.creditos,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CreditosChanged(it)) },
                label = { Text("Créditos") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.creditosError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.creditosError != null) {
                Text(
                    state.creditosError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { onEvent(EditAsignaturaUiEvent.Save) },
                    enabled = !state.isSaving
                ) { Text("Guardar") }
                Spacer(Modifier.width(8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditAsignaturaUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditAsignaturaBodyPreview() {
    val state = EditAsignaturaUiState()
    MaterialTheme {
        EditAsignaturaBody(state = state, onEvent = {}, onDrawer = {}, goBack = {})
    }
}