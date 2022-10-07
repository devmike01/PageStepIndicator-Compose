package gbenga.devmike01.compose_pagestepindicator.stepper

sealed interface PageStepIndicatorState

data class ChangePageState(val activeColor: Int,
                           val position: Int= 0): PageStepIndicatorState



