package com.tuuzed.androidx.recyclerview.adapter.sample

import android.content.Context

import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Assert.assertEquals

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context

        assertEquals("com.tuuzed.androidxx.recyclerviewadapter.test", appContext.packageName)
    }
}
