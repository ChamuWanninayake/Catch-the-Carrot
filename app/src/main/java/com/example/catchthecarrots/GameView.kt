package com.example.catchthecarrots;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import java.util.ArrayList
import java.util.Random
import android.graphics.Rect
import android.util.Log


class GameView(context: Context) : View(context) {

    private val background: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.background1)
    }
    private val ground: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.ground)
    }
    private val rabbit: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.bunny1)
    }

    private val dWidth: Int

    private val dHeight: Int

    private val rectBackground = Rect(0, 0, 0, 0)
    private val rectGround = Rect(0, 0, 0, 0)

    private val handler = Handler()
    private val runnable = Runnable {
        invalidate()
    }

    private val textPaint = Paint()


    private val healthPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
    }


    private val UPDATE_MILLIS = 30
    private val TEXT_SIZE = 100f
    private var points = 0
    private var life = 3
    private var count = 0
    private val random = Random()
    private var rabbitX = 0f
    private var rabbitY = 0f
    private var oldX = 0f
    private var oldRabbitX = 0f
    private val carrots = ArrayList<Carrot>()
    private val explotions = ArrayList<Explotion>()
    private val stones = ArrayList<Stone>()

    init {
        textPaint.color = Color.rgb(255, 165, 0)
        textPaint.textSize = TEXT_SIZE
        textPaint.textAlign = Paint.Align.LEFT

        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        dWidth = size.x
        dHeight = size.y

        rectBackground.right = dWidth
        rectBackground.bottom = dHeight
        rectGround.top = dHeight - ground.height
        rectGround.bottom = dHeight

        rabbitX = dWidth / 2f - rabbit.width / 2f
        rabbitY = (dHeight - ground.height - rabbit.height).toFloat()

        for (i in 0 until 4) {
            val carrot = Carrot(context, dWidth)
            carrots.add(carrot)
        }
        stones.add(Stone(context , dWidth))
    }

    override fun onDraw(canvas: Canvas) {



        canvas.drawBitmap(background, null, rectBackground, null)

        canvas.drawBitmap(ground, null, rectGround, null)

        canvas.drawBitmap(rabbit, rabbitX, rabbitY, null)


        for (carrot in carrots) {
            val carrotBitmap = carrot.getCarrot(carrot.carrotFrame)

            if (carrotBitmap != null) {
                canvas.drawBitmap(carrotBitmap, carrot.carrotX.toFloat(), carrot.carrotY.toFloat(), null)
            }

            carrot.carrotFrame++

            if (carrot.carrotFrame > 3) {
                carrot.carrotFrame = 0
            }

            carrot.carrotY += carrot.carrotVelocity


            if (carrot.carrotY + carrot.getCarrotHeight() >= dHeight - ground.height) {

                val explotion = Explotion(context)
                explotion.explotionX = carrot.carrotX
                explotion.explotionY = carrot.carrotY
                explotions.add(explotion)
                carrot.resetPosition()
            }


            if (carrot.carrotX + carrot.getCarrotWidth() >= rabbitX
                && carrot.carrotX <= rabbitX + rabbit.width
                && carrot.carrotY <= rabbitY + rabbit.height
                && carrot.carrotY + carrot.getCarrotWidth() >= rabbitY) {

                points += 10
                count++


                if (count == 5 && life <= 2) {
                    life++;
                    count = 0;
                }

                carrot.resetPosition()
            }
        }


        for (stone in stones) {
            val stoneBitmap = stone.getStone(stone.stoneFrame)

            if (stoneBitmap != null) {
                canvas.drawBitmap(stoneBitmap, stone.stoneX.toFloat(), stone.stoneY.toFloat(), null)
            }

            stone.stoneFrame++

            if (stone.stoneFrame > 3) {
                stone.stoneFrame = 0
            }

            stone.stoneY += stone.stoneVelocity


            if (stone.stoneY + stone.getStoneHeight() >= height - ground.height) {

                val explotion = Explotion(context)
                explotion.explotionX = stone.stoneX
                explotion.explotionY = stone.stoneY
                explotions.add(explotion)
                stone.resetPosition()
            }


            if (stone.stoneX + stone.getStoneWidth() >= rabbitX
                && stone.stoneX <= rabbitX + rabbit.width
                && stone.stoneY <= rabbitY + rabbit.height
                && stone.stoneY + stone.getStoneWidth() >= rabbitY
            ) {

                life--
                count = 0;

                stone.resetPosition()

                if (life == 0) {
                    val intent = Intent(this.context, GameOver::class.java)
                    intent.putExtra("points", points)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        }


        val iterator = explotions.iterator()

        while (iterator.hasNext()) {
            val explotion = iterator.next()
            val explosionBitmap = explotion.getExplotion(explotion.explotionFrame)

            if (explosionBitmap != null) {
                canvas.drawBitmap(explosionBitmap, explotion.explotionX.toFloat(), explotion.explotionY.toFloat(), null)


            }

            explotion.explotionFrame++

            if (explotion.explotionFrame > 3) {
                iterator.remove()
            }
        }


        if (life == 3) {
            healthPaint.color = Color.GREEN
        }
        else if (life == 2) {
            healthPaint.color = Color.YELLOW

        } else if (life == 1) {
            healthPaint.color = Color.RED
        }


        canvas.drawRect(width - 200f, 30f, width - 200f + 60 * life, 80f, healthPaint)

//        Log.d("GameView", "points: $points")

        canvas.drawText(""+points, 20f, TEXT_SIZE, textPaint)

        handler.postDelayed(runnable, UPDATE_MILLIS.toLong())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val touchX = event.x
        val touchY = event.y

        if (touchY >= rabbitY) {
            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    oldX = event.x
                    oldRabbitX = rabbitX
                }

                MotionEvent.ACTION_MOVE -> {

                    val shift = oldX - touchX
                    val newRabbitX = oldRabbitX - shift


                    rabbitX = when {
                        newRabbitX <= 0 -> 0f
                        newRabbitX >= width - rabbit.width -> (width - rabbit.width).toFloat()

                        else -> newRabbitX
                    }
                }
            }
        }
        return true
    }
}

