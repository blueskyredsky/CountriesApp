package com.reza.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
            }
        ) {
            // TODO Add interactions to measure list scrolling.
        }
    }
}