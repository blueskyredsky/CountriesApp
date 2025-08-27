package com.reza.details

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.details.di.DetailsModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(DetailsModule::class)
class DetailsScreenTest {
}