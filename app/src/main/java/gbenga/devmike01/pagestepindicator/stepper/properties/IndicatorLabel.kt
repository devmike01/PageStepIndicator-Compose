package gbenga.devmike01.pagestepindicator.stepper.properties

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize


@Parcelize
data class IndicatorLabel(val text: String, val color: Int = android.graphics.Color.DKGRAY) : Parcelable