package com.sunnni.auth_prj.api

import com.sunnni.auth_prj.data.dto.ResLogin
import com.sunnni.auth_prj.data.dto.ResRefreshToken
import com.sunnni.auth_prj.data.dto.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Service {
    @POST("api/user/")
    fun postSignUp(
        @Body body: User
    ): Call<String>

    @POST("api/user/login")
    fun postSignIn(
        @Body body: User
    ): Call<Void>

    @GET("api/user/")
    fun getUserInfo(
        @Header("authorization") accessToken: String
    ): Call<ResLogin>

    @GET("api/user/admin/users")
    fun getUsers(): Call<List<User>>

    @POST("api/user/admin/switchType")
    fun postChangeUserType(
        @Body body: User
    ): Call<Void>

    @GET("api/user/refresh")
    fun getRefresh(
        @Header("authorization") refreshToken: String
    ): Call<ResRefreshToken>
}