package gbenga.devmike01.compose_pagestepindicator.stepper

import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import gbenga.devmike01.compose_pagestepindicator.stepper.properties.IndicatorLabel
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import kotlin.math.nextUp
import kotlin.math.roundToInt

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PageStepIndicator(
    propertyState: MutableState<IndicatorProperty>,
    pagerState: PagerState,
    indicatorState: IndicatorState = rememberIndicatorState(),
    stepColor : IndicatorColor = IndicatorColor(),
    content: (@Composable (
        indicatorState: MutableState<PageStepIndicatorState>,
        pagerState: PagerState
    ) -> Unit)? = null,
) {


    val coroutineScope = rememberCoroutineScope();

    val properties = propertyState.value

    Column(modifier = Modifier,) {

        PaintStepIndicators(
              colorProp = propertyState.value.color,
            applyPaint = { textPaint, stepPaint,
                           stepBorderPaint, stepCountPaint,
                           color ->

                DrawPageStepIndicator(
                    textPaint = textPaint,
                    stepPaint = stepPaint,
                    stepBorderPaint = stepBorderPaint,
                    stepCountPaint = stepCountPaint,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(),
                    onStepClick = {
                                  coroutineScope.launch {
                                      pagerState.animateScrollToPage(it);
                                  }
                    },
                    indicatorDimen = properties.dimensions,
                    labels = properties.labels,
                    selectedPosition = pagerState.currentPage,
                    stepColor = stepColor
                )
            }
        )

        if(content != null){
            content(indicatorState, pagerState)
        }
    }

}

@Composable
private fun PaintStepIndicators(
    colorProp : IndicatorColor,
                                applyPaint: @Composable (textPaint: android.graphics.Paint,
                                                         stepPaint: Paint,
                                                         stepBorderPaint: Paint,
                                                         stepCountPaint: android.graphics.Paint,
                                                         colorProp: IndicatorColor,
                                ) -> Unit){

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 29F
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val stepCountPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 29F
        color = colorProp.countColor
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val stepPaint = Paint().apply {
        isAntiAlias = true
        style = PaintingStyle.Fill
    }

    val stepBorderPaint = Paint().apply {
        isAntiAlias = true
        this.strokeWidth = 10f
        //color = Color(colorProp.)
        style = PaintingStyle.Stroke
    }

    applyPaint(textPaint, stepPaint, stepBorderPaint,
        stepCountPaint, colorProp)

}

@Composable
private fun DrawPageStepIndicator(
    stepColor: IndicatorColor,
    indicatorDimen: StepDimensions,
    textPaint: android.graphics.Paint,
    stepPaint: Paint,
    stepBorderPaint : Paint,
    stepCountPaint: android.graphics.Paint,
    onStepClick: (position: Int) -> Unit,
    stepCoords: ArrayList<Offset> = arrayListOf<Offset>(),
    circleRadius: Float = 50f,
    labels: List<IndicatorLabel>,
    pathHeight : Float = 10F,
    selectedPosition: Int,
    modifier: Modifier
){

    val strokeWidth = indicatorDimen.strokeWidth
    Canvas(
        modifier = modifier
            .pointerInput(key1 = Unit, block = {
                detectTapGestures(
                    onTap = {tapOffset ->
                        calculatePosition(stepCoords,
                            tapOffset.x, tapOffset.y, circleRadius).apply {
                                if (this > -1){
                                    onStepClick(this)
                                }
                        }


                    }
                )
            }),
    ){
        val labelTopSpace = 50f

        // WORKING ON THIS
        val canvasWidth = size.width

        val stepDistance = (canvasWidth / labels.size)//1.5f
        val canvasHeight = (circleRadius.times(2))
            .plus(strokeWidth).plus(labelTopSpace)
        val stepCount = labels.size-1

        val lineWidth = stepDistance -  circleRadius
                // Draw component on canvas
        var drawingsWidth = ((strokeWidth)
            .plus(circleRadius *2)
            .plus(lineWidth)).times(stepCount-1);

        val firstStepX = drawingsWidth.minus(canvasWidth)

        drawIntoCanvas {
            labels.forEachIndexed{ i, label ->

                val pos = "${i +1}"

                // Paint steps and texts
                textPaint.color = label.color

                val labelBounds = Rect()
                textPaint.getTextBounds(label.text, 0, label.text.length, labelBounds)
                val centerX = labelBounds.centerX()

                val posBounds = Rect()
                textPaint.getTextBounds(pos, 0, pos.length, posBounds)
                val posCenterX = posBounds.centerX()
                val posCenterY = posBounds.centerY()

                val circleOffset =   Offset(x = (posCenterX
                    .plus(strokeWidth)
                    .times(2)) +(if(i == 0){
                    firstStepX
                }else{
                     (lineWidth* i)
                }), y = (canvasHeight /1.5f)) //16

                val circleBorderX = strokeWidth + circleRadius

                stepCoords.add(circleOffset)

                stepPaint.color = Color(if(i == selectedPosition)
                    stepColor.activeColor else
                        stepColor.inActiveColor)

                stepBorderPaint.color = Color(if(i <= selectedPosition)
                    stepColor.strokeActiveColor else
                    stepColor.strokeInActiveColor)

                stepPaint.color = Color(if(i <= selectedPosition)
                    stepColor.activeColor else
                    stepColor.inActiveColor)

                it.drawCircle(
                    center = circleOffset,
                    radius = circleRadius - (strokeWidth/2f),
                    stepPaint
                )

                // Draw circle border
                it.drawCircle(
                    center = circleOffset,
                    radius = circleRadius,
                    stepBorderPaint
                )

                // Draw connector
                if(i < stepCount){
                    drawLine(
                        cap = StrokeCap.Round,
                        strokeWidth = pathHeight,
                        color = if(i < selectedPosition){
                            Color.Cyan
                        }else{
                            Color.Black
                        },
                        start = Offset(x =  circleOffset.x +circleBorderX,
                            y = circleOffset.y),
                        end = Offset(x = (circleOffset.x -(circleRadius+strokeWidth)) +lineWidth// +lineWidth
                            , y = circleOffset.y)
                    )
                }

                //Draw step position text
                it.nativeCanvas.drawText(pos,
                    circleOffset.x -posCenterX,
                    circleOffset.y -posCenterY,
                    stepCountPaint
                )

                it.nativeCanvas.drawText(label.text,
                    (circleOffset.x- (centerX)),
                    circleOffset.y+ (circleRadius + labelTopSpace),
                    textPaint
                )
            }

        }
    }
}


fun calculatePosition(coords: List<Offset>, coordX: Float, coordY: Float,
                      radius: Float): Int {

    val firstCord = coords.first().x

    for(element in coords){
        val xPos = element.x
        val yPos = element.y

        val startX = xPos -radius
        val endX = xPos + radius

        val startY = yPos -radius
        val endY = yPos + radius

        val yClicked = (coordY < endY).and(coordY > startY)
        val xClicked = (coordX < endX).and(coordX > startX)

        if((yClicked).and(xClicked)){
            val position = ((coordX / firstCord)-1)
                .nextUp();
             return position.roundToInt()
        }
    }
    return -1
}

