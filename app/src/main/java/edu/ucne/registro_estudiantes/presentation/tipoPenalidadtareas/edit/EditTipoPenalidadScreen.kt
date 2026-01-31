package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.edit

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
fun EditTipoPenalidadScreen(
    tipoId: Int?,
    onDrawer: () -> Unit,
    goBack: () -> Unit,
    viewModel: EditTipoPenalidadViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(tipoId, state.saved, state.deleted) {
        if (state.saved || state.deleted) {
            goBack()
        } else {
            viewModel.onEvent(EditTipoPenalidadUiEvent.Load(tipoId))
        }
    }

    EditTipoPenalidadBody(state, viewModel::onEvent, onDrawer, goBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit,
    onDrawer: () -> Unit,
    goBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isNew) "Nuevo Tipo de Penalidad" else "Modificar") },
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
                value = state.nombre,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(it)) },
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
                value = state.descripcion,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(it)) },
                label = { Text("Descripción") },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.descripcionError != null) {
                Text(
                    state.descripcionError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.puntosDescuento,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosDescuentoChanged(it)) },
                label = { Text("Puntos de Descuento") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.puntosDescuentoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.puntosDescuentoError != null) {
                Text(
                    state.puntosDescuentoError,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { onEvent(EditTipoPenalidadUiEvent.Save) },
                    enabled = !state.isSaving
                ) { Text("Guardar") }
                Spacer(Modifier.width(8.dp))
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditTipoPenalidadUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditTipoPenalidadBodyPreview() {
    val state = EditTipoPenalidadUiState()
    MaterialTheme {
        EditTipoPenalidadBody(state = state, onEvent = {}, onDrawer = {}, goBack = {})
    }
}