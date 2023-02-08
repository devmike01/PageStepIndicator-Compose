package gbenga.devmike01.pagestepindicator

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import gbenga.devmike01.compose_pagestepindicator.stepper.*
import gbenga.devmike01.compose_pagestepindicator.stepper.properties.IndicatorLabel
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
                    val indicatorState = rememberIndicatorPropertyState(
                        IndicatorProperty(labels = listOf(IndicatorLabel("Rice"),
                            IndicatorLabel("Beans"), IndicatorLabel("Soda and Wine"),
                            IndicatorLabel("Soda"), IndicatorLabel("Beans and Rice"),
                            IndicatorLabel("Last Step")))
                    )

                    val pageState = rememberPagerState()

                    PageStepIndicator(
                        stepColor= IndicatorColor(
                            strokeActiveColor = android.graphics.Color.YELLOW,
                            activeColor = android.graphics.Color.GREEN,
                            inActiveColor = android.graphics.Color.YELLOW
                        ),
                        propertyState =indicatorState,
                        indicatorState = rememberIndicatorState(),
                        pagerState = pageState){ indicatorState, pagerState ->
                        HorizontalPager(count = 5, state = pagerState) {
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.fillMaxWidth(fraction = 0.9f)
                                    .fillMaxHeight()
                                    .background(Color.Blue.copy(red = (pagerState.currentPage*0.2).toFloat())))
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