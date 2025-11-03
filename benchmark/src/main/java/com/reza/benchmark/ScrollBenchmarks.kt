package com.reza.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollBenchmarks {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scroll() {
        benchmarkRule.measureRepeated(
            packageName = "com.reza.countriesapp.production",
            iterations = 5,
            metrics = listOf(FrameTimingMetric()),
            setupBlock = {
                // Start the default activity, but don't measure the frames yet
                pressHome()
                startActivityAndWait()

                val continentList = device.findObject(By.desc("continent_list"))
                val continentItemCondition = Until.hasObject(By.desc("continent_item"))
                // Wait until a continent item within the list is rendered
                continentList.wait(continentItemCondition, 5_000)

                // Select the first continent item
                val item = device.findObjects(By.desc("continent_item"))[0]
                item.click()

                // Wait until the screen is gone
                device.wait(Until.gone(By.desc("continent_list")), 5_000)

                val countryList = device.findObject(By.desc("country_list"))
                val countryItemCondition = Until.hasObject(By.desc("country_item"))
                // Wait until a country item within the list is rendered
                countryList.wait(countryItemCondition, 5_000)
            }
        ) {
            // TODO Scroll the country list
        }
    }
}