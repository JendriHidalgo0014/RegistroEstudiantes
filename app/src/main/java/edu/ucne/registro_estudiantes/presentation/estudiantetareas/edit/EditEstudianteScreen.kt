package edu.ucne.registro_estudiantes.presentation.estudiantetareas.edit

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
fun EditEstudianteScreen(
    estudianteId: Int?,
    onDrawer: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditEstudianteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(estudianteId, state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        } else {
            viewModel.onEvent(EditEstudianteUiEvent.Load(estudianteId))
        }
    }

    EditEstudianteBody(state, viewModel::onEvent, onDrawer, goBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEstudianteBody(
    state: EditEstudianteUiState,
    onEvent: (EditEstudianteUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Estudiante" else "Modificar") },
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
                value = state.nombres,
                onValueChange = { onEvent(EditEstudianteUiEvent.NombresChanged(it)) },
                label = { Text("Nombres") },
                isError = state.nombresError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombresError != null) {
                Text(
                    state.nombresError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(EditEstudianteUiEvent.EmailChanged(it)) },
                label = { Text("Email") },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.emailError != null) {
                Text(
                    state.emailError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.edad,
                onValueChange = { onEvent(EditEstudianteUiEvent.EdadChanged(it)) },
                label = { Text("Edad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.edadError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.edadError != null) {
                Text(
                    state.edadError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { onEvent(EditEstudianteUiEvent.Save) },
                    enabled = !state.isSaving
                ) { Text("Guardar") }
                Spacer(Modifier.width(8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditEstudianteUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditEstudianteBodyPreview() {
    val state = EditEstudianteUiState()
    MaterialTheme {
        EditEstudianteBody(state = state, onEvent = {}, onDrawer = {}, goBack = {})
    }
}