package com.reza.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.feature.home.di.HomeModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(HomeModule::class)
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    // Rule for Hilt to manage dependency injection in tests.
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    // Rule for Compose UI testing. Use createAndroidComposeRule for Activity-based tests.
    // This rule allows you to control the activity and compose content.
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
}