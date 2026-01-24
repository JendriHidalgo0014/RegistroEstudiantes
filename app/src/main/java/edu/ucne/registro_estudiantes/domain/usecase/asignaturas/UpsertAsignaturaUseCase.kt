package edu.ucne.registro_estudiantes.domain.usecase

import edu.ucne.registro_estudiantes.domain.model.Asignatura
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateAula
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateCodigo
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateCreditos
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateNombre
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.validateNombreDuplicado

class UpsertAsignaturaUseCase(
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(asignatura: Asignatura, nombresExistentes: List<String>): Result<Unit> {
        val c = validateCodigo(asignatura.codigo)

        if (!c.isValid) return Result.failure(IllegalArgumentException(c.error))

        val n = validateNombre(asignatura.nombre)
        if (!n.isValid) return Result.failure(IllegalArgumentException(n.error))

        val a = validateAula(asignatura.aula)
        if (!a.isValid) return Result.failure(IllegalArgumentException(a.error))

        val cr = validateCreditos(asignatura.creditos.toString())
        if (!cr.isValid) return Result.failure(IllegalArgumentException(cr.error))

        val dup = validateNombreDuplicado(asignatura.nombre, nombresExistentes)
        if (!dup.isValid) return Result.failure(IllegalArgumentException(dup.error))

        return runCatching { repository.upsert(asignatura) }
    }
}