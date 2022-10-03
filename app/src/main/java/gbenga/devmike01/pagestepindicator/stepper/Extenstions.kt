package gbenga.devmike01.compose_pagestepindicator

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.content.res.ResourcesCompat

val Int.color : Color get() = Color(this)