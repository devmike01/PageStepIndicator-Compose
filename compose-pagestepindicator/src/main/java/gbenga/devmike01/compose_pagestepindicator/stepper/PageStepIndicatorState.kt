package gbenga.devmike01.compose_pagestepindicator.stepper

sealed interface PageStepIndicatorState

data class ChangePageState(val position: Int= 0): PageStepIndicatorState



