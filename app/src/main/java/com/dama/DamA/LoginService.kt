package com.dama.DamA

import retrofit2.Call
import retrofit2.http.*

interface LoginService{

    @FormUrlEncoded
    @POST("/auth/login/")
    fun requestLogin(
        @Field("email") email:String,
        @Field("password") password:String
    ) : Call<Login>

}