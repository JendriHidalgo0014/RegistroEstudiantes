package edu.ucne.registro_estudiantes.domain.usecase

import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import javax.inject.Inject

class UpsertEstudianteUseCase @Inject constructor(
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(estudiante: Estudiante, nombresExistentes: List<String>): Result<Unit> {
        val n = validateNombres(estudiante.nombres)
        if (!n.isValid) return Result.failure(IllegalArgumentException(n.error))

        val e = validateEmail(estudiante.email)
        if (!e.isValid) return Result.failure(IllegalArgumentException(e.error))

        val ed = validateEdad(estudiante.edad.toString())
        if (!ed.isValid) return Result.failure(IllegalArgumentException(ed.error))

        val dup = validateNombreDuplicado(estudiante.nombres, nombresExistentes)
        if (!dup.isValid) return Result.failure(IllegalArgumentException(dup.error))

        return runCatching { repository.upsert(estudiante) }
    }
}