/*
 * Copyright 2023 Kotools S.A.S.U.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.internal

import kotlinx.serialization.SerializationException
import kotlin.jvm.JvmSynthetic
import kotlin.reflect.KClass

/** Throws a [SerializationException] with the specified [message]. */
@JvmSynthetic
internal fun serializationError(message: ErrorMessage): Nothing =
    throw SerializationException("$message")

/**
 * Throws an [IllegalStateException] indicating that creating an instance of
 * type [T] with the specified [value] shouldn't fail.
 */
@JvmSynthetic
internal inline fun <reified T : Any> unexpectedCreationError(
    value: Any
): Nothing {
    val kClass: KClass<T> = T::class
    val typeName: String = kClass.simpleName
        ?: error("Getting simple name of '$kClass' shouldn't return 'null'.")
    error("Creating an instance of $typeName with '$value' shouldn't fail.")
}
