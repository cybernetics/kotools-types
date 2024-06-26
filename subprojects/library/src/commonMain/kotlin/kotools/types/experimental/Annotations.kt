package kotools.types.experimental

import org.kotools.types.internal.DeprecatedAsErrorSince
import org.kotools.types.internal.DeprecatedAsWarningSince
import org.kotools.types.internal.HiddenSince
import org.kotools.types.internal.KotoolsTypesVersion
import org.kotools.types.internal.Since
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.annotation.AnnotationTarget.PROPERTY
import kotlin.annotation.AnnotationTarget.TYPEALIAS

private const val OPT_IN_MESSAGE: String = "This declaration is experimental" +
        " and can be incompatibly changed in the future."

/** Marks declarations that are still **experimental** in the API. */
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Since(KotoolsTypesVersion.V4_4_0)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalKotoolsTypesApi

// -------------------------- Deprecated annotations ---------------------------

private const val RECOMMENDED_ANNOTATION: String = "ExperimentalKotoolsTypesApi"
private const val RECOMMENDED_ANNOTATION_PACKAGE: String =
    "kotools.types.experimental"

/** Marks declarations that are still **experimental** in the collection API. */
@Deprecated(
    "Use the $RECOMMENDED_ANNOTATION annotation instead.",
    ReplaceWith(
        RECOMMENDED_ANNOTATION,
        "$RECOMMENDED_ANNOTATION_PACKAGE.$RECOMMENDED_ANNOTATION"
    ),
    DeprecationLevel.HIDDEN
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_4_0)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_0)
@HiddenSince(KotoolsTypesVersion.V4_5_1)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalCollectionApi

/** Marks declarations that are still **experimental** in the number API. */
@Deprecated(
    "Use the $RECOMMENDED_ANNOTATION annotation instead.",
    ReplaceWith(
        RECOMMENDED_ANNOTATION,
        "$RECOMMENDED_ANNOTATION_PACKAGE.$RECOMMENDED_ANNOTATION"
    ),
    DeprecationLevel.HIDDEN
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_4_0)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_0)
@HiddenSince(KotoolsTypesVersion.V4_5_1)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalNumberApi

/** Marks declarations that are still **experimental** in the range API. */
@Deprecated(
    "Use the $RECOMMENDED_ANNOTATION annotation instead.",
    ReplaceWith(
        RECOMMENDED_ANNOTATION,
        "$RECOMMENDED_ANNOTATION_PACKAGE.$RECOMMENDED_ANNOTATION"
    ),
    DeprecationLevel.HIDDEN
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_4_0)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_0)
@HiddenSince(KotoolsTypesVersion.V4_5_1)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalRangeApi

/** Marks declarations that are still **experimental** in the result API. */
@Deprecated(
    "Use the $RECOMMENDED_ANNOTATION annotation instead.",
    ReplaceWith(
        RECOMMENDED_ANNOTATION,
        "$RECOMMENDED_ANNOTATION_PACKAGE.$RECOMMENDED_ANNOTATION"
    ),
    DeprecationLevel.HIDDEN
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_4_0)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_0)
@HiddenSince(KotoolsTypesVersion.V4_5_1)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalResultApi

/** Marks declarations that are still **experimental** in the text API. */
@Deprecated(
    "Use the $RECOMMENDED_ANNOTATION annotation instead.",
    ReplaceWith(
        RECOMMENDED_ANNOTATION,
        "$RECOMMENDED_ANNOTATION_PACKAGE.$RECOMMENDED_ANNOTATION"
    ),
    DeprecationLevel.HIDDEN
)
@DeprecatedAsWarningSince(KotoolsTypesVersion.V4_4_0)
@DeprecatedAsErrorSince(KotoolsTypesVersion.V4_5_0)
@HiddenSince(KotoolsTypesVersion.V4_5_1)
@MustBeDocumented
@RequiresOptIn(OPT_IN_MESSAGE)
@Retention(BINARY)
@Target(CLASS, FUNCTION, PROPERTY, TYPEALIAS)
public annotation class ExperimentalTextApi
