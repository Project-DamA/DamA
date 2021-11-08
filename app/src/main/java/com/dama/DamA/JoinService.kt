package com.dama.DamA

import retrofit2.Call
import retrofit2.http.*

interface JoinService{

    @FormUrlEncoded
    @POST("/auth/signup/user/")
    fun requestLogin(
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("username") username:String,
        @Field("age") age:String,
        ) : Call<Join>

}