package com.albaha.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.albaha.easyfood.R
import com.albaha.easyfood.activites.CategoryMealsActivity
import com.albaha.easyfood.activites.MainActivity
import com.albaha.easyfood.activites.MealActivity
import com.albaha.easyfood.activites.adaptrs.CategoriesAdapter
import com.albaha.easyfood.activites.adaptrs.MostPopularAdapter
import com.albaha.easyfood.databinding.FragmentHomeBinding
import com.albaha.easyfood.db.MealDatabase
import com.albaha.easyfood.pojo.Category
import com.albaha.easyfood.pojo.MealsByCategory
import com.albaha.easyfood.pojo.Meal
import com.albaha.easyfood.viewModel.HomeViewModel
import com.bumptech.glide.Glide


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment: Fragment() {

   private lateinit var binding:FragmentHomeBinding
   private lateinit var viewModel:HomeViewModel
   private lateinit var randomMeal:Meal
   private lateinit var itemPopularAdapter: MostPopularAdapter
   private lateinit var itemCategoryAdapter: CategoriesAdapter

    companion object{
       const val MEAL_ID="com.albaha.easyfood.fragments.idMeal"
       const val MEAL_NANE="com.albaha.easyfood.fragments.nameMeal"
       const val MEAL_THUMB="com.albaha.easyfood.fragments.thumbMeal"
        const val CATEGORY_NAME="com.albaha.easyfood.fragments.categoryName"
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

        itemPopularAdapter= MostPopularAdapter()
        itemCategoryAdapter=CategoriesAdapter()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularItemsRecyclerView()
        categoryItemsRecyclerView()

        viewModel.getRandomMeal()
        viewModel.getPopularItems()
        viewModel.getCategories()
        observeRandomMeal()
        observerPopularItems()
        observerCategoriesLiveData()
        onRandonClickMeal()
        onPopularItemClick()
        onClickCategoryClickItem()
        onPopularItemOnLongClick()
        onClickBtnSearch()

    }

    private fun onClickBtnSearch() {
        binding.imgSearch.setOnClickListener{

            findNavController().navigate(R.id.action_homeFragment_to_searchMealFragment)
        }
    }

    private fun onPopularItemOnLongClick() {
        itemPopularAdapter.onLongItemClick = {meal->
            val intent = Intent(activity,MealActivity::class.java)
            val mealBottomSheetFragment =MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")


        }
    }

    private fun onPopularItemClick() {
        itemPopularAdapter.onItemClick = {meal ->
            val  intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NANE,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun popularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)

            adapter=itemPopularAdapter

        }
    }

    private fun categoryItemsRecyclerView(){
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(context,3,LinearLayoutManager.VERTICAL,false)
            adapter=itemCategoryAdapter

        }
    }

    private fun onClickCategoryClickItem(){
        itemCategoryAdapter.onItemClick={category ->
           var intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)



        }

    }


    fun observeRandomMeal(){
        viewModel.observeRandomMealLivedata().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(value: Meal) {
                randomMeal=value
                Glide.with(this@HomeFragment)
                    .load(value.strMealThumb)
                    .into(binding.imgRandomMeal)
            }

        })
    }

    fun observerPopularItems(){
        viewModel.observerPopularItemsLiveData().observe(viewLifecycleOwner,object  : Observer<List<MealsByCategory>>{
            override fun onChanged(value: List<MealsByCategory>) {
                itemPopularAdapter.setMeals(value as ArrayList<MealsByCategory>)
            }

        })
    }
    fun observerCategoriesLiveData(){
        viewModel.observerCategoriesListLiveData().observe(viewLifecycleOwner,object : Observer<List<Category>>{
            override fun onChanged(value: List<Category>) {
                itemCategoryAdapter.setCategoryList(value as ArrayList<Category>)
            }

        })
    }

    fun observerFavoritesMealsLiveData(){


    }

    fun onRandonClickMeal(){
       binding.randomMeal.setOnClickListener {
           val intent= Intent(activity,MealActivity::class.java)
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_NANE,randomMeal.strMeal)
           intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
           startActivity(intent)
       }

    }

}