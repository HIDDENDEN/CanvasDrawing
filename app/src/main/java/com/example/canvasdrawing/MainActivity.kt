package com.example.canvasdrawing

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.canvasdrawing.CustomView.MyCanvasView
import java.io.ByteArrayOutputStream


class MainActivity : Activity() {

    lateinit var btnPickColor: ImageView
    lateinit var btnThicknessPicker: ImageView
    lateinit var btnClearScreen: ImageView
    lateinit var btnShareBtn: ImageView
    lateinit var colorPanel: ScrollView
    lateinit var brushThicknessPanel: ScrollView
    lateinit var thicknessLayout: LinearLayout
    lateinit var canvasView: MyCanvasView

    lateinit var blackColor: ImageView
    lateinit var orangeColor: ImageView
    lateinit var blueColor: ImageView
    lateinit var redColor: ImageView

    lateinit var thickness_10: TextView
    lateinit var thickness_30: TextView
    lateinit var thickness_50: TextView
    lateinit var thickness_70: TextView
    lateinit var curThicknessTv: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI(){
        btnPickColor = findViewById(R.id.btn_color_picker)
        btnThicknessPicker = findViewById(R.id.btn_brush_thickness)
        btnClearScreen = findViewById(R.id.btn_clear_screen)
        btnShareBtn = findViewById(R.id.btn_share)
        colorPanel = findViewById(R.id.panel_colors)
        brushThicknessPanel = findViewById(R.id.panel_brush_size)
        thicknessLayout = findViewById(R.id.thickness_layout)

        canvasView = findViewById(R.id.canvasView)

        blackColor = findViewById(R.id.circle_color_black)
        orangeColor = findViewById(R.id.circle_color_orange)
        blueColor = findViewById(R.id.circle_color_blue)
        redColor = findViewById(R.id.circle_color_red)

        thickness_10 = findViewById(R.id.brush_size_10)
        thickness_30 = findViewById(R.id.brush_size_30)
        thickness_50 = findViewById(R.id.brush_size_50)
        thickness_70 = findViewById(R.id.brush_size_70)
        curThicknessTv = findViewById(R.id.tv_cur_thick_size)

        btnPickColor.setOnClickListener {
            if (colorPanel.visibility == View.VISIBLE){
                removePanels()
            } else {
                removePanels()
                colorPanel.visibility = View.VISIBLE
            }

        }
        btnThicknessPicker.setOnClickListener {
            if (thicknessLayout.visibility == View.VISIBLE){
                removePanels()
            } else{
                removePanels()
                thicknessLayout.visibility = View.VISIBLE
            }
        }

        btnClearScreen.setOnClickListener {
            canvasView.resetCanvas()
        }

        btnShareBtn.setOnClickListener {
            val bitmap = loadBitmapFromView(canvasView)
            share_bitMap_to_Apps(bitmap)
        }

        blackColor.setOnClickListener {
            DecoratorHelper.paint.color = ResourcesCompat.getColor(resources, R.color.MyBlack, null)
            btnPickColor.setColorFilter(DecoratorHelper.paint.color, android.graphics.PorterDuff.Mode.SRC_IN);
            removePanels()
        }
        orangeColor.setOnClickListener {
            DecoratorHelper.paint.color = ResourcesCompat.getColor(resources, R.color.MyOrange, null)
            btnPickColor.setColorFilter(DecoratorHelper.paint.color, android.graphics.PorterDuff.Mode.SRC_IN);
            removePanels()
        }
        blueColor.setOnClickListener {
            DecoratorHelper.paint.color = ResourcesCompat.getColor(resources, R.color.MyBlue, null)
            btnPickColor.setColorFilter(DecoratorHelper.paint.color, android.graphics.PorterDuff.Mode.SRC_IN);
            removePanels()
        }
        redColor.setOnClickListener {
            DecoratorHelper.paint.color = ResourcesCompat.getColor(resources, R.color.MyRed, null)
            btnPickColor.setColorFilter(DecoratorHelper.paint.color, android.graphics.PorterDuff.Mode.SRC_IN);
            removePanels()
        }

        thickness_10.setOnClickListener{
            DecoratorHelper.paint.strokeWidth = 10.0f
            curThicknessTv.text = "10"
            removePanels()
        }
        thickness_30.setOnClickListener{
            DecoratorHelper.paint.strokeWidth = 30.0f
            curThicknessTv.text = "30"
            removePanels()
        }
        thickness_50.setOnClickListener{
            DecoratorHelper.paint.strokeWidth = 50.0f
            curThicknessTv.text = "50"
            removePanels()
        }
        thickness_70.setOnClickListener{
            DecoratorHelper.paint.strokeWidth = 70.0f
            curThicknessTv.text = "70"
            removePanels()
        }

        initPaint()
        }

    private fun removePanels(){
        colorPanel.visibility = View.GONE
        thicknessLayout.visibility = View.GONE

    }

    fun loadBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        v.draw(c)
        return b
    }
    fun share_bitMap_to_Apps(bitmap: Bitmap) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        val stream = ByteArrayOutputStream()
        i.putExtra(
            Intent.EXTRA_STREAM,
            getImageUri(this, bitmap)
        )
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."))
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.getContentResolver(),
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    fun initPaint(){
        DecoratorHelper.paint = Paint().apply {
            color = ResourcesCompat.getColor(resources,
                R.color.MyBlack, null)
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
}