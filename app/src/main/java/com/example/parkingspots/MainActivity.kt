package com.example.parkingspots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var optionsButton: Button
    private var hasShownNotification = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        changeTheme()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        MobileAds.initialize(this) {}
        val adView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        startButton = findViewById<Button>(R.id.startToMap)

        optionsButton = findViewById<Button>(R.id.startToOption)


        optionsButton.setOnClickListener { _ -> goToOptions() }

        startButton.setOnClickListener { _ -> goToMap() }

        var task : ServerTaskSelect = ServerTaskSelect()

        task.start()
        task.join()


    }



    override fun onResume() {
        super.onResume()
        changeTheme()
        if (!hasShownNotification) {
            showNotification()
            hasShownNotification = true
        }
    }

    fun goToMap(){
        var newIntent : Intent = Intent(this, MapActivity::class.java)
        startActivity(newIntent)

        hasShownNotification = false

    }

    fun goToOptions(){
        var newIntent : Intent = Intent(this, OptionActivity::class.java)
        startActivity(newIntent)

        hasShownNotification = false
    }
    fun changeTheme(){
        var pref : SharedPreferences = this.getSharedPreferences(this.packageName + "_preferences",
            Context.MODE_PRIVATE)



        var isDark = pref.getBoolean("isDark",false)


        val newMode = if(isDark){
            AppCompatDelegate.MODE_NIGHT_YES
        }else{
            AppCompatDelegate.MODE_NIGHT_NO
        }


        if(AppCompatDelegate.getDefaultNightMode() != newMode ) {
            AppCompatDelegate.setDefaultNightMode(
                newMode


            )
            recreate()
        }
    }

    fun showNotification() {
        val pref: SharedPreferences = getSharedPreferences(packageName + "_preferences", Context.MODE_PRIVATE)
        val enabled = pref.getBoolean("notificationsEnabled", true)

        if(enabled) {
            var totalAvailable = 0
            for(spot in lotList) {
                totalAvailable += spot.getAvailable()
            }

            Toast.makeText(this, "$totalAvailable parking spots available!", Toast.LENGTH_SHORT).show()
        }
    }



    companion object {


        var lotList : ArrayList<ParkingSpot> = ArrayList<ParkingSpot>()
    }
}
