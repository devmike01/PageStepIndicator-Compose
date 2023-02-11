package gbenga.devmike01.compose_pagestepindicator

import android.graphics.Color
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import gbenga.devmike01.compose_pagestepindicator.stepper.*
import gbenga.devmike01.compose_pagestepindicator.stepper.properties.IndicatorLabel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class PageStepIndicatorTest {

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalPagerApi::class)
    @Before
    fun init(){
        composeRule.setContent {

            val pageState = rememberPagerState()


            val indicatorState = rememberIndicatorPropertyState(
                IndicatorProperty(labels = listOf(
                    IndicatorLabel("Title1"),
                    IndicatorLabel("Title2"),
                    IndicatorLabel("Title3"),
                ))
            )

            MaterialTheme() {
                PageStepIndicator(
                    stepColor= IndicatorColor(
                        strokeActiveColor = Color.YELLOW,
                        activeColor = Color.GREEN,
                        inActiveColor = Color.YELLOW
                    ),
                    propertyState =indicatorState,
                    indicatorState = rememberIndicatorState(),
                    pagerState = pageState){ indicatorState, pagerState ->
                }
            }
        }
    }

    @Test
    fun testPageIndicator(){
        composeRule.onNodeWithContentDescription("PageStepIndicator").assertIsDisplayed()
    }


}