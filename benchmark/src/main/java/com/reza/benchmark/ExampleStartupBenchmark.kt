package com.reza.benchmark

import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.reza.systemdesign.ui.util.UiTags
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */

private const val LIST_ITEMS = 5

@LargeTest
@RunWith(Parameterized::class)
class ExampleStartupBenchmark(
    private val startupMode: StartupMode
) {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.reza.countriesapp.production",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = startupMode,
        setupBlock = {
            // Press home button before each run to ensure the starting activity isn't visible.
            pressHome()
        }
    ) {
        startActivityAndWait()

        // 1. USE By.desc() to target the Compose testTag
        val listContainerSelector = By.desc(UiTags.ContinentScreen.CONTINENTS_LAZY_COLUMN)
        val itemSelector = By.desc(UiTags.ContinentScreen.CONTINENT_ITEM)

        // 2. Wait for the list container to appear (Max 5 seconds should be plenty if it loads in <2s)
        val containerPresent = device.wait(
            Until.hasObject(listContainerSelector),
            5_000 // 5 seconds
        )

        // Check if the container was found
        if (!containerPresent) {
            throw AssertionError("Timed out waiting for the Continents LazyColumn. Check the selector tag: ${UiTags.ContinentScreen.CONTINENTS_LAZY_COLUMN}")
        }

        // 3. Find the container object
        val continentsList = device.findObject(listContainerSelector)
        checkNotNull(continentsList) // Should now pass

        // 4. Wait for an item inside the list
        val searchCondition = Until.hasObject(itemSelector)
        continentsList.wait(searchCondition, 5_000)
    }

    companion object {
        @Parameterized.Parameters(name = "mode={0}")
        @JvmStatic
        fun parameters(): List<Array<Any>> {
            return listOf(
                StartupMode.COLD,
                StartupMode.WARM
            ).map { arrayOf(it) }
        }
    }
}