package edu.ucne.registro_estudiantes.data.local.mapper

import edu.ucne.registro_estudiantes.data.local.entities.TipoPenalidadEntity
import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad


fun TipoPenalidadEntity.toDomain(): TipoPenalidad = TipoPenalidad(
    tipoId = tipoId,
    nombre = nombre,
    descripcion = descripcion,
    puntosdescuento = puntosdescuento

)
fun TipoPenalidad.toEntity(): TipoPenalidadEntity = TipoPenalidadEntity(
    tipoId = tipoId,
    nombre = nombre,
    descripcion = descripcion,
    puntosdescuento = puntosdescuento
)