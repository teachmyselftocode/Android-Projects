package com.companyname.pokemonandroid

import android.location.Location

/**
 * Created by jim on 7/7/2017.
 */
class Pokemon(var image: Int ?= null,
              var name: String?= null,
              var des: String?= null,
              var power: Double?= null,
              var lat: Double,
              var lon: Double,
              var isCatch: Boolean = false,
              var location:Location?=null
              ) {

    init {
        this.location =Location(name)
        this.location!!.longitude = lon
        this.location!!.latitude = lat
    }

}