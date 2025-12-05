package com.example.parkingspots

import org.json.JSONArray
import org.json.JSONException
import java.io.InputStream
import java.net.URL
import java.util.Scanner

class ServerTaskUpdate : Thread {


    private lateinit var activity: MainActivity

    private lateinit var result: String

    private lateinit var dataString: String

    private val update: String = "http://jbessanh.cmsc424-2501.cs.umd.edu/436Final/update.php"

    private lateinit var data: Any

    private lateinit var name: String
    private lateinit var field: String


    constructor(activity: MainActivity, name: String, field: String, data: Any) {

        this.activity = activity

        this.name = name
        this.field = field
        this.data = data


    }


    fun setDataString(d: String) {

        dataString = d
    }

    override fun run() {

        super.run()





        try {


            //create url

            var data = "name=$name&field=$field&data=$data"
            var url: URL = URL(update + "?$data")


            var iStream: InputStream = url.openStream()
            var scan: Scanner = Scanner(iStream)

            //read from input stream
            while (scan.hasNext()) {
                result += scan.nextLine()
            }


        } catch (e: Exception) {


            result = e.message.toString()
        }


    }

    fun parseJson(json: String): Array<String>? {

        var result: Array<String>? = null


        try {

            var jsonArray: JSONArray = JSONArray(json)

            result = Array<String>(jsonArray.length()) { i -> jsonArray.getString(i) }


        } catch (e: JSONException) {

            result = arrayOf(e.toString())
        }

        return result
    }
}