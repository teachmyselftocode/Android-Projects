package com.companyname.calculatorapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buNumberEvent(view: View)
    {
        //A switch for clearing the input after tapping on an operation
        if (isClear)
        {
            displayText.setText("")
        }

        isClear = false
        val buSelect= view as Button
        var clickValue: String =displayText.text.toString()
        when (buSelect.id)
        {
            R.id.bu0-> clickValue +="0"   //can also write in this way bu0.id-> clickValue += "0"
            R.id.bu1-> clickValue +="1"
            R.id.bu2-> clickValue +="2"
            R.id.bu3-> clickValue +="3"
            R.id.bu4-> clickValue +="4"
            R.id.bu5-> clickValue +="5"
            R.id.bu6-> clickValue +="6"
            R.id.bu7-> clickValue +="7"
            R.id.bu8-> clickValue +="8"
            R.id.bu9-> clickValue +="9"
            R.id.buDot-> clickValue +="." //TODO  Prevent adding double dots
            R.id.buSign-> clickValue = "-" + clickValue //TODO Prevent adding double negative sign
        }
        displayText.setText(clickValue)
    }


    var op ="*"
    var oldNumber =""
    var isClear = true

    fun buOpEvent (view: View)
    {
        val buSelect= view as Button
        when (buSelect.id)
        {
            R.id.buPlus-> op = "+"
            R.id.buMinus-> op = "-"
            R.id.buTimes-> op="x"
            R.id.buDivide-> op="/"
        }
        oldNumber = displayText.text.toString()
        isClear = true
    }

    fun buEqualEvent (view:View)
    {
        val newNumber = displayText.text.toString()
        var finalAnswer : Double?= null
        when(op)
        {
            "+" -> finalAnswer= oldNumber.toDouble() + newNumber.toDouble()
            "-" -> finalAnswer= oldNumber.toDouble() - newNumber.toDouble()
            "x" -> finalAnswer= oldNumber.toDouble() * newNumber.toDouble()
            "/" -> finalAnswer= oldNumber.toDouble() / newNumber.toDouble()
        }

        displayText.setText(finalAnswer.toString())
        isClear=true

    }

    fun buPercentageEvent(view: View)
    {
        val convertToDecimal : Double= displayText.text.toString().toDouble()/100
        displayText.setText(convertToDecimal.toString())
        isClear=true
    }

    fun buAllClearEvent (view: View)
    {
        displayText.setText("")
        isClear=true
    }

}


