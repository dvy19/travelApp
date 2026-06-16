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
    val image: String?
)

/*
is null without handling it, you risk a NullPointerException (NPE).
By marking it as String?, Kotlin forces you to handle the null case safely.
 */