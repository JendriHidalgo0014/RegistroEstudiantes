package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.edit


interface EditTipoPenalidadUiEvent {
    data class Load(val id: Int?) : EditTipoPenalidadUiEvent
    data class NombreChanged(val value: String) : EditTipoPenalidadUiEvent
    data class DescripcionChanged(val value: String) : EditTipoPenalidadUiEvent
    data class PuntosDescuentoChanged(val value: String) : EditTipoPenalidadUiEvent
    data object Save : EditTipoPenalidadUiEvent
    data object Delete : EditTipoPenalidadUiEvent
}