package com.cs.mainfoodapp.networking

import com.cs.mainfoodapp.model.CategoryList
import com.cs.mainfoodapp.model.MealResponse
import com.cs.mainfoodapp.model.MealsByCategoryList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal():Response<MealResponse>


    @GET("api/json/v1/1/filter.php?")
    fun getPopularItems(
        @Query("c")
        categoryName: String
    ): Call<MealsByCategoryList>

    @GET("api/json/v1/1/categories.php?")
    fun getCategory(
    ): Call<CategoryList>

    @GET("api/json/v1/1/filter.php")
    fun getSeparateMealsByCategory(
        @Query("c")
        categoryName:String
    ) : Call<MealsByCategoryList>

    @GET("api/json/v1/1/lookup.php?")
    fun getMealDetails(
        @Query("i")
        id:String
    ) :Call<MealResponse>

}