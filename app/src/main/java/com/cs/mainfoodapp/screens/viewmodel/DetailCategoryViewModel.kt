package com.cs.mainfoodapp.screens.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs.mainfoodapp.model.MealsByCategory
import com.cs.mainfoodapp.model.MealsByCategoryList
import com.cs.mainfoodapp.networking.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCategoryViewModel: ViewModel() {

    private var _separateItemsCategoryLiveData = MutableLiveData<List<MealsByCategory>>()
    val separateItemsCategoryLiveData : LiveData<List<MealsByCategory>> = _separateItemsCategoryLiveData


    fun getSeparateMealsByCategory(categoryName: String){
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitInstance.api.getSeparateMealsByCategory(categoryName).enqueue(object :
                Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if(response.isSuccessful){
                        _separateItemsCategoryLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}