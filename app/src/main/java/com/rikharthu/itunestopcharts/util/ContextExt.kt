package com.rikharthu.itunestopcharts.util

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
