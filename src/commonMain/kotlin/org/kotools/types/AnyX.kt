package org.kotools.types

internal fun hashCodeOf(first: Any, vararg others: Any): Int {
    val prime = 31
    var result: Int = prime + first.hashCode()
    others.forEach { result = prime * result + it.hashCode() }
    return result
}