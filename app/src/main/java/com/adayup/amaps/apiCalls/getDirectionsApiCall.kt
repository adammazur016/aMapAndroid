package com.adayup.aMaps.apiCalls

import com.adayup.aMaps.responses.Metadata

import com.adayup.aMaps.DirectionRequest
import com.adayup.aMaps.interfaces.DirectionsInterface
import com.adayup.aMaps.responses.DirectionsResponse
import com.adayup.aMaps.responses.Engine
import com.adayup.aMaps.responses.Query
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