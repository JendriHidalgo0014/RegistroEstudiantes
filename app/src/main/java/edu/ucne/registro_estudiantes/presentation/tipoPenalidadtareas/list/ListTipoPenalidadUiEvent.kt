package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.list


interface ListTipoPenalidadUiEvent {
    data object Load : ListTipoPenalidadUiEvent
    data class Delete(val id: Int) : ListTipoPenalidadUiEvent
    data object CreateNew : ListTipoPenalidadUiEvent
    data class Edit(val id: Int) : ListTipoPenalidadUiEvent
    data class ShowMessage(val message: String) : ListTipoPenalidadUiEvent
}