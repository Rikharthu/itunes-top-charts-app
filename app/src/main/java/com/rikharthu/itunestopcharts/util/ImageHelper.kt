package com.rikharthu.itunestopcharts.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LightingColorFilter
import android.graphics.Paint
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.FloatRange
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

object ImageHelper {

    private const val BITMAP_SCALE = 0.6f
    private const val BLUR_RADIUS = 15f

    fun blurImage(context: Context, image: Bitmap): Bitmap {
        val width = Math.round(image.width * BITMAP_SCALE)
        val height = Math.round(image.height * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)

        val intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

        with(intrinsicBlur) {
            setRadius(BLUR_RADIUS)
            setInput(tmpIn)
            forEach(tmpOut)
        }
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }

    fun blurImageAsync(context: Context, image: Bitmap): Deferred<Bitmap> {
        return async {
            blurImage(context, image)
        }
    }

    fun dimImageAsync(image: Bitmap, @FloatRange(from = 0.0, to = 1.0) percent: Float): Deferred<Bitmap> = async {
        val paint = Paint().apply {
            colorFilter = LightingColorFilter(0xFF7F7F7F.toInt(), 0x00000000)
        }
        with(Canvas(image)) {
            drawBitmap(image, 0f, 0f, paint)
        }
        return@async image
    }
}