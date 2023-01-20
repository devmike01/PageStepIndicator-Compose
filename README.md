# PageStepIndicator-Compose
A page step indicator for Android jetpack compose. If your project doesn't yet support Compose,
checkout the [other page stepper](https://github.com/devmike01/PageStepIndicator) for `XML` layouts.

[![](https://jitpack.io/v/devmike01/PageStepIndicator-Compose.svg)](https://jitpack.io/#devmike01/PageStepIndicator-Compose)

### Preview
!<img src="https://github.com/devmike01/PageStepIndicator-Compose/blob/choir/media/pageindicator_anim.gif" width="240" height="400">

### How To Use
- Add it to your root build.gradle at the end of repositories:

```groovy
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
- Add the dependency

```groovy
dependencies {
    implementation 'com.github.devmike01:PageStepIndicator-Compose:alpha01'
}
```

```Kotlin DSL(Android Only)
dependencies {
    implementation("com.github.devmike01:PageStepIndicator-Compose:alpha01")
}
```

That's all you need to use the library.

### Available APIs
I'm sleepy already. To be continued...

### Sample

```java
val indicatorState = rememberIndicatorPropertyState(
    IndicatorProperty(labels = listOf(IndicatorLabel("Rice"),
        IndicatorLabel("Beans"), IndicatorLabel("Soda and Wine"),
        IndicatorLabel("Soda"), IndicatorLabel("Beans and Rice")))
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
```

License
-------

    Copyright 2023 Oladipupo Gbenga

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


