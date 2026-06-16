package com.example.travelguide.city

/*
List<T> is a generic ordered collection of elements of type T
of any type T
ordered
index access
read only
immutable

 */

data class CityRequest(
    var name:String,
    var state:String,
    var latitude:Float,
    var longitude:Float,
    var description:String,
    var imageUrl:String?
)


data class CityResponse(
    var message:String,
    var data:List<City>
)
data class City(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val state: String,
    val created_at: String,
    val updated_at: String,
    val image: String?,
    val famous_for:String?
)

data class SingleCityResponse(
    var message:String,
    var data:City
)

/*
is null without handling it, you risk a NullPointerException (NPE).
By marking it as String?, Kotlin forces you to handle the null case safely.
 */


/*
{
    "message": "City retrieved successfully",
    "data": {
        "id": 1,
        "name": "Kanpur",
        "description": "Industrial city of Uttar Pradesh",
        "famous_for": "",
        "latitude": 26.4499,
        "longitude": 80.3319,
        "state": "Uttar Pradesh",
        "created_at": "2026-06-14T15:19:57.999437Z",
        "updated_at": "2026-06-14T15:19:57.999437Z",
        "image": null
    }
}
 */