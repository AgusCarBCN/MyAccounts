package com.blogspot.agusticar.miscuentasv2.main.model

data class UserProfile (
    val name: String,
    val userName: String,
    val password: String
){
    val profileName:String
        get()=userName
    val profilePass:String
        get()=password
}