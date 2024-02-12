package com.albaha.easyfood.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albaha.easyfood.pojo.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun InsertMeal(meal: Meal)

     @Delete
     suspend fun Delete(meal: Meal)


     @Query("SELECT * FROM mealInformation")
     fun getAllMeals():LiveData<List<Meal>>
}