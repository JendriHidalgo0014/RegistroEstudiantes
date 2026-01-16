package edu.ucne.registro_estudiantes.data.local.mapper

import edu.ucne.registro_estudiantes.data.local.entities.EstudianteEntity
import edu.ucne.registro_estudiantes.domain.model.Estudiante

fun EstudianteEntity.toDomain(): Estudiante = Estudiante(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad

)

fun Estudiante.toEntity(): EstudianteEntity = EstudianteEntity(
    estudianteId = estudianteId,
    nombres = nombres,
    email = email,
    edad = edad
)

