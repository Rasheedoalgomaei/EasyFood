package com.albaha.easyfood.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.albaha.easyfood.R
import com.albaha.easyfood.databinding.ActivityMealBinding
import com.albaha.easyfood.db.MealDatabase
import com.albaha.easyfood.fragments.HomeFragment
import com.albaha.easyfood.pojo.Meal
import com.albaha.easyfood.viewModel.HomeViewModel
import com.albaha.easyfood.viewModel.MealViewModel
import com.albaha.easyfood.viewModel.HomeViewModelFactory
import com.albaha.easyfood.viewModel.MealViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var viewModel:HomeViewModel
   private  lateinit var  youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMealInformationFromIntent()
        setInformationInView()
        loadingCase()

        val  mealDatabase=MealDatabase.getInstance(this)
        val  viewModelFactory=HomeViewModelFactory(mealDatabase)
        viewModel= ViewModelProvider( this,viewModelFactory)[HomeViewModel::class.java]

       // mealMvvm=ViewModelProviders.of(this)[MealViewModel::class.java]


        viewModel.getMealDetails(mealId)

        observerMealDetailsLiveData()


        onYoutubeImgClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnAddToFavorite.setOnClickListener {
            mealToSave?.let {
                viewModel.insertMeal(it)
                Toast.makeText(this,"Add to Favirate Successfull!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun onYoutubeImgClick() {

        binding.imgYouTube.setOnClickListener {

            intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }

    }

    private var mealToSave:Meal?=null
    private fun observerMealDetailsLiveData() {
        viewModel.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal) {
                responseCase()
                val meal=t
                mealToSave=t
                binding.tvCategory.text= "Category : ${meal!!.strCategory}"
                binding.tvArea.text="Area : ${meal!!.strArea}"
                binding.tvInstructionsSteps.text=meal.strInstructions
                youtubeLink= meal.strYoutube!!
            }

        })

    }

    private fun setInformationInView() {
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)
        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent =intent
        mealId=intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName=intent.getStringExtra(HomeFragment.MEAL_NANE)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }
    fun loadingCase(){
        binding.btnAddToFavorite.visibility= View.INVISIBLE
        binding.tvCategory.visibility= View.INVISIBLE
        binding.tvArea.visibility= View.INVISIBLE
        binding.tvInstructionsSteps.visibility= View.INVISIBLE
        binding.imgYouTube.visibility= View.INVISIBLE
        binding.progressBar.visibility= View.VISIBLE


    }

    fun responseCase(){

        binding.btnAddToFavorite.visibility= View.VISIBLE
        binding.tvCategory.visibility= View.VISIBLE
        binding.tvArea.visibility= View.VISIBLE
        binding.tvInstructionsSteps.visibility= View.VISIBLE
        binding.imgYouTube.visibility= View.VISIBLE
        binding.progressBar.visibility= View.INVISIBLE
    }
}