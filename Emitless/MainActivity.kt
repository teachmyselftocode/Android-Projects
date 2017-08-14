package com.companyname.emitless2

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.android.volley.RequestQueue
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.BasicNetwork




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Checks permission once for the at the beginning only.
        do {
            checkPermission()
        }
        while (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.
                ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)

    }


    fun buTest2 (view: View){
        val intent = Intent(this, Page2::class.java)
        intent.putExtra("JSONObject", responseText)
        startActivity(intent)
    }


    var responseText : String?= ""
    fun buGo(view:View){

        val locationInput = idInputDestination.text.toString()

        val locationCoordinatesLong = currentLocation!!.longitude
        val locationCoordinatesLat = currentLocation!!.latitude

        //Using Volley to send request
        //TODO Singleton? or leave it like this?
        val cache : Cache= DiskBasedCache(cacheDir, 1024 * 1024)
        val network : Network= BasicNetwork(HurlStack())
        val mRequestQueue : RequestQueue= RequestQueue(cache, network)
        mRequestQueue.start()

        val myAPIKey = "AIzaSyAcGKihEcRIKWOQ-SNEhgAOG6uL5C1bcdQ"
        val requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="+ locationCoordinatesLat +  ","+ locationCoordinatesLong + "&destinations=" + locationInput + "&key=" + myAPIKey
        val jsonObjectRequest= JsonObjectRequest(Request.Method.GET,requestURL,
                null, Response.Listener<JSONObject> { response ->
            responseText  = response.toString()
        },
                Response.ErrorListener { responseText = "That didn't work!" })
        mRequestQueue.add(jsonObjectRequest)

        idTextView2.text = "The longtitude is ${locationCoordinatesLong.toString()} and the latitude is ${locationCoordinatesLat.toString()}"

        //Call the function to go to page 2
//        val intent = Intent(this, Page2::class.java)
//        intent.putExtra("JSONObject", responseText)
//        startActivity(intent)
    }



//    fun writeToFile(jsonText : String?){
//        try {
//            val jsonFile = FileWriter("object.json", true)
//            jsonFile.write(jsonText)
//            jsonFile.close()
//        }catch (ex:Exception){
//            print(ex.message)
//        }
//    }

    val myRequestCode = 123
    fun checkPermission(){

        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.
                    ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                val permissionArray = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissionArray,myRequestCode)
                return
            }
        }
        getUserLocation()
    }

    //Mandatory code
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            myRequestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else {
                    Toast.makeText(this, "User location access not granted", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    fun getUserLocation(){
        Toast.makeText(this,"User location access granted",Toast.LENGTH_LONG).show()

        val locationListener = MyLocationListener()
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3 , 3f , locationListener)
//        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null)     //Does this save battery?
    }


    var currentLocation: Location? = null
    inner class MyLocationListener: LocationListener{

        init {
            currentLocation = Location("Start")
            currentLocation!!.longitude = 0.0
            currentLocation!!.latitude = 0.0
        }
        //Mandatory code
        override fun onLocationChanged(p0: Location?) {
            currentLocation=p0
        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented, Have to know what is happening inside!") //To change body of created functions use File | Settings | File Templates.
        }
        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates. 
        }
        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }


}
