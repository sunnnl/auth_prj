package com.sunnni.auth_prj.api

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
    ) : Call<String>

    @POST("api/user/login")
    fun postSignIn(
        @Body body: User
    ) : Call<Void>

    @GET("api/user/")
    fun getUserInfo(
        @Header("authorization") accessToken: String
    ) : Call<User>

    @GET("admin/users")
    fun getUsers() : Call<ArrayList<User>>
}