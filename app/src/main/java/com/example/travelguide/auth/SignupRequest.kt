package com.example.travelguide.auth

data class SignupRequest(
    var email:String,
    var password:String,
    var role:String
)

data class SignupResponse(
    var message:String,
    var tokens:Token
)

data class Token(
    var access:String,
    var refresh:String
)

data class LoginRequest(

    var email:String,
    var password:String,
)


data class  LoginResponse(
    var message:String,
    var tokens:Token
)


data class DeviceTokenRequest(
    var token:String
)