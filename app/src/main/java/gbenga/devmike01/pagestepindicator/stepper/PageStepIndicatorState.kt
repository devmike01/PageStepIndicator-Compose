package gbenga.devmike01.pagestepindicator.stepper

sealed interface PageStepIndicatorState

data class ChangePageState(val activeColor: Int,
                           val position: Int= 0): PageStepIndicatorState



