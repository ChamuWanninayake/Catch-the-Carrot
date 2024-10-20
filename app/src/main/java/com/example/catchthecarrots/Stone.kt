package com.example.catchthecarrots;
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.catchthecarrots.R
import java.util.Random

class Stone(context: Context, val dWidth: Int) {


    private val stone: Array<Bitmap?> = arrayOf(null, null, null, null)

    var stoneFrame = 0
    var stoneX = 0
    var stoneY = 0
    var stoneVelocity = 0

    private val random = Random()



    init {
        stone[0] = BitmapFactory.decodeResource(context.resources, R.drawable.stone0)
        stone[1] = BitmapFactory.decodeResource(context.resources, R.drawable.stone1)
        stone[2] = BitmapFactory.decodeResource(context.resources, R.drawable.stone2)
        stone[3] = BitmapFactory.decodeResource(context.resources, R.drawable.stone3)

        resetPosition()
    }

    fun getStone(stoneFrame: Int): Bitmap? {

        return stone[stoneFrame]
    }

    fun getStoneWidth(): Int {
        return stone[0]?.width ?: 0
    }

    fun getStoneHeight(): Int {
        return stone[1]?.height ?: 0
    }

    fun resetPosition() {
        val stoneWidth = getStoneWidth()
        stoneX = random.nextInt(dWidth - stoneWidth)
        stoneY = -200 - random.nextInt(600)
        stoneVelocity = 35 + random.nextInt(16)
    }
}
