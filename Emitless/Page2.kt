package com.companyname.emitless2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.activity_page2.*
import android.view.View
import org.json.JSONArray
import org.json.JSONObject

import com.beust.klaxon.*



class Page2 : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        val message = intent.getStringExtra("JSONObject")
        // Capture the layout's TextView and set the string as its text
        idPrintText.text = message

    }


    fun buReturn (view: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    fun buTest3 (view: View){
        parseJSONObject()
        idPrintText.text = ""
        idPrintText2.text = afterParsingJSON
    }


    var origin: String? =""
    var destination: String? =""
    var distance: String? =""
    var duration: String? =""
    var afterParsingJSON: String? = ""

    fun parseJSONObject(){
        val message =intent.getStringExtra("JSONObject")
        val mainObject : JSONObject = JSONObject(message)
                .getJSONArray("rows")
                .getJSONObject(0)
                .getJSONArray("elements")
                .getJSONObject(0)

        distance = mainObject.getJSONObject("distance").get("text").toString()
        duration = mainObject.getJSONObject("duration").get("text").toString()
        origin = JSONObject(message).get("origin_addresses").toString()
        destination = JSONObject(message).get("destination_addresses").toString()

        afterParsingJSON = "From $origin to $destination, the distance is $distance and the duration is $duration"
    }

//    //this is using klaxon
//    fun parseJSONObject(){
//
//        val message =intent.getStringExtra("JSONObject")
//        val parser : Parser = Parser()
//        val stringBuilder : StringBuilder = StringBuilder(message)
//        val obj : JsonArray<JsonObject> = parser.parse(stringBuilder) as JsonArray<JsonObject>
//        origin = obj.int("distance")
//    }

//    fun parse(name:String) : Any?{
//        val cls = Parser::class.java
//        return cls.getResourceAsStream(name)?.let{inputStream -> return Parser().parse(inputStream) }
//    }
}
