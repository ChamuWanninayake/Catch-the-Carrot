package com.example.catchthecarrots;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.catchthecarrots.R

class Explotion(context: Context) {


    private val explotion: Array<Bitmap?> = arrayOf(null, null, null, null)


    var explotionFrame = 0
    var explotionX = 0
    var explotionY = 0

    init {
        explotion[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explotion0)
        explotion[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explotion1)
        explotion[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explotion2)
        explotion[3] = BitmapFactory.decodeResource(context.resources, R.drawable.explotion3)
    }

    fun getExplotion(explotionFrame: Int): Bitmap? {
        return explotion[explotionFrame]
    }
}

