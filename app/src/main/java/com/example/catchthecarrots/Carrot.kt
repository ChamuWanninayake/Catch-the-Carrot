package com.example.catchthecarrots;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.catchthecarrots.R
import java.util.Random


class Carrot(context: Context, val dWidth: Int) {


    val carrot: Array<Bitmap?> = arrayOf(null, null, null, null)


    var carrotFrame = 0
    var carrotX = 0
    var carrotY = 0
    var carrotVelocity = 0

    private val random = Random()


    init {
        carrot[0] = BitmapFactory.decodeResource(context.resources, R.drawable.carrot0)
        carrot[1] = BitmapFactory.decodeResource(context.resources, R.drawable.carrot1)
        carrot[2] = BitmapFactory.decodeResource(context.resources, R.drawable.carrot2)
        carrot[3] = BitmapFactory.decodeResource(context.resources, R.drawable.carrot3)

        resetPosition()
    }



    fun getCarrot(carrotFrame: Int): Bitmap? {
        return carrot[carrotFrame]
    }

    fun getCarrotWidth(): Int {
        return carrot[0]?.width ?: 0
    }

    fun getCarrotHeight(): Int {
        return carrot[0]?.height ?: 0
    }


    fun resetPosition() {
        val carrotWidth = getCarrotWidth()
        val random = Random()

        carrotX = random.nextInt(dWidth - carrotWidth)
        carrotY = -200 - random.nextInt(600)
        carrotVelocity = 35 + random.nextInt(16)
    }
}
