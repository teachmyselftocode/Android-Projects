package com.companyname.zooapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_ticket.*
import kotlinx.android.synthetic.main.animal_ticket.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val listOfAnimals = ArrayList<Animal>()

        listOfAnimals.add(
                Animal("Baboon","Baboon live in  big place with tree",R.drawable.baboon,false))
        listOfAnimals.add(
                Animal("Bulldog","Bulldog live in  big place with tree",R.drawable.bulldog,false))
        listOfAnimals.add(
                Animal("Panda","Panda live in  big place with tree",R.drawable.panda,true))
        listOfAnimals.add(
                Animal("Swallow","Swallow live in  big place with tree",R.drawable.swallow_bird,false))
        listOfAnimals.add(
                Animal("Tiger","Tiger live in  big place with tree",R.drawable.white_tiger,true))
        listOfAnimals.add(
                Animal("Zebra","Zebra live in  big place with tree",R.drawable.zebra,false))


        val adapter = AnimalsAdapter(listOfAnimals, this) // sending it to inner class
        tvListAnimal.adapter = adapter
    }

    inner class AnimalsAdapter: BaseAdapter{

        private var listOfAnimalsLocal = ArrayList<Animal>() // local array. Why set it to another local?
        private var context : Context? = null

        constructor(listOfAnimals : ArrayList<Animal>, mContext : Context):super(){
            this.listOfAnimalsLocal= listOfAnimals //set the data to a local array
            this.context = mContext
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View { //This method fires after getCount

            val animal = listOfAnimalsLocal[p0]
            val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val myView =inflator.inflate(R.layout.animal_ticket, null) //allows access to the layout view to become objects
            myView.tvName.text= animal.name
            myView.tvDescription.text= animal.des
            myView.ivAnimal.setImageResource(animal.image!!)
            return myView
        }

        override fun getItem(p0: Int): Any {
            return listOfAnimalsLocal[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {  //How many times we need to call the getView method above
            return listOfAnimalsLocal.size
        }


    }
}

//TODO 15:40 Loadlist View with Data



