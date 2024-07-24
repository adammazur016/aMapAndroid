package com.adayup.aMaps

import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.adayup.aMaps.apiCalls.getDirections
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.material.textfield.TextInputEditText
import com.xiaomi.xms.wearable.Status
import kotlinx.coroutines.launch
import test.invoke.sdk.XiaomiWatchHelper

class MainActivity : AppCompatActivity() {
    private lateinit var bannerText: TextView
    private lateinit var textInput: TextInputEditText
    private lateinit var sendButton: Button
    private var instance = XiaomiWatchHelper.getInstance(this)

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 3000 // Request location update every 10 seconds
            fastestInterval = 1500 // The fastest interval for location updates, 5 seconds
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currentLocation = location
                    val locationText = currentLocation.latitude.toString() + " " + currentLocation.longitude.toString()
                    Log.d("LOCATION", locationText)
                    val textToSend = "left;$locationText"
                    instance.sendMessageToWear(
                        textToSend
                    ) { obj: Status ->
                        if (obj.isSuccess) {
                            Log.e("WATCH", "Message send")
                        }
                    }
                }
            }
        }

        bannerText = findViewById(R.id.bannerText)
        textInput = findViewById(R.id.textInput)
        sendButton = findViewById(R.id.sendButton)

        instance.setInitMessageListener {
            instance.sendMessageToWear(
                "connected"
            ) { obj: Status ->
                if (obj.isSuccess) {
                    Log.e("WATCH", "Init message send")
                }
            }
        }

        instance.registerMessageReceiver()
        instance.sendUpdateMessageToWear()
        instance.sendNotify(
            "aMaps", "Connected to aMaps :)"
        ) { obj: Status ->
            Log.e(
                "WATCH",
                "send notify ->" + obj.isSuccess
            )
        }

        sendButton.setOnClickListener {
            lifecycleScope.launch {
                val directions = getDirections(50.75323369515438, 17.615213263952203, 50.752655334389665, 17.61660916747918)
                val testMsg = directions.routes[0].segments[0].steps[1].instruction + ";" + directions.routes[0].segments[0].steps[1].distance
                Log.d("WATCH", testMsg)
            }
            instance.sendMessageToWear(
                textInput.text.toString()
            ) { obj: Status ->
                if (obj.isSuccess) {
                    Log.e("WATCH", "Message send")
                }
            }
        }
    }
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}