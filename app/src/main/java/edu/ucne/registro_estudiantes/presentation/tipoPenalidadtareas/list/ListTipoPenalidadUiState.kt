package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.list

import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad

data class ListTipoPenalidadUiState(
    val isLoading: Boolean = false,
    val tipoPenalidades: List<TipoPenalidad> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)
