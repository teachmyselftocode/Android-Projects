package com.example.jim.tictactoy

//import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun buClick(view: View)
    {
        val buSelected = view as Button //Converts view to button. WTF?
        var cellID :Int = 0
        when (buSelected.id){
            R.id.bu1->cellID=1
            R.id.bu2->cellID=2
            R.id.bu3->cellID=3
            R.id.bu4->cellID=4
            R.id.bu5->cellID=5
            R.id.bu6->cellID=6
            R.id.bu7->cellID=7
            R.id.bu8->cellID=8
            R.id.bu9->cellID=9
        }
//        Toast.makeText(this, "This ID is $cellID", Toast.LENGTH_SHORT).show()

        PlayGame(buSelected,cellID)
    }

    val player1 = ArrayList<Int>()
    val player2 = ArrayList<Int>()
    var activePlayer = 1

    fun PlayGame(buSelected : Button, cellID : Int)
    {
        if (activePlayer == 1){
            buSelected.text = "X"
            buSelected.setBackgroundResource(R.color.blue)
            player1.add(cellID)
            activePlayer = 2
            AutoPlay()
        }
        else{
            buSelected.text = "O"
            buSelected.setBackgroundResource(R.color.green)
            player2.add(cellID)
            activePlayer = 1
        }

        buSelected.isEnabled = false // Cannot be Enabled again.
        CheckWinner(player1, player2)
    }

    fun CheckWinner (a: ArrayList<Int>, b: ArrayList<Int>)
    {
        var winner: Int
        if (a.containsAll(listOf(1,2,3)) || a.containsAll(listOf(4,5,6)) ||
                a.containsAll(listOf(7,8,9)) || a.containsAll(listOf(1,4,7)) ||
                a.containsAll(listOf(2,5,8)) || a.containsAll(listOf(3,6,9)) ||
                a.containsAll(listOf(1,5,9)) || a.containsAll(listOf(3,5,7)))
                {
                    winner = 1
                    Toast.makeText(this, "The winner is Player $winner", Toast.LENGTH_SHORT).show()
                }

        if (b.containsAll(listOf(1,2,3)) || b.containsAll(listOf(4,5,6)) ||
                b.containsAll(listOf(7,8,9)) || b.containsAll(listOf(1,4,7)) ||
                b.containsAll(listOf(2,5,8)) || b.containsAll(listOf(3,6,9)) ||
                b.containsAll(listOf(1,5,9)) || b.containsAll(listOf(3,5,7)))
                {
                    winner = 2
                    Toast.makeText(this, "The winner is Player $winner", Toast.LENGTH_SHORT).show()
                }
    }


    fun AutoPlay(){
        val playerComp = ArrayList<Int>()

        for (a in 1..9)
        {
            if (!(player1.contains(a) || player2.contains(a)))
            {
                playerComp.add(a)
            }
        }

        val r = Random()

        //E.g.if the playerComp array has 5 values, it will generate random value from 0 - 4 only
        val randIndex = r.nextInt(playerComp.size-0)+0  // E.g random number between 1 to 10 ==
                                                        // == r.nextInt(10-1) + 1

        val cellID = playerComp[randIndex]

        val buCompSelect : Button?
        when (cellID)
        {
            1 -> buCompSelect = bu1
            2 -> buCompSelect = bu2
            3 -> buCompSelect = bu3
            4 -> buCompSelect = bu4
            5 -> buCompSelect = bu5
            6 -> buCompSelect = bu6
            7 -> buCompSelect = bu7
            8 -> buCompSelect = bu8
            9 -> buCompSelect = bu9
            else -> buCompSelect=bu1
        }
        PlayGame(buCompSelect,cellID)

    }

    //TODO when Player 1 wins and right after that Player 2 also wins, the toast shows only Player 2 wins. Fix it
}


