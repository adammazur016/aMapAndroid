package com.adayup.amaps.responses

data class DirectionsResponse(
    val bbox: List<Double>,
    val routes: List<Route>,
    val metadata: Metadata
)

data class Route(
    val summary: Summary,
    val segments: List<Segment>,
    val bbox: List<Double>,
    val geometry: String,
    val way_points: List<Int>
)

data class Summary(
    val distance: Double,
    val duration: Double
)

data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)

data class Step(
    val distance: Double,
    val duration: Double,
    val type: Int,
    val instruction: String,
    val name: String,
    val way_points: List<Int>
)

data class Metadata(
    val attribution: String,
    val service: String,
    val timestamp: Long,
    val query: Query,
    val engine: Engine
)

data class Query(
    val coordinates: List<List<Double>>,
    val profile: String,
    val format: String
)

data class Engine(
    val version: String,
    val build_date: String,
    val graph_date: String
)
