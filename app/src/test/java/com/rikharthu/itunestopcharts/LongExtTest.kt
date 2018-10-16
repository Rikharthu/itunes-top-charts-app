package com.rikharthu.itunestopcharts

import com.rikharthu.itunestopcharts.util.millisToFormattedTime
import com.rikharthu.itunestopcharts.util.toFormattedTime
import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LongExtTest {
    @Test
    fun `toFormattedTime converts correctly`() {
        val time = 340L
        val formattedTime = time.toFormattedTime()
        assertEquals("05:40", formattedTime)
    }

    @Test
    fun `millisToFormattedTime converts correctly`() {
        val time = 340000L
        val formattedTime = time.millisToFormattedTime()
        assertEquals("05:40", formattedTime)
    }
}
