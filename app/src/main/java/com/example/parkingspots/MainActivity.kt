package com.example.parkingspots

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
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




    }



    override fun onResume() {
        super.onResume()
        changeTheme()
    }

    fun goToOptions(){
        var newIntent : Intent = Intent(this, OptionActivity::class.java)
        startActivity(newIntent)

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



    companion object {


        var lotList : ArrayList<ParkingSpot> = ArrayList<ParkingSpot>()
    }
}
