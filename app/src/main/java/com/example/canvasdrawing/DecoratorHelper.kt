package com.example.canvasdrawing

import android.content.Context
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat

object DecoratorHelper {

    var paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 50.0f
    }
}