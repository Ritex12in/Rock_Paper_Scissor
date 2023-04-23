package com.example.stonepaperscissor

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.stonepaperscissor.databinding.ActivityPlayWithOtherBinding

class PlayWithOther : AppCompatActivity() {

    private var binding:ActivityPlayWithOtherBinding? = null

    private var animation1:AnimationDrawable?=null
    private var animation2:AnimationDrawable? = null
    private var setTime:CountDownTimer? = null

    private var playerName1 = "Player 1"
    private var playerName2 = "Player 2"

    private var player1Ready = false
    private var player2Ready = false
    private var allowPlaying = true

    private lateinit var selectionP1: String
    private lateinit var selectionP2: String

    private var scoreP1 = 0
    private var scoreP2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayWithOtherBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getPlayersName()

        binding?.btnP2rock?.setOnClickListener {
            onPlayP2("rock")
        }
        binding?.btnP2paper?.setOnClickListener {
            onPlayP2("paper")
        }
        binding?.btnP2scissor?.setOnClickListener {
            onPlayP2("scissor")
        }
        binding?.btnP1rock?.setOnClickListener {
            onPlayP1("rock")
        }
        binding?.btnP1paper?.setOnClickListener {
            onPlayP1("paper")
        }
        binding?.btnP1scissor?.setOnClickListener {
            onPlayP1("scissor")
        }
    }

    private fun playAnimation()
    {
        binding?.ivIconP1?.setImageResource(0)
        binding?.ivIconP2?.setImageResource(0)
        binding?.tvP1Status?.text= ""
        binding?.tvP2Status?.text= ""
        binding?.ivIconP1?.setBackgroundResource(R.drawable.animation_rpc)
        animation1 = binding?.ivIconP1?.background as AnimationDrawable
        binding?.ivIconP2?.setBackgroundResource(R.drawable.animation_rpc)
        animation2 = binding?.ivIconP2?.background as AnimationDrawable

        setTime = object :CountDownTimer(3000,1000)
        {
            override fun onTick(p0: Long) {
                animation1?.start()
                animation2?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                allowPlaying = true
                player1Ready = false
                player2Ready = false
                binding?.ivIconP1?.setBackgroundResource(0)
                binding?.ivIconP2?.setBackgroundResource(0)
                setSelectedIcon()
                setScore()
                endGame()
            }

        }.start()
    }

    private fun onPlayP1(selection:String)
    {
        if (allowPlaying)
        {
            binding?.ivIconP1?.setImageResource(R.drawable.check)
            binding?.tvP1Status?.text= "Ready"
            selectionP1 = selection
            player1Ready=true
            if (player2Ready)
            {
                allowPlaying=false
                playAnimation()
            }
        }
    }
    private fun onPlayP2(selection:String)
    {
        if (allowPlaying)
        {
            binding?.ivIconP2?.setImageResource(R.drawable.check)
            binding?.tvP2Status?.text= "Ready"
            selectionP2 = selection
            player2Ready=true
            if (player1Ready)
            {
                allowPlaying=false
                playAnimation()
            }
        }
    }

    private fun setSelectedIcon()
    {
        when(selectionP1)
        {
            "rock" -> binding?.ivIconP1?.setImageResource(R.drawable.rock)
            "paper" -> binding?.ivIconP1?.setImageResource(R.drawable.paper)
            "scissor" -> binding?.ivIconP1?.setImageResource(R.drawable.scissor)
        }

        when(selectionP2)
        {
            "rock" -> binding?.ivIconP2?.setImageResource(R.drawable.rock)
            "paper" -> binding?.ivIconP2?.setImageResource(R.drawable.paper)
            "scissor" -> binding?.ivIconP2?.setImageResource(R.drawable.scissor)
        }
    }

    private fun getResult():String
    {
        return if (selectionP1==selectionP2)
            "tie"
        else if(selectionP1=="rock" && selectionP2=="scissor" ||
                selectionP1=="paper" && selectionP2=="rock" ||
                selectionP1=="scissor" && selectionP2=="paper")
            "P1"
        else
            "P2"
    }

    private fun setScore()
    {
        if (getResult()=="tie")
        {
            binding?.tvP1Status?.text="Tie"
            binding?.tvP2Status?.text="Tie"
        }
        else if(getResult()=="P1")
        {
            binding?.tvP1Status?.text="Win"
            binding?.tvP2Status?.text="Lose"
            scoreP1++
            when(scoreP1)
            {
                1-> binding?.ivP1Star1?.setImageResource(R.drawable.star)
                2-> binding?.ivP1Star2?.setImageResource(R.drawable.star)
                3-> binding?.ivP1Star3?.setImageResource(R.drawable.star)
            }
        }
        else
        {
            binding?.tvP1Status?.text="Lose"
            binding?.tvP2Status?.text="Win"
            scoreP2++
            when(scoreP2)
            {
                1-> binding?.ivP2Star1?.setImageResource(R.drawable.star)
                2-> binding?.ivP2Star2?.setImageResource(R.drawable.star)
                3-> binding?.ivP2Star3?.setImageResource(R.drawable.star)
            }
        }
    }

    private fun getPlayersName()
    {
        val nameDialog = Dialog(this)
        nameDialog.setContentView(R.layout.player_name_dialog)
        nameDialog.findViewById<Button>(R.id.btnOk).setOnClickListener {
            val name1 = nameDialog.findViewById<EditText>(R.id.et_name1).text
            val name2 = nameDialog.findViewById<EditText>(R.id.et_name2).text

            if (name1.isNotEmpty() && name2.isNotEmpty())
            {
                playerName1 = name1.toString()
                playerName2 = name2.toString()
                binding?.playerName1?.text = playerName1
                binding?.playerName2?.text = playerName2
                nameDialog.cancel()
            }
            else
            {
                Toast.makeText(this,"Enter both players name.",Toast.LENGTH_SHORT).show()
            }
        }
        nameDialog.show()
    }

    private fun endGame()
    {
        if(scoreP1==3 || scoreP2==3)
        {
            var winner = if (scoreP1==3)
                playerName1
            else
                playerName2
            val intent = Intent(this,FinishActivity::class.java)
            intent.putExtra("name",winner)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        setTime = null
    }
}