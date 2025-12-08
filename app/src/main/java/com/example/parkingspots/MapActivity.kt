package com.example.parkingspots

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.VisibleRegion

class MapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var map : GoogleMap


    private lateinit var addButton : Button
    private lateinit var  removeButton : Button

    private lateinit var lotName : TextView

    private lateinit var dispTaken : TextView

    private lateinit var dispMax : TextView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_map)


        var mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync (this)


        addButton = findViewById<Button>(R.id.addCar)
        removeButton = findViewById<Button>(R.id.removeCar)
        lotName = findViewById<TextView>(R.id.lotName)


        dispTaken = findViewById<TextView>(R.id.takenSpots)
        dispMax = findViewById<TextView>(R.id.maxSpots)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)











    }

    override fun onMapReady(p0: GoogleMap) {

        map= p0
        var umd : LatLng =
            LatLng( 38.9889183, -76.9360543 )

        val update : CameraUpdate =
            CameraUpdateFactory.newLatLngZoom( umd, 12.0f )
        map.moveCamera( update )



        addParkingSpots()

        map.setOnMarkerClickListener (markerListener())

    }

    fun addParkingSpots() {




        for (spot in MainActivity.lotList){
            var tempMarker = MarkerOptions().position(spot.getPos()).title(spot.getName())

            map.addMarker(tempMarker)

        }





    }

    inner class markerListener : GoogleMap.OnMarkerClickListener {
        override fun onMarkerClick(p0: Marker): Boolean {


            var tempSpot : ParkingSpot? = null
            var foundMarker = false
            for (spot in MainActivity.lotList) {

                if (p0.title == spot.getName()) {
                    tempSpot = spot

                    foundMarker = true
                }

            }

            if(foundMarker){

                var useSpot = tempSpot!!

                addButton.visibility = VISIBLE
                removeButton.visibility = VISIBLE

                lotName.text = useSpot.getName()

                dispTaken.text = useSpot.getTaken().toString()

                dispMax.text = useSpot.getMax().toString()

                progressBar.max = useSpot.getMax()

                progressBar.setProgress(useSpot.getTaken(),true)



















            }

            return true
        }

    }
}