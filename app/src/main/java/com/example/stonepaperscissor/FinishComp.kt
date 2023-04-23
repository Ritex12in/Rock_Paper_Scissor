package com.example.stonepaperscissor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import pl.droidsonroids.gif.GifImageView

class FinishComp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_comp)

        val btnHome = findViewById<Button>(R.id.btnHome)

        val winner = intent.getStringExtra("winner")
        setView(winner.toString())
        btnHome.setOnClickListener {
            finish()
        }
    }

    private fun setView(winner:String)
    {
        val imageView:GifImageView = findViewById(R.id.statusDisplay)
        val tvStatus:TextView = findViewById(R.id.tv_Status)
        if (winner=="comp")
        {
            imageView.setImageResource(R.drawable.lose_gif)
            tvStatus.text = "Better luck next time\nYou lose!"
        }
        else
        {
            imageView.setImageResource(R.drawable.win_gif)
            tvStatus.text = "Congratulation\nYou won!"
        }
    }
}