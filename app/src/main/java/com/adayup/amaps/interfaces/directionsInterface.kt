package com.adayup.aMaps.interfaces

import com.adayup.aMaps.DirectionRequest
import com.adayup.aMaps.responses.DirectionsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DirectionsInterface {
    @POST("v2/directions/cycling-road")
    @Headers(
        "Content-Type: application/json; charset=utf-8",
        "Accept: application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8",
        "Authorization: place_api_here" //PLACE API HERE
    )
    suspend fun getDirections(@Body request: DirectionRequest): Response<DirectionsResponse>
}