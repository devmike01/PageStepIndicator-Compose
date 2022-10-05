package gbenga.devmike01.pagestepindicator.stepper

import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import gbenga.devmike01.pagestepindicator.stepper.properties.IndicatorLabel

@OptIn(ExperimentalPagerApi::class)
@OptInV2(ExperimentalPageStepIndicatorApi::class)
@Composable
fun PageStepIndicator(
    propertyState: MutableState<IndicatorProperty>,
    indicatorState: IndicatorState = rememberIndicatorState(),
    canvasModifier: Modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
    pageModifier: Modifier = Modifier.fillMaxSize(),
    content: (@Composable (
        indicatorState: MutableState<PageStepIndicatorState>,
       // pagerState: PagerState
    ) -> Unit)? = null,
) {

    val properties = propertyState.value
    Column(modifier = pageModifier) {

        PaintStepIndicators(
              colorProp = propertyState.value.color,
            applyPaint = { textPaint, stepPaint,
                           stepBorderPaint,stepCountPaint, color ->
                DrawPageStepIndicator(
                    textPaint = textPaint,
                    stepPaint = stepPaint,
                    stepBorderPaint = stepBorderPaint,
                    stepCountPaint = stepCountPaint,
                    canvasModifier = canvasModifier,
                    onStepClick = {
                        indicatorState.value = ChangePageState(
                            activeColor = android.graphics.Color.CYAN
                        )
                    },
                    indicatorDimen = properties.dimensions,
                    labels = properties.labels
                )
            }
        )

        if(content != null){
            content(indicatorState)
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
                                                         colorProp: IndicatorColor
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
        color = Color(colorProp.activeColor)
        style = PaintingStyle.Fill
    }

    val stepBorderPaint = Paint().apply {
        isAntiAlias = true
        this.strokeWidth = strokeWidth
        color = Color(colorProp.strokeActiveColor)
        style = PaintingStyle.Stroke
    }

    applyPaint(textPaint = textPaint, stepCountPaint = stepCountPaint,
        stepPaint= stepPaint,
        stepBorderPaint = stepBorderPaint, colorProp = colorProp)

}

@Composable
private fun DrawPageStepIndicator(
    indicatorDimen: StepDimensions,
    textPaint: android.graphics.Paint,
    stepPaint: Paint,
    stepBorderPaint : Paint,
    stepCountPaint: android.graphics.Paint,
    onStepClick: (position: Int) -> Unit,
    stepCoords: ArrayList<Offset> = arrayListOf<Offset>(),
    circleRadius: Float = 50f,
    labels: List<IndicatorLabel>, //= listOf(IndicatorLabel("Rice"), "Agbado", "Biscuit", "Water"),
    strokeWidth: Float =10F,
    pathHeight : Float = 10F,
    canvasModifier: Modifier){

    Canvas(
        modifier = canvasModifier
            .pointerInput(key1 = Unit, block = {
                detectTapGestures(
                    onTap = {tapOffset ->
                        onStepClick(calculatePosition(stepCoords,
                            tapOffset.x, tapOffset.y, circleRadius))

                        Log.d("drawIntoCanvas001",
                            "drawIntoCanvas => ${calculatePosition(stepCoords,
                                tapOffset.x, tapOffset.y, circleRadius)}")

                    }
                )
                // onStepClick(steps)
            }),
    ){

        val labelTopSpace = 50f

        val canvasWidth = size.width
        val stepDistance = (canvasWidth / labels.size)//1.5f
        val canvasHeight = (circleRadius.times(2))
            .plus(strokeWidth).plus(labelTopSpace)
        val stepCount = labels.size-1;

        val lineWidth = stepDistance -  circleRadius

                // Draw component on canvas
        drawIntoCanvas {
            labels.forEachIndexed{ i, label ->

                val pos = "${i +1}"

                // Paint steps and texts
                textPaint.color = label.color

                val labelBounds = Rect()
                //textPaint.getTextBounds(label.text, 0, labels.size, labelBounds)
                val centerX = labelBounds.centerX()

                val posBounds = Rect()
                textPaint.getTextBounds(pos, 0, pos.length, posBounds)
                val posCenterX = posBounds.centerX()
                val posCenterY = posBounds.centerY()

                var circleOffset =  Offset(x = (lineWidth) +(if(i == 0){
                    i.toFloat()
                }else{
                    (lineWidth* i)
                }), y = (canvasHeight /1.5f))

                if(i ==0 ){
                    val circleOffsetX = lineWidth
                    circleOffset = circleOffset.copy(x = lineWidth)
                }
                val circleBorderX = strokeWidth + circleRadius


                // Draw filled circle
               // offsetClicked.value = circleOffset
                it.drawCircle(
                    center = circleOffset,
                    radius = circleRadius - strokeWidth,
                    stepPaint
                )

                // Draw circle border
                it.drawCircle(
                    center = circleOffset,
                    radius = circleRadius,
                    stepBorderPaint
                )

                stepCoords.add(circleOffset)

                Log.d("circleOffset", "$circleOffset")

                // Draw connector
                if(i < stepCount){
                    drawLine(
                        strokeWidth = pathHeight,
                        color = stepBorderPaint.color, //pathWidth
                        start = Offset(x =  circleOffset.x +circleBorderX, y = circleOffset.y),
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

                //Draw label text
                it.nativeCanvas.drawText(label.text,
                    (circleOffset.x- (centerX)),
                    circleOffset.y+ (circleRadius + labelTopSpace),
                    textPaint
                )
            }

        }
    }
}


@Synchronized
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
            return ((coordX / firstCord)-1).toInt()
        }
    }
    return -1
}


@OptIn(ExperimentalPagerApi::class)
@OptInV2(ExperimentalPageStepIndicatorApi::class)
@Composable
@Preview()
fun PageStepIndicatorPreview(){
   // PageStepIndicator()
}