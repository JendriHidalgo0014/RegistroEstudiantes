package edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.DeleteTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.GetTipoPenalidadesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTipoPenalidadViewModel @Inject constructor(
    private val getTipoPenalidadesUseCase: GetTipoPenalidadesUseCase,
    private val deleteTipoPenalidadUseCase: DeleteTipoPenalidadUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListTipoPenalidadUiState())
    val state: StateFlow<ListTipoPenalidadUiState> = _state.asStateFlow()

    init {
        onLoad()
    }

    fun onEvent(event: ListTipoPenalidadUiEvent) {
        when (event) {
            ListTipoPenalidadUiEvent.Load -> onLoad()
            is ListTipoPenalidadUiEvent.Delete -> onDelete(event.id)
            ListTipoPenalidadUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListTipoPenalidadUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListTipoPenalidadUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun onLoad() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, message = null) }
            getTipoPenalidadesUseCase().collectLatest { tipoPenalidades ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        tipoPenalidades = tipoPenalidades
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int) {
        viewModelScope.launch {
            deleteTipoPenalidadUseCase(id)
            _state.update { it.copy(message = "Tipo de penalidad eliminada") }
        }
    }
}