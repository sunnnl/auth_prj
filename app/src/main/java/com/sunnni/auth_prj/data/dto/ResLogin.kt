package com.sunnni.auth_prj.data.dto

data class ResLogin(
    val success: Boolean?,
    val message: String?,
    val code: Int?,
    var user: User?
)