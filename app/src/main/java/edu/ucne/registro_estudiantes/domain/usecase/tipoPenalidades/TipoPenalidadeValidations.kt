package edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades

private const val Campo = "Este campo es obligatorio"

data class TipoPenalidadeValidationResult(
    val isValid: Boolean,
    val error: String? = null
)
fun validateDescripcion(value: String): TipoPenalidadeValidationResult {
    if (value.isBlank()) return TipoPenalidadeValidationResult(false, Campo)
    return TipoPenalidadeValidationResult(true)
}

fun validateNombre(value: String): TipoPenalidadeValidationResult {
    if (value.isBlank()) return TipoPenalidadeValidationResult(false, Campo)
    return TipoPenalidadeValidationResult(true)
}

fun validatePuntosDescuento(value: String): TipoPenalidadeValidationResult {
    if (value.isBlank()) return TipoPenalidadeValidationResult(false, Campo)
    val puntos = value.toIntOrNull() ?: return TipoPenalidadeValidationResult(false, "Debe ingresar un numero valido")
    if (puntos <= 0) return TipoPenalidadeValidationResult(false, "Los puntos deben ser mayor a cero")
    return TipoPenalidadeValidationResult(true)
}

fun validateNombreDuplicado(nombre: String, nombresExistentes: List<String>): TipoPenalidadeValidationResult {
    if (nombresExistentes.contains(nombre)) {
        return TipoPenalidadeValidationResult(false, "Ya existe un tipo de penalidad registrado con este nombre")
    }
    return TipoPenalidadeValidationResult(true)
}