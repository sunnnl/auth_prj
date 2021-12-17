package com.sunnni.auth_prj.api

import com.sunnni.auth_prj.data.dto.Register
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {
//    @GET("users/userByParam")
//    fun getUserByParam(
//        @Query("user_id") userId: Int
//    ) : Call<User>

    @POST("api/user")
    fun postSignUp(
        @Body body: Register
    ) : Call<String>
}