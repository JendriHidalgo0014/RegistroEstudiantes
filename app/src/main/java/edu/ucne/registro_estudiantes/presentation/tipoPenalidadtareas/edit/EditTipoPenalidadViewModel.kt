package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.DeleteTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.GetTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.GetTipoPenalidadesUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.UpsertTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.validateDescripcion
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.validateNombre
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.validatePuntosDescuento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadUseCase: GetTipoPenalidadUseCase,
    private val getTipoPenalidadesUseCase: GetTipoPenalidadesUseCase,
    private val upsertTipoPenalidadUseCase: UpsertTipoPenalidadUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditTipoPenalidadUiState())
    val state: StateFlow<EditTipoPenalidadUiState> = _state.asStateFlow()

    fun onEvent(event: EditTipoPenalidadUiEvent) {
        when (event) {
            is EditTipoPenalidadUiEvent.Load -> onLoad(event.id)
            is EditTipoPenalidadUiEvent.NombreChanged -> _state.update {
                it.copy(
                    nombre = event.value,
                    nombreError = null
                )
            }
            is EditTipoPenalidadUiEvent.DescripcionChanged -> _state.update {
                it.copy(
                    descripcion = event.value,
                    descripcionError = null
                )
            }
            is EditTipoPenalidadUiEvent.PuntosDescuentoChanged -> _state.update {
                it.copy(
                    puntosDescuento = event.value,
                    puntosDescuentoError = null
                )
            }
            EditTipoPenalidadUiEvent.Save -> onSave()
            EditTipoPenalidadUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, tipoId = null) }
            return
        }
        viewModelScope.launch {
            val tipoPenalidad = getTipoPenalidadUseCase(id)
            if (tipoPenalidad != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        tipoId = tipoPenalidad.tipoId,
                        nombre = tipoPenalidad.nombre,
                        descripcion = tipoPenalidad.descripcion,
                        puntosDescuento = tipoPenalidad.puntosdescuento.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val nombre = state.value.nombre
        val descripcion = state.value.descripcion
        val puntosDescuentoStr = state.value.puntosDescuento

        val n = validateNombre(nombre)
        val d = validateDescripcion(descripcion)
        val p = validatePuntosDescuento(puntosDescuentoStr)

        if (!n.isValid || !d.isValid || !p.isValid) {
            _state.update {
                it.copy(
                    nombreError = n.error,
                    descripcionError = d.error,
                    puntosDescuentoError = p.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.tipoId ?: 0
            val tipoPenalidad = TipoPenalidad(id, nombre, descripcion, puntosDescuentoStr.toInt())

            val nombresExistentes = getTipoPenalidadesUseCase().first()
                .filter { it.tipoId != id }
                .map { it.nombre }

            val result = upsertTipoPenalidadUseCase(tipoPenalidad, nombresExistentes)
            result.onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombreError = error.message
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.tipoId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteTipoPenalidadUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}