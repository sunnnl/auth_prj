package com.sunnni.auth_prj.data.dto

data class ResRefreshToken(
    val success: Boolean?,
    val message: String?,
    val code: Int?,
    var token: String?
)