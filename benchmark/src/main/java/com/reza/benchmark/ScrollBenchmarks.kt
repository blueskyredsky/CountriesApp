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
                startActivityAndWait() // Starts the app and waits for the first frame

                val continentList = device.findObject(By.desc("continent_list"))
                // Wait until the initial list is rendered
                continentList.wait(Until.hasObject(By.desc("continent_item")), 2_000)

                // Select the first continent item
                val item = device.findObjects(By.desc("continent_item"))[0]
                item.click()

                // Wait for the OLD screen to be GONE (Increased timeout for stability)
                device.wait(Until.gone(By.desc("continent_list")), 5_000)

                // Wait for the NEW screen's main element to EXIST
                device.wait(Until.hasObject(By.desc("country_list")), 5_000)
            }
        ) {
            // MEASUREMENT BLOCK

            // 1. Reliably find the list UiObject2 before measurement starts
            val countryList = device.wait(
                Until.findObject(By.desc("country_list")),
                5_000
            ) ?: throw IllegalStateException("Country list not found for measurement.")

            // 2. Wait for the list items to be visible
            countryList.wait(Until.hasObject(By.desc("country_item")), 2_000)

            // Set gesture margin and scroll
            countryList.setGestureMargin(device.displayWidth / 5)
            countryList.fling(Direction.DOWN)
            device.waitForIdle()
        }
    }
}