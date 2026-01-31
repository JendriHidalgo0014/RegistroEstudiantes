package edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades

import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow

class ObserveTipoPenalidadUseCase (
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> = repository.observeTipoPenalidades()
}