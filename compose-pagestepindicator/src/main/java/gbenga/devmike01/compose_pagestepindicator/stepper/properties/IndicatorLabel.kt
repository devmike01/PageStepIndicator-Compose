package gbenga.devmike01.compose_pagestepindicator.stepper.properties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class IndicatorLabel(val text: String, val color: Int = android.graphics.Color.DKGRAY) : Parcelable