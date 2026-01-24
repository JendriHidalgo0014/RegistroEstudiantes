package edu.ucne.registro_estudiantes.data.local.mapper

import edu.ucne.registro_estudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registro_estudiantes.domain.model.Asignatura
fun AsignaturaEntity.toDomain(): Asignatura = Asignatura(
    asignaturaId = asignaturaId,
    codigo = codigo,
    nombre = nombre,
    aula = aula,
    creditos = creditos

)
fun Asignatura.toEntity(): AsignaturaEntity = AsignaturaEntity(
    asignaturaId = asignaturaId,
    codigo = codigo,
    nombre = nombre,
    aula = aula,
    creditos = creditos
)