package gbenga.devmike01.pagestepindicator

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import gbenga.devmike01.compose_pagestepindicator.color
import gbenga.devmike01.pagestepindicator.stepper.IndicatorProperty
import gbenga.devmike01.pagestepindicator.stepper.PageStepIndicator
import gbenga.devmike01.pagestepindicator.stepper.properties.IndicatorLabel
import gbenga.devmike01.pagestepindicator.stepper.rememberIndicatorPropertyState
import gbenga.devmike01.pagestepindicator.ui.theme.PageStepIndicatorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PageStepIndicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Greeting("Android")
                        val indicatorState = rememberIndicatorPropertyState(
                            IndicatorProperty(labels = listOf(IndicatorLabel("Rice"),
                                IndicatorLabel("Beans"), IndicatorLabel("Soda"), IndicatorLabel("Soda"),

                                IndicatorLabel("Four")))
                        )
                        PageStepIndicator(indicatorState){ indicatorState ->
                            HorizontalPager(count = 5, state = rememberPagerState()) {

                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(fraction = 0.7f)) {
                                    Text(text = "PAGE $this")
                                }
                                
                            }
                        }
                        
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PageStepIndicatorTheme {
       // PageStepIndicator()
    }
}