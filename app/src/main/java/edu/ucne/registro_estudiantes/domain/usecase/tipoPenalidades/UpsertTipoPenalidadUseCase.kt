package edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades

import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository


class UpsertTipoPenalidadUseCase (
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(tipoPenalidad: TipoPenalidad, nombresExistentes: List<String>): Result<Unit> {
        val descrip = validateDescripcion(tipoPenalidad.descripcion)

        if (!descrip.isValid) return Result.failure(IllegalArgumentException(descrip.error))

        val nomb = validateNombre(tipoPenalidad.nombre)
        if (!nomb.isValid) return Result.failure(IllegalArgumentException(nomb.error))

        val puntdesc = validatePuntosDescuento(tipoPenalidad.puntosdescuento.toString())
        if (!puntdesc.isValid) return Result.failure(IllegalArgumentException(puntdesc.error))

        val duplicado = validateNombreDuplicado(tipoPenalidad.nombre, nombresExistentes)
        if (!duplicado.isValid) return Result.failure(IllegalArgumentException(duplicado.error))

        return runCatching { repository.upsert(tipoPenalidad) }
    }
}