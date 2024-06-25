package com.adayup.amaps.apiCalls

import com.adayup.amaps.responses.*
import DirectionsInterface
import android.content.Context
import com.adayup.amaps.DirectionRequest

import com.adayup.amaps.responses.DirectionsResponse
import com.adayup.amaps.responses.Engine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


fun createEmptyDirectionsResponse(): DirectionsResponse {
    return DirectionsResponse(
        bbox = listOf(),
        routes = listOf(),
        metadata = Metadata(
            attribution = "",
            service = "",
            timestamp = 0L,
            query = Query(
                coordinates = listOf(),
                profile = "",
                format = ""
            ),
            engine = Engine(
                version = "",
                build_date = "",
                graph_date = ""
            )
        )
    )
}

suspend fun getDirections(): DirectionsResponse {
    val service = RetrofitClient.retrofitInstance.create(DirectionsInterface::class.java)
    val apiKey = ""
    val response: Response<DirectionsResponse> = withContext(Dispatchers.IO) {

        var listMain = listOf<List<Double>>()
        listMain = listMain + listOf(listOf(17.61556,50.75425),listOf(17.61365,50.75420))
        val directionRequest = DirectionRequest(listMain)

        service.getDirections(directionRequest)
    }
    if (response.isSuccessful) {
        val res = response.body()
        res?.let {
            return it
        }
    }
    return createEmptyDirectionsResponse()
}