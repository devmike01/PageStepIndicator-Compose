package gbenga.devmike01.compose_pagestepindicator.proptests

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import gbenga.devmike01.compose_pagestepindicator.stepper.*
import gbenga.devmike01.compose_pagestepindicator.stepper.properties.IndicatorLabel
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IndicatorPropertyTest {


    private val indicatorState = IndicatorState(value = ChangePageState(
        position = 4
    ))

    private val mIndicatorProperty=  IndicatorProperty(labels = listOf(
        IndicatorLabel("Title1"),
        IndicatorLabel("Title2"),
        IndicatorLabel("Title3"),
    ))


    @Test
    fun testIndicatorState(){
        val changeState =indicatorState.value as ChangePageState
        Assert.assertEquals(changeState.position, 4)
    }


    @Test
    fun testColorIndicators(){
        val indicatorColor = mIndicatorProperty.copy(
            color = IndicatorColor(activeColor = Color.Blue.int(),
            strokeActiveColor = Color.Black.int(),
                inActiveColor = Color.Gray.int()
            )
        ).color

        Assert.assertEquals(indicatorColor.activeColor, Color.Blue.int())
        Assert.assertEquals(indicatorColor.strokeActiveColor, Color.Black.int())
        Assert.assertEquals(indicatorColor.inActiveColor, Color.Gray.int())
    }


    @Test
    fun testLabelTitle(){
        val firstVal = mIndicatorProperty.labels.first().text
        Assert.assertEquals(firstVal, "Title1")
        val firstVal3 = mIndicatorProperty.labels.last().text
        Assert.assertEquals(firstVal3, "Title3")
    }
}

fun Color.int(): Int{
    return this.value.toInt()
}