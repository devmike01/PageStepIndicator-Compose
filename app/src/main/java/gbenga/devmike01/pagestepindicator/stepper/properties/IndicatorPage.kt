package gbenga.devmike01.pagestepindicator.stepper.properties

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IndicatorPage(val prevPage: Int =-1, val curPage: Int=0) : Parcelable {
    val isSelected : Boolean = prevPage  != curPage
}