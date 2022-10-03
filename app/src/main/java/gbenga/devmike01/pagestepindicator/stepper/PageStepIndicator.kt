package gbenga.devmike01.compose_pagestepindicator

import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import java.lang.reflect.Type
import android.graphics.Color as Color2

@Composable
fun PageStepIndicator(labels: List<String> = emptyList(), inActiveColor: Int = Color2.GRAY,
                      activeColor: Int = Color2.BLUE,
                      borderColor: Int = Color2.RED,
                      onChanged: ((position: Int) -> Unit)? = null) {

    PaintStepIndicators()

}

@Composable
fun PaintStepIndicators(  strokeWidth: Float =10F,){

    val textPaint = Paint().asFrameworkPaint().apply {
        isAntiAlias = true
        textSize = 29F
        color = android.graphics.Color.BLACK
        typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
    }

    val stepPaint = Paint().apply {
        isAntiAlias = true
        this.strokeWidth = strokeWidth
        color = Color.Green
        style = PaintingStyle.Stroke }

    DrawPageStepIndicator(textPaint = textPaint, stepPaint= stepPaint)
}

@Composable
fun DrawPageStepIndicator(
    textPaint: android.graphics.Paint,
    stepPaint: Paint,
    circleRadius: Float = 50f,
             stepColor: Color = Color.Red,
                      labels: List<String> = listOf("Rice", "Agbado", "Biscuit", "Water"),
                      strokeWidth: Float =10F,
                      pathHeight : Float = 10F,
             canvasModifier: Modifier =Modifier){

    
    Canvas(modifier = canvasModifier){
        val canvasWidth = size.width
        val stepDistance = (canvasWidth / labels.size)
        val canvasHeight = size.height
        val stepCount = labels.size-1;

        val lineWidth = stepDistance -  circleRadius

        val circleWidth = circleRadius*2

        val labelTopSpace = 50f


        drawIntoCanvas {
            labels.forEachIndexed{ i, label ->

                val circleOffset =  Offset(x = stepDistance +(lineWidth* i), y = (canvasHeight /2))
                Log.d("PageStepIndicator", "$label")
                val circleBorderX = strokeWidth + circleRadius
                it.drawCircle(
                    //style = Fill(),
                    center = circleOffset,
                    radius = circleRadius,
                    stepPaint
                )

                if(i < stepCount){
                    drawLine(
                        strokeWidth = pathHeight,
                        //strokeWidth = 6f,
                        color = listOf<Color>(Color.Blue, Color.Red, Color.Black, Color.DarkGray,)[i], //pathWidth
                        start = Offset(x =  circleOffset.x +circleBorderX, y = circleOffset.y),
                        end = Offset(x = (circleOffset.x -(circleRadius+strokeWidth)) +lineWidth// +lineWidth
                            , y = circleOffset.y)
                    )
                }


                val bounds = Rect()
                textPaint.getTextBounds(label, 0, label.length, bounds)
                val centerX = bounds.centerX()

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