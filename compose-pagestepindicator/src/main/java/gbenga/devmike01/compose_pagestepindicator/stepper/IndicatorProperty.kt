package gbenga.devmike01.compose_pagestepindicator.stepper

import android.graphics.Color
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import gbenga.devmike01.compose_pagestepindicator.stepper.properties.IndicatorLabel
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndicatorProperty( val color: IndicatorColor = IndicatorColor(),
                                  val dimensions: StepDimensions = StepDimensions(),
                             val labels: List<IndicatorLabel> = emptyList()
) : Parcelable

@Composable
fun rememberIndicatorPropertyState(property: IndicatorProperty? = null): MutableState<IndicatorProperty>{
    return rememberSaveable {
        mutableStateOf(property ?: IndicatorProperty(
            color = IndicatorColor( ),
            dimensions = StepDimensions(),
            labels = emptyList()
        ))
    }
}


@Parcelize
data class IndicatorColor(
    val activeColor: Int = Color.RED,
    val inActiveColor: Int = Color.GRAY,
    val strokeActiveColor: Int = Color.CYAN,
    val strokeInActiveColor: Int = Color.GRAY,
    val pathColor : Int = Color.BLUE,
    val countColor: Int = Color.WHITE ) : Parcelable

@Parcelize
data class StepDimensions(   val strokeWidth: Float = 20f,
                             val strokeThickness: Float = 6f,) : Parcelable

