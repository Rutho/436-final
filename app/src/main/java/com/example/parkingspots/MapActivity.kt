package com.example.parkingspots

import android.os.Bundle
import android.widget.Button
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
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var map : GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_map)


        var mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync (this)








    }

    override fun onMapReady(p0: GoogleMap) {

        map= p0
        var umd : LatLng =
            LatLng( 38.9889183, -76.9360543 )

        val update : CameraUpdate =
            CameraUpdateFactory.newLatLngZoom( umd, 12.0f )
        map.moveCamera( update )



        addParkingSpots()

    }

    fun addParkingSpots() {




        for (spot in MainActivity.lotList){


            map.addMarker(MarkerOptions().position(spot.getPos()).title(spot.getName()))

        }





    }
}