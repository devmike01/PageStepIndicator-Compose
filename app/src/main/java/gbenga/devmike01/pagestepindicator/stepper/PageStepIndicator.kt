package gbenga.devmike01.pagestepindicator.stepper

import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.concurrent.Flow
import android.graphics.Color as Color2

@Composable
fun PageStepIndicator(labels: List<String> = emptyList(), inActiveColor: Int = Color2.GRAY,
                      strokeWidth: Float =10F,
                      textPaintColor: Int =android.graphics.Color.BLACK,
                      stepPaintColor: Color= Color.Green,
                      strokePaintColor: Color = Color.Magenta,
                      canvasModifier: Modifier = Modifier
                          .height(100.dp)
                          .fillMaxWidth(),
                      onChanged: ((position: Int) -> Unit)? = null) {

    val state: MutableState<PageStepIndicatorState> = remember {
        mutableStateOf(NoEvent)
    }


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
                stepCountPaint = stepCountPaint,
                canvasModifier = canvasModifier,
                onStepClick = {}
            )
        }
    )

}
//
//@Composable
//private fun PageIndicatorEvent(event : PageStepIndicatorState, pageChanged : () ->){
//    when(event){
//        is ChangePageEvent ->{
//
//        }
//        else ->{
//            // Nothing
//        }
//    }
//}

@Composable
private fun PaintStepIndicators(strokeWidth: Float =10F,
                                textPaintColor: Int,
                                stepPaintColor: Color,
                                event : PageStepIndicatorState,
                                applyPaint: @Composable (textPaint: android.graphics.Paint,
                                                         stepPaint: Paint,
                                                         stepBorderPaint: Paint,
                                                         stepCountPaint: android.graphics.Paint
                                ) -> Unit,
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

private suspend fun PointerInputScope.handleClicks(){
    detectTapGestures {
        Log.d("handleClicks", "$it")
    }
}

@Composable
private fun DrawPageStepIndicator(
    textPaint: android.graphics.Paint,
    stepPaint: Paint,
    stepBorderPaint : Paint,
    stepCountPaint: android.graphics.Paint,
    onStepClick: (position: Int) -> Unit,
    stepCoords: ArrayList<Offset> = arrayListOf<Offset>(),
    circleRadius: Float = 50f,
    stepColor: Color = Color.Red,
    labels: List<String> = listOf("Rice", "Agbado", "Biscuit", "Water"),
    strokeWidth: Float =10F,
    pathHeight : Float = 10F,
    canvasModifier: Modifier){

    Canvas(
        modifier = canvasModifier
            .background(Color.Red)
            .pointerInput(key1 = Unit, block = {
                detectTapGestures(
                    onTap = {tapOffset ->
                        val clickAreaRadius = circleRadius.times(2)
                        val baseCord = stepCoords.first()

                        val xCoords: List<Float> = stepCoords.map { it.x }

                        Log.d("drawIntoCanvas001",
                            "drawIntoCanvas => ${calculateIndexV3(xCoords,
                                tapOffset.x, circleRadius)}")

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

                val labelBounds = Rect()
                textPaint.getTextBounds(label, 0, label.length, labelBounds)
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
                it.nativeCanvas.drawText(label,
                    (circleOffset.x- (centerX)),
                    circleOffset.y+ (circleRadius + labelTopSpace),
                    textPaint
                )
            }

        }
    }
}


@Synchronized
fun calculateIndexV3(coords: List<Float>, coord: Float,
                     radius: Float): Int {
    var firstCord = coords.first()

    for(element in coords){
        val coordX = element
        val startX = coordX -radius
        val endX = coordX + radius

        if((coord < endX).and(coord > startX)){
            return ((coordX / firstCord)-1).toInt()
        }
    }
    return -1
}


@Composable
@Preview()
fun PageStepIndicatorPreview(){
  //  PageStepIndicator()
}