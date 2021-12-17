package com.sunnni.auth_prj.data.dto

import com.google.gson.annotations.SerializedName

data class Register (
    @SerializedName("id")
    var id : String,
    @SerializedName("nickname")
    var nickname : String,
    @SerializedName("password")
    var password : String
)