package edu.ucne.registro_estudiantes.presentation.tareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.model.Asignatura
import edu.ucne.registro_estudiantes.domain.usecase.UpsertAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturasUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateAula
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateCodigo
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateCreditos
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateNombre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAsignaturaViewModel @Inject constructor(
    private val getAsignaturaUseCase: GetAsignaturaUseCase,
    private val getAsignaturasUseCase: GetAsignaturasUseCase,
    private val upsertAsignaturaUseCase: UpsertAsignaturaUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EditAsignaturaUiState())
    val state: StateFlow<EditAsignaturaUiState> = _state.asStateFlow()

    fun onEvent(event: EditAsignaturaUiEvent) {
        when (event) {
            is EditAsignaturaUiEvent.Load -> onLoad(event.id)
            is EditAsignaturaUiEvent.CodigoChanged -> _state.update {
                it.copy(
                    codigo = event.value,
                    codigoError = null
                )
            }
            is EditAsignaturaUiEvent.NombreChanged -> _state.update {
                it.copy(
                    nombre = event.value,
                    nombreError = null
                )
            }
            is EditAsignaturaUiEvent.AulaChanged -> _state.update {
                it.copy(
                    aula = event.value,
                    aulaError = null
                )
            }
            is EditAsignaturaUiEvent.CreditosChanged -> _state.update {
                it.copy(
                    creditos = event.value,
                    creditosError = null
                )
            }
            EditAsignaturaUiEvent.Save -> onSave()
            EditAsignaturaUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, asignaturaId = null) }
            return
        }
        viewModelScope.launch {
            val asignatura = getAsignaturaUseCase(id)
            if (asignatura != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        asignaturaId = asignatura.asignaturaId,
                        codigo = asignatura.codigo,
                        nombre = asignatura.nombre,
                        aula = asignatura.aula,
                        creditos = asignatura.creditos.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val codigo = state.value.codigo
        val nombre = state.value.nombre
        val aula = state.value.aula
        val creditosStr = state.value.creditos

        val c = validateCodigo(codigo)
        val n = validateNombre(nombre)
        val a = validateAula(aula)
        val cr = validateCreditos(creditosStr)

        if (!c.isValid || !n.isValid || !a.isValid || !cr.isValid) {
            _state.update {
                it.copy(
                    codigoError = c.error,
                    nombreError = n.error,
                    aulaError = a.error,
                    creditosError = cr.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.asignaturaId ?: 0
            val asignatura = Asignatura(id, codigo, nombre, aula, creditosStr.toInt())

            val nombresExistentes = getAsignaturasUseCase().first()
                .filter { it.asignaturaId != id }
                .map { it.nombre }

            val result = upsertAsignaturaUseCase(asignatura, nombresExistentes)
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
        val id = state.value.asignaturaId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteAsignaturaUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}