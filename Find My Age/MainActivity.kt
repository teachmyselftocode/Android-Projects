package com.example.jim.emitless

import android.annotation.TargetApi
import android.icu.util.Calendar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView1.text = "Input your year of birth"
        inputYearOfBirth.hint = "Enter Year"
        buFindAge.text = "FIND MY AGE"

        /*buFindAge.setOnClickListener{
            //fire when button is clicked
        }*/
    }

    fun buFindAgeEvent(view: View) {
        //fire when button is clicked
        val yearOfBirth: Int = inputYearOfBirth.text.toString().toInt()

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val myAge = currentYear - yearOfBirth
        textView1.text = "Your age is $myAge years old"


    }
}


