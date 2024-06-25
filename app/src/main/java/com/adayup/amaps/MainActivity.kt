package com.adayup.amaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.adayup.amaps.apiCalls.getDirections
import com.adayup.amaps.responses.DirectionsResponse
import com.adayup.amaps.responses.Route
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //potrzebna mi będzie lokalizacja zegarka
        //potem będę musiał pobrać tylko pierwszy step w zależności od lokalizacji
        //generalnie to wyświetlam pierwszy step a reszta na razie mnie nie obchodzi
        // raz na 2-5 sekund myślę że będzie git
        lifecycleScope.launch {
            val response: DirectionsResponse = getDirections()
            val routes = response.routes[0]
            val steps = routes.segments[0].steps
            for(elem in steps){
                Log.d("TEST", elem.instruction)
                Log.d("TEST", elem.distance.toString())
            }
        }
    }


}