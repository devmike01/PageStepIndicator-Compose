package gbenga.devmike01.pagestepindicator.stepper

sealed interface PageStepIndicatorState

data class ChangePageEvent(val activeColor: Int): PageStepIndicatorState


object NoEvent: PageStepIndicatorState
