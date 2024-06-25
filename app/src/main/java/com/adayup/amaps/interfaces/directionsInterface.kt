import com.adayup.amaps.DirectionRequest
import com.adayup.amaps.responses.DirectionsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DirectionsInterface {
    @POST("v2/directions/driving-car")
    @Headers(
        "Content-Type: application/json; charset=utf-8",
        "Accept: application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8",
        "Authorization: "
    )
    suspend fun getDirections(@Body request: DirectionRequest): Response<DirectionsResponse>
}