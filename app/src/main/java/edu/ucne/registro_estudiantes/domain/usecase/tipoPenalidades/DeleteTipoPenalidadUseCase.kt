package edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades

import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository

class DeleteTipoPenalidadUseCase (
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}