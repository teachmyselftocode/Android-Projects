package com.companyname.pokemonandroid

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
//import android.support.v4.app.FragmentActivity
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback { //AppCompatActivity()

    private lateinit var mMap: GoogleMap
    //private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
        loadPokemon()
    }

    
    /**
     ADO: The concept of this program is that the GPS will always have my location data. This program
     sets the character Mario as a marker, to always have my location so that it will look like I am moving.
     */

    //method for checking permission. for API 23 and after, need to write code for permission
    //var ACCESSLOCATIONCODE = 123 ---> this was the code I specified to pass to the onRequestPermissionResult
    fun checkPermission() {
        if(Build.VERSION.SDK_INT>=23){

            //First check if user have permission or not
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.
                            permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        123)
                return
            }
        }
        getUserLocation()
    }


    //This method will fire up immediately when request permissions is called
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            123->{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){ // Self Note: only 1 permission from array, find the case where there is 2?
                    getUserLocation()
                }
                else{
                    Toast.makeText(this,"User access location not granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    fun getUserLocation(){
        Toast.makeText(this,"User access location granted", Toast.LENGTH_SHORT).show()


        val locationListener = MyLocationListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        //So this code access specific service in the system (locationManager), and we ask it to get user location every
        // 3 milliseconds , 3 meters, and will call myLocationListener
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,locationListener) // not an error, just requires permission checking
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3,3f,locationListener) // requestLocationUpdates can be used twice
        val myThread = myThread()
        myThread.start()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }


    var location:Location? =null // this variable saves the updates for the location. now use it

    inner class MyLocationListener: LocationListener {

        init {
            location= Location("Start")
            location!!.longitude=0.0
            location!!.latitude=0.0
            /*  Notes for using !!
                var hello: String? = "Test"
                hello?.length() //returns the length of the string since it's not null
                hello!!.length() // also returns the length since hello is not null
                hello = null // valid since it's of type String? versus just a normal String
                hello?.length() //this simply returns a null
                hello!!.length() // this throws an exception
             */
        }
        override fun onLocationChanged(p0: Location?) { //this method fires during requestLocationUpdates which sets location variable to update.
            location = p0
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


    inner class myThread : Thread{        //Thread is working overtime. It takes the location variable and passes it to UI.

        var oldlocation: Location? = null

        constructor():super(){
            oldlocation= Location("Start")
            oldlocation!!.longitude=0.0
            oldlocation!!.latitude=0.0
        }

        override fun run() {
            while (true) {              // I still do not understand why while(true) is used. And why try and carch is used for this Thread
                try {

                    // If the mario location steady, keep go back
                    if(oldlocation!!.distanceTo(location) == 0f)
                    {
                        continue
                    }

                    //If mario location move, update
                    oldlocation=location



                    runOnUiThread{

                        mMap.clear()

                        //Show me
                        val hongkong = LatLng(location!!.latitude, location!!.longitude)
                        mMap.addMarker(MarkerOptions()
                                .position(hongkong)
                                .title("Me")
                                .snippet("I am in Tung Chung Library")
                                .icon(BitmapDescriptorFactory
                                        .fromResource(R.drawable.mario)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hongkong,14f))



                        //Show other pokemon
                        for (i in 0 .. (listPokemons.size-1)){

                            val newPokemon = listPokemons[i]

                            if (!newPokemon.isCatch){

                            val pokemonLocation = LatLng(newPokemon.lat, newPokemon.lon)
                            mMap.addMarker(MarkerOptions()
                                    .position(pokemonLocation)
                                    .title(newPokemon.name!!)
                                    .snippet(newPokemon.des!! + " power:" + newPokemon.power!!)
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(newPokemon.image!!)))

                                if (location!!.distanceTo(newPokemon.location) <2f) {
                                    newPokemon.isCatch = true
                                    listPokemons[i] = newPokemon
                                    playerpower +=newPokemon.power!!
                                    Toast.makeText(applicationContext, "You catched ${newPokemon.name}. " +
                                            " Your new power is $playerpower", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                    Thread.sleep(1000) //to delay because too busy? I don't even understand lol

                } catch (e: Exception) {}
            }
        }
    }
    var playerpower = 0.0

    //Adds the details of the Pokemons
    var listPokemons = ArrayList<Pokemon>()

    fun loadPokemon(){
        listPokemons.add(Pokemon(R.drawable.bulbasaur,
                "Bulbasaur","Bulbasaur living in USA",90.5,22.290849,113.947245))
        listPokemons.add(Pokemon(R.drawable.charmander,
                "Charmander", "Charmander living in japan", 55.0, 22.290834, 113.945850))
        listPokemons.add(Pokemon(R.drawable.squirtle,
                "Squirtle", "Squirtle living in Iraq", 33.5, 22.291295, 113.949038))
    }

}

/**Summary of the whole program
 1. We defined the OnRequestAccess Permission
 2. We defined the class the we will use to access with the permission
 3. We then accessed system service to run that class
 4. Then we get the location updated
 5. Then we used a thread to read that location that is showed in the map
 */

/**
 * TODO
 * Current fucking problem:
 * Mario is appearing at the start
 * Mario changes position when coordinates are changed by its decimals
 * However, when changed coordinates to Hong Kong, Mario is gone.
 *
 * Solved by interchanig lat and lng in LatLng. LOL!
 *
 * When this is solved, rubber duck debug everything else tomorrow
 * 15 Jul Friday

 */