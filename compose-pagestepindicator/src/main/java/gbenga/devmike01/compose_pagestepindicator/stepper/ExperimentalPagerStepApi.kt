package gbenga.devmike01.compose_pagestepindicator.stepper

import kotlin.reflect.KClass


@RequiresOptInV2(message = "PageStepIndicator contains pager api which experimental. " +
        "The Pager API may be changed in the future.")
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalPageStepIndicatorApi



@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
@SinceKotlin("1.3")
annotation class RequiresOptInV2(
    val message: String = "",
    val level: Level = Level.ERROR
) {
    /**
     * Severity of the diagnostic that should be reported on usages which did not explicitly opted into
     * the API either by using [OptIn] or by being annotated with the corresponding marker annotation.
     */
    enum class Level {
        /** Specifies that a warning should be reported on incorrect usages of this API. */
        WARNING,

        /** Specifies that an error should be reported on incorrect usages of this API. */
        ERROR,
    }
}


@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.LOCAL_VARIABLE,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.FILE,
    AnnotationTarget.TYPEALIAS
)
@Retention(AnnotationRetention.SOURCE)
@SinceKotlin("1.3")
annotation class OptInV2(
    vararg val markerClass: KClass<out Annotation>
)