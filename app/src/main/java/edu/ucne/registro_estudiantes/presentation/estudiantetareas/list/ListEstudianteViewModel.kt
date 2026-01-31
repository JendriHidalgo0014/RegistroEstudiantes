package edu.ucne.registro_estudiantes.presentation.estudiantetareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.DeleteEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.GetEstudiantesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEstudianteViewModel @Inject constructor(
    private val getEstudiantesUseCase: GetEstudiantesUseCase,
    private val deleteEstudianteUseCase: DeleteEstudianteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListEstudianteUiState())
    val state: StateFlow<ListEstudianteUiState> = _state.asStateFlow()

    fun onEvent(event: ListEstudianteUiEvent) {
        when (event) {
            ListEstudianteUiEvent.Load -> onLoad()
            is ListEstudianteUiEvent.Delete -> onDelete(event.id)
            ListEstudianteUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListEstudianteUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListEstudianteUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getEstudiantesUseCase().collect { estudiantes ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        estudiantes = estudiantes
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteEstudianteUseCase(id)
            _state.update { it.copy(message = "Estudiante eliminado") }
        }
    }
}