package com.reza.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
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
            iterations = 2,
            metrics = listOf(FrameTimingMetric()),
            startupMode = StartupMode.WARM,
            setupBlock = {
                pressHome()
                startActivityAndWait()

                val continentList = device.findObject(By.desc("continent_list"))
                continentList.wait(Until.hasObject(By.desc("continent_item")), 2_000)

                val item = device.findObjects(By.desc("continent_item"))[0]
                item.click()

                device.wait(Until.gone(By.desc("continent_list")), 5_000)
                device.wait(Until.hasObject(By.desc("country_list")), 5_000)
            }
        ) {
            // MEASUREMENT BLOCK
            val countryList = device.wait(
                Until.findObject(By.desc("country_list")),
                5_000
            ) ?: throw IllegalStateException("Country list not found for measurement.")

            countryList.wait(Until.hasObject(By.desc("country_item")), 2_000)

            countryList.setGestureMargin(device.displayWidth / 5)
            countryList.fling(Direction.DOWN)
            device.waitForIdle()
        }
    }
}