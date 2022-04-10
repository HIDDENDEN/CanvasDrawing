package com.example.canvasdrawing

import android.content.Context
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat

object DecoratorHelper {

    var paint = Paint().apply {
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        style = Paint.Style.STROKE // default: FILL
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = 50.0f // default: Hairline-width (really thin)
    }
}