package gbenga.devmike01.compose_pagestepindicator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PageStepIndicator(labels: List<String>, inActiveColor: Int, activeColor: Int,
                      borderColor: Int, onChanged: (position: Int) -> Unit) {

    PageStepIndicator()

}

@Composable
fun PageStepIndicator(circleRadius: Float = 50f,
             stepColor: Color = Color.Red,
             borderStepColor: Color= Color.Red,
                      labels: List<String> = listOf("Rice", "Agbado", "Biscuit", "Water"),
                      strokeWidth: Float =10F,
                      pathColor: Color = Color.Red,
                      pathHeight : Float = 10F,
             canvasModifier: Modifier =Modifier){
    
    Canvas(modifier = canvasModifier){
        val canvasWidth = size.width
        val stepDistance = (canvasWidth / labels.size)
        val canvasHeight = size.height
        val stepCount = labels.size-1;

        val lineWidth = stepDistance -  circleRadius

        labels.forEachIndexed{ i, label ->

            val circleOffset =  Offset(x = stepDistance +(lineWidth* i), y = (canvasHeight /2))

            drawCircle(
                //style = Fill(),
                color = stepColor,
                center = circleOffset,
                radius = circleRadius
            )

            drawCircle(
                brush =  Brush.linearGradient(
                    0.0f to Color.Black,
                    0.3f to Color.Green,
                    1.0f to Color.Blue,
                    start = Offset(0.0f, 50.0f),
                    end = Offset(0.0f, 100.0f)
                ),
                style = Stroke(),
                //color = stepColor,
                center = circleOffset,
                radius = circleRadius
            )

            if(i < stepCount){
                drawLine(
                    strokeWidth = pathHeight,
                    color = pathColor, //pathWidth
                    start = Offset(x =  circleOffset.x +circleRadius, y = circleOffset.y),
                    end = Offset(x = (circleOffset.x +circleRadius) +lineWidth// +lineWidth
                        , y = circleOffset.y)
                )
            }
        }
    }
}

@Composable
@Preview()
fun PageStepIndicatorPreview(){
    PageStepIndicator()
}