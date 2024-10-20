package com.example.catchthecarrots;

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.catchthecarrots.R

class GameOver : AppCompatActivity() {

    private lateinit var tvPoints: TextView
    private lateinit var tvHighest: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ivNewHighest: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over)


        tvPoints = findViewById(R.id.tvPoints)
        tvHighest = findViewById(R.id.tvHighest)
        ivNewHighest = findViewById(R.id.ivNewHeighest)



        val points = intent.extras?.getInt("points") ?: 0
        tvPoints.text = points.toString()


        sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE)
        val highest = sharedPreferences.getInt("highest", 0)


        if (points > highest) {
            ivNewHighest.visibility = View.VISIBLE
            sharedPreferences.edit().putInt("highest", points).apply()
            tvHighest.text = points.toString()

        }else{
            tvHighest.text = highest.toString()
        }

    }

    fun restart(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun exit(view: View) {
        finish()
    }
}
