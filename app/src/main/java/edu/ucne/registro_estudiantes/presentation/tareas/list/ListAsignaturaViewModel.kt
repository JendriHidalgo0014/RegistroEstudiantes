package edu.ucne.registro_estudiantes.presentation.tareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListAsignaturaViewModel @Inject constructor(
    private val getAsignaturasUseCase: GetAsignaturasUseCase,
    private val deleteAsignaturaUseCase: DeleteAsignaturaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListAsignaturaUiState())
    val state: StateFlow<ListAsignaturaUiState> = _state.asStateFlow()

    init {
        onLoad()
    }

    fun onEvent(event: ListAsignaturaUiEvent) {
        when (event) {
            ListAsignaturaUiEvent.Load -> onLoad()
            is ListAsignaturaUiEvent.Delete -> onDelete(event.id)
            ListAsignaturaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListAsignaturaUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListAsignaturaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            getAsignaturasUseCase().collectLatest { asignaturas ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        asignaturas = asignaturas
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteAsignaturaUseCase(id)
            _state.update { it.copy(message = "Asignatura eliminada") }
        }
    }
}