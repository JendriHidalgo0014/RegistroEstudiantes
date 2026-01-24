package edu.ucne.registro_estudiantes.presentation.tareas.list

import edu.ucne.registro_estudiantes.domain.model.Asignatura

data class ListAsignaturaUiState(
    val isLoading: Boolean = false,
    val asignaturas: List<Asignatura> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)