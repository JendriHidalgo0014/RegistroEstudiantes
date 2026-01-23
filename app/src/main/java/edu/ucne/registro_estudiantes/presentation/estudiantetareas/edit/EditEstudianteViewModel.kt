package edu.ucne.registro_estudiantes.presentation.estudiantetareas.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.GetEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.GetEstudiantesUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.UpsertEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.DeleteEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.validateNombres
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.validateEmail
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.validateEdad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEstudianteViewModel @Inject constructor(
    private val getEstudianteUseCase: GetEstudianteUseCase,
    private val getEstudiantesUseCase: GetEstudiantesUseCase,
    private val upsertEstudianteUseCase: UpsertEstudianteUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditEstudianteUiState())
    val state: StateFlow<EditEstudianteUiState> = _state.asStateFlow()

    fun onEvent(event: EditEstudianteUiEvent) {
        when (event) {
            is EditEstudianteUiEvent.Load -> onLoad(event.id)
            is EditEstudianteUiEvent.NombresChanged -> _state.update {
                it.copy(
                    nombres = event.value,
                    nombresError = null
                )
            }
            is EditEstudianteUiEvent.EmailChanged -> _state.update {
                it.copy(
                    email = event.value,
                    emailError = null
                )
            }
            is EditEstudianteUiEvent.EdadChanged -> _state.update {
                it.copy(
                    edad = event.value,
                    edadError = null
                )
            }
            EditEstudianteUiEvent.Save -> onSave()
            EditEstudianteUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) {
            _state.update { it.copy(isNew = true, estudianteId = null) }
            return
        }
        viewModelScope.launch {
            val estudiante = getEstudianteUseCase(id)
            if (estudiante != null) {
                _state.update {
                    it.copy(
                        isNew = false,
                        estudianteId = estudiante.estudianteId,
                        nombres = estudiante.nombres,
                        email = estudiante.email,
                        edad = estudiante.edad.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        val nombres = state.value.nombres
        val email = state.value.email
        val edadStr = state.value.edad

        val n = validateNombres(nombres)
        val e = validateEmail(email)
        val ed = validateEdad(edadStr)

        if (!n.isValid || !e.isValid || !ed.isValid) {
            _state.update {
                it.copy(
                    nombresError = n.error,
                    emailError = e.error,
                    edadError = ed.error
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            val id = state.value.estudianteId ?: 0
            val estudiante = Estudiante(id, nombres, email, edadStr.toInt())

            val nombresExistentes = getEstudiantesUseCase().first()
                .filter { it.estudianteId != id }
                .map { it.nombres }

            val result = upsertEstudianteUseCase(estudiante, nombresExistentes)
            result.onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombresError = error.message
                    )
                }
            }
        }
    }

    private fun onDelete() {
        val id = state.value.estudianteId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            deleteEstudianteUseCase(id)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}