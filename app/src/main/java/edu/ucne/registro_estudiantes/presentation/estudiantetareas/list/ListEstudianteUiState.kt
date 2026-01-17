package edu.ucne.registro_estudiantes.presentation.estudiantetareas.list

import edu.ucne.registro_estudiantes.domain.model.Estudiante

data class ListEstudianteUiState(
    val isLoading: Boolean = false,
    val estudiantes: List<Estudiante> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)