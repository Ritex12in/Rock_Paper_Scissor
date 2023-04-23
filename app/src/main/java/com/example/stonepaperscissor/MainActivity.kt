package com.example.stonepaperscissor

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPlayWithOther = findViewById<Button>(R.id.btnPlayWithOther)
        btnPlayWithOther.setOnClickListener {
            val intent = Intent(this,PlayWithOther::class.java)
            startActivity(intent)
        }
        val btnPlayWithComp:Button = findViewById(R.id.btnPlayWithComputer)
        btnPlayWithComp.setOnClickListener {
            val intent = Intent(this,PlayWithComputer::class.java)
            startActivity(intent)
        }
        val tvInstruction:TextView = findViewById(R.id.tvInstruction)
        tvInstruction.setOnClickListener {
            showInstructions()
        }

    }

    private fun showInstructions()
    {
        val instDialog = Dialog(this)
        instDialog.setContentView(R.layout.instruction_dialog)
        instDialog.findViewById<Button>(R.id.btnok).setOnClickListener {
            instDialog.cancel()
        }
        instDialog.show()
    }
}