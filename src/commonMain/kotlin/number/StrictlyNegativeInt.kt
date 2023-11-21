/*
 * Copyright 2022-2023 Loïc Lamarque.
 * Use of this source code is governed by the MIT license.
 */

package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.types.ExperimentalSinceKotoolsTypes
import kotools.types.Package
import kotools.types.SinceKotoolsTypes
import kotools.types.experimental.ExperimentalNumberApi
import kotools.types.experimental.ExperimentalRangeApi
import kotools.types.internal.unexpectedCreationError
import kotools.types.range.NotEmptyRange
import kotools.types.range.notEmptyRangeOf
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun Int.isStrictlyNegative(): Boolean = this < 0

/**
 * Returns this number as an encapsulated [StrictlyNegativeInt], which may
 * involve rounding or truncation, or returns an encapsulated
 * [IllegalArgumentException] if this number is [positive][PositiveInt].
 */
@SinceKotoolsTypes("4.1")
public fun Number.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    runCatching { StrictlyNegativeInt(toInt()) }

/** Representation of negative integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotoolsTypes("1.1")
public value class StrictlyNegativeInt
internal constructor(private val value: Int) : NonZeroInt, NegativeInt {
    init {
        require(value.isStrictlyNegative()) { errorMessageFor(value) }
    }

    @SinceKotoolsTypes("4.0")
    override fun toInt(): Int = value

    @SinceKotoolsTypes("4.0")
    override fun toString(): String = "$value"

    /**
     * Contains declarations for holding or building a [StrictlyNegativeInt].
     */
    public companion object {
        /** The minimum value a [StrictlyNegativeInt] can have. */
        public val min: StrictlyNegativeInt by lazy(
            Int.MIN_VALUE.toStrictlyNegativeInt()::getOrThrow
        )

        /** The maximum value a [StrictlyNegativeInt] can have. */
        public val max: StrictlyNegativeInt by lazy(
            (-1).toStrictlyNegativeInt()::getOrThrow
        )

        /** The range of values a [StrictlyNegativeInt] can have. */
        @ExperimentalRangeApi
        @ExperimentalSinceKotoolsTypes("4.2")
        public val range: NotEmptyRange<StrictlyNegativeInt> by lazy {
            val start: StrictlyNegativeInt = Int.MIN_VALUE
                .toStrictlyNegativeInt()
                .getOrThrow()
            val end: StrictlyNegativeInt = (-1).toStrictlyNegativeInt()
                .getOrThrow()
            notEmptyRangeOf { start.inclusive to end.inclusive }
        }

        internal infix fun errorMessageFor(number: Number): NotBlankString =
            "Number should be strictly negative (tried with $number)."
                .toNotBlankString()
                .getOrThrow()

        /** Returns a random [StrictlyNegativeInt]. */
        @SinceKotoolsTypes("3.0")
        public fun random(): StrictlyNegativeInt = (min.value..max.value)
            .random()
            .toStrictlyNegativeInt()
            .getOrThrow()
    }
}

/** Returns the negative of this integer. */
@ExperimentalNumberApi
@ExperimentalSinceKotoolsTypes("4.2")
public operator fun StrictlyNegativeInt.unaryMinus(): StrictlyPositiveInt {
    val value: Int = -toInt()
    return value.toStrictlyPositiveInt()
        .getOrNull()
        ?: unexpectedCreationError<StrictlyPositiveInt>(value)
}

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyNegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .toStrictlyNegativeInt()
        .getOrNull()
        ?: throw SerializationException(
            "${StrictlyNegativeInt errorMessageFor value}"
        )
}
