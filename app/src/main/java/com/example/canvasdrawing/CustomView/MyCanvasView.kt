package com.example.canvasdrawing.CustomView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import com.example.canvasdrawing.DecoratorHelper
import com.example.canvasdrawing.R

private const val STROKE_WIDTH = 12f


class MyCanvasView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap


    private var path = Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    private lateinit var frame: Rect



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColorPurple)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap,0f,0f,null)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }

    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {
            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path, DecoratorHelper.paint)
        }
        invalidate()
    }

    private fun touchUp() {
        path.reset()
    }

    private val backgroundColorPurple = ResourcesCompat.getColor(resources, android.R.color.holo_purple, null)
    private val backgroundColorGrey = ResourcesCompat.getColor(resources, R.color.MyGrey, null)
    private val backgroundColorOrange = ResourcesCompat.getColor(resources, R.color.MyOrange, null)
    private val backgroundColorBlue = ResourcesCompat.getColor(resources, R.color.MyBlue, null)
    private val backgroundColorRed = ResourcesCompat.getColor(resources, R.color.MyRed, null)

    private var resetCounter = 0
    val backgroundColorList = mutableListOf<Int>(backgroundColorPurple,backgroundColorOrange,backgroundColorBlue, backgroundColorRed,backgroundColorGrey)
    fun resetCanvas(){
        resetCounter+=1
        extraCanvas.drawColor(backgroundColorList.get(resetCounter % backgroundColorList.size))
        invalidate()
    }



}