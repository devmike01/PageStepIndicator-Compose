package gbenga.devmike01.compose_pagestepindicator

import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import gbenga.devmike01.pagestepindicator.stepper.ChangePageEvent
import gbenga.devmike01.pagestepindicator.stepper.NoEvent
import gbenga.devmike01.pagestepindicator.stepper.PageStepIndicatorState
import android.graphics.Color as Color2

@Composable
fun PageStepIndicator(labels: List<String> = emptyList(), inActiveColor: Int = Color2.GRAY,
                      strokeWidth: Float =10F,
                      textPaintColor: Int =android.graphics.Color.BLACK,
                      stepPaintColor: Color= Color.Green,
                      strokePaintColor: Color = Color.Magenta,
                      state: MutableState<PageStepIndicatorState> = remember {
                          mutableStateOf(NoEvent)
                      },
                      onChanged: ((position: Int) -> Unit)? = null) {



    PaintStepIndicators(textPaintColor = textPaintColor,
        stepPaintColor = stepPaintColor,
        strokeWidth = strokeWidth,
        strokePaintColor = strokePaintColor,
        event = state.value,
        applyPaint = { textPaint, stepPaint,
                       stepBorderPaint,stepCountPaint ->
            DrawPageStepIndicator(
                textPaint = textPaint,
                stepPaint = stepPaint,
                stepBorderPaint = stepBorderPaint,
                stepCountPaint = stepCountPaint
            )
        }
    )

}

@Composable
private fun PageIndicatorEvent(){

}

@Composable
private fun PaintStepIndicators(strokeWidth: Float =10F,
                                textPaintColor: Int,
                                stepPaintColor: Color,
                                applyPaint: @Composable (textPaint: android.graphics.Paint,
                                                         stepPaint: Paint,
                                                         stepBorderPaint: Paint,
                                                         stepCountPaint: android.graphics.Paint
                                ) -> Unit,
                                event : PageStepIndicatorState,
                                strokePaintColor: Color){

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 29F
        color = textPaintColor
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val stepCountPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 29F
        color = textPaintColor
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val stepPaint = Paint().apply {
        isAntiAlias = true
        this.strokeWidth = strokeWidth
        color = stepPaintColor
        style = PaintingStyle.Fill
    }

    val stepBorderPaint = Paint().apply {
        isAntiAlias = true
        this.strokeWidth = strokeWidth
        color = strokePaintColor
        style = PaintingStyle.Stroke
    }

    applyPaint(textPaint = textPaint, stepCountPaint = stepCountPaint,
        stepPaint= stepPaint,
        stepBorderPaint = stepBorderPaint)

}

@Composable
private fun DrawPageStepIndicator(
    textPaint: android.graphics.Paint,
    stepPaint: Paint,
    stepBorderPaint : Paint,
    stepCountPaint: android.graphics.Paint,
    circleRadius: Float = 50f,
             stepColor: Color = Color.Red,
                      labels: List<String> = listOf("Rice", "Agbado", "Biscuit", "Water"),
                      strokeWidth: Float =10F,
                      pathHeight : Float = 10F,
             canvasModifier: Modifier =Modifier){

    
    Canvas(modifier = canvasModifier){

        val labelTopSpace = 50f

        val canvasWidth = size.width
        val stepDistance = (canvasWidth / labels.size)
        val canvasHeight = (circleRadius.times(2))
            .plus(strokeWidth).plus(labelTopSpace)
        val stepCount = labels.size-1;

        val lineWidth = stepDistance -  circleRadius

        // Draw component on canvas
        drawIntoCanvas {
            labels.forEachIndexed{ i, label ->

                val pos = "${i +1}"

                val labelBounds = Rect()
                textPaint.getTextBounds(label, 0, label.length, labelBounds)
                val centerX = labelBounds.centerX()

                val posBounds = Rect()
                textPaint.getTextBounds(pos, 0, pos.length, posBounds)
                val posCenterX = posBounds.centerX()
                val posCenterY = posBounds.centerY()

                val circleOffset =  Offset(x = stepDistance +(if(i == 0){
                    i.toFloat()
                }else{
                    (lineWidth* i)
                }), y = (canvasHeight /2))
                val circleBorderX = strokeWidth + circleRadius

                // Draw filled circle
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
                it.nativeCanvas.drawText(label,
                    (circleOffset.x- (centerX)),
                    circleOffset.y+ (circleRadius + labelTopSpace),
                    textPaint
                )
            }

        }
    }
}

@Composable
@Preview()
fun PageStepIndicatorPreview(){
  //  PageStepIndicator()
}