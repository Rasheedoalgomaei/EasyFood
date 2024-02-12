package com.albaha.easyfood.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albaha.easyfood.db.MealDatabase
import com.albaha.easyfood.pojo.Category
import com.albaha.easyfood.pojo.CategoryList
import com.albaha.easyfood.pojo.MealsByCategoryList
import com.albaha.easyfood.pojo.MealsByCategory
import com.albaha.easyfood.pojo.Meal
import com.albaha.easyfood.pojo.MealList
import com.albaha.easyfood.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel() {

    private var randomMealLiveData=MutableLiveData<Meal>()
    private var popularItemsLiveData=MutableLiveData<List<MealsByCategory>>()
    private var categoriesListLiveData =MutableLiveData<List<Category>>()
    private var mealDetailsLiveData=MutableLiveData<Meal>()
    private  var favoratesMealsLiveData=mealDatabase.mealDao().getAllMeals()
    private var bottomSheetLiveData=MutableLiveData<Meal>()
    private var mealSearchLiveData= MutableLiveData<List<Meal>>()

    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() !=null){
                    val randomMeal: Meal =response.body()!!.meals[0];
                    randomMealLiveData.value=randomMeal
                    Log.d("TEST", "meal id ${randomMeal.idMeal} name ${randomMeal.strMeal}")
                }else{
                    return
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeFrament",t.message.toString())
            }

        });
    }
    fun observeRandomMealLivedata():LiveData<Meal>{
        return randomMealLiveData
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList>{
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() !=null){
                        popularItemsLiveData.value=response.body()!!.meals
                    }

                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment",t.message.toString())
                }

            })
    }
    fun observerPopularItemsLiveData():LiveData<List<MealsByCategory>>{
        return popularItemsLiveData
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if (response.body() != null){
                    categoriesListLiveData.postValue(response.body()!!.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }


        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().Delete(meal)
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {

            mealDatabase.mealDao().InsertMeal(meal)

        }
    }

    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id)
            .enqueue(object : Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if (response.body() !=null){
                        mealDetailsLiveData.value=response.body()!!.meals[0]

                    }
                    else
                        return
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("MealActivity",t.message.toString())
                }

            })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{

        return mealDetailsLiveData
    }


    fun observerCategoriesListLiveData() : LiveData<List<Category>>{

        return categoriesListLiveData

    }
    fun observerFavoritesMealsLiveData() : LiveData<List<Meal>>{
        return favoratesMealsLiveData

    }
    fun getMealById(id :String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val  meal=response.body()?.meals?.first()
                meal?.let {meal ->
                    bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }

        })

    }
    fun observerBottomSheetMeals():LiveData<Meal>{
        return bottomSheetLiveData
    }

    fun searchMeal(mealName:String){
        RetrofitInstance.api.searchMeal(mealName)
            .enqueue(object :Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {

                   response.body()?.let {
                       mealSearchLiveData.postValue(it.meals)
                   }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.e("HomeViewModel",t.message.toString())
                }

            })
    }
    fun observerSearchList():LiveData<List<Meal>>{

        return  mealSearchLiveData;
    }
}