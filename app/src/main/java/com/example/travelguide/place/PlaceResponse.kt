package com.example.travelguide.place

data class PlaceResponse(
    val message: String,
    val data: List<Place>
)

data class Place(
    val id: Int,
    val city: Int,
    val city_name: String,
    val category: Int,
    val category_name: String,
    val name: String,
    val description: String,
    val historical_significance: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val opening_time: String,
    val closing_time: String,
    val entry_fee: String,
    val contact_number: String?,
    val website: String,
    val image_url: String?,
    val created_at: String
)


data class SinglePlaceResponse(
    var message:String,
    var data:SinglePlaceData
)

data class  SinglePlaceData(
    var id:Int,
    var city:Int,
    var city_name:String,
    var category:Int,
    var category_name:String,
    var name:String,
    var description:String,
    var historical_significance:String,
    var address:String,
    var latitude:Double,
    var longitude:Double,
    var opening_time:String,
    var closing_time:String,
    var entry_fee:String,
    var contact_number:String,
    var website:String,
    var image_url:String,
    var created_at:String,
    var reviews:List<ReviewResponse>
)

data class ReviewRequest(
    var user:Int,
    var place:Int,
    var rating:Int,
    var content: String,
)

data class ReviewResponse(
    var message:String,
    var data:ReviewData
)
data class ReviewData(

    var id:Int,
    var place:Int,
    var user:Int,
    var user_mail:String,
    var created_at: String,
    var rating:Int,
    var content:String
)

/*
{
    "message": "Review created successfully",
    "data": {
        "id": 1,
        "place": 4,
        "user": 11,
        "user_email": "user0909@gmail.com",
        "rating": 4,
        "content": "good place",
        "created_at": "2026-06-17T09:44:58.543706Z"
    }
}
 */
/*
{
    "message": "Place retrieved successfully",
    "data": {
        "id": 3,
        "city": 2,
        "city_name": "Bithoor",
        "category": 2,
        "category_name": "Park",
        "name": "Nana Rao Park",
        "description": "Popular park in Bithoor",
        "historical_significance": "Associated with Nana Sahib and the Revolt of 1857",
        "address": "Bithoor, Kanpur Nagar, Uttar Pradesh",
        "latitude": 26.617,
        "longitude": 80.266,
        "opening_time": "05:00:00",
        "closing_time": "20:00:00",
        "entry_fee": "20.00",
        "contact_number": "",
        "website": "https://example.com/nanaraopark.jpg",
        "image_url": "",
        "reviews": []
    }
}
 */