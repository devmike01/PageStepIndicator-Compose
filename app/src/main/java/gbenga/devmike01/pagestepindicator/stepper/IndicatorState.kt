package gbenga.devmike01.pagestepindicator.stepper

import android.graphics.Color
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

class IndicatorState(override var value: PageStepIndicatorState) : MutableState<PageStepIndicatorState> {
    override fun component1(): PageStepIndicatorState {
        return value
    }

    override fun component2(): (PageStepIndicatorState) -> Unit {
        TODO("Not yet implemented")
    }
}

@Composable
fun rememberIndicatorState(value: PageStepIndicatorState? =null): IndicatorState{

    return remember{
        IndicatorState( value ?: ChangePageState(activeColor = Color.GREEN))
    }
}
