package edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades

import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository


class GetTipoPenalidadUseCase (
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int): TipoPenalidad? = repository.getTipoPenalidad(id)
}