package com.albaha.easyfood.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.albaha.easyfood.pojo.MealsByCategory
import com.albaha.easyfood.pojo.MealsByCategoryList
import com.albaha.easyfood.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {

     var mealsLiveData=MutableLiveData<List<MealsByCategory>>()

            fun getMealsByCategory(categoryName:String){

                RetrofitInstance.api.getMealsByCategory(categoryName)
                    .enqueue(object : Callback<MealsByCategoryList>{
                        override fun onResponse(
                            call: Call<MealsByCategoryList>,
                            response: Response<MealsByCategoryList>
                        ) {
                            response.body()?.let {mealsList->
                                mealsLiveData.postValue(mealsList.meals)

                            }


                        }

                        override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                            TODO("Not yet implemented")
                        }

                    })



            }

    fun observeMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}


