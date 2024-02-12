package com.albaha.easyfood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.albaha.easyfood.R
import com.albaha.easyfood.activites.MainActivity
import com.albaha.easyfood.activites.adaptrs.CategoriesAdapter
import com.albaha.easyfood.activites.adaptrs.CategoryMealsAdapter
import com.albaha.easyfood.databinding.FragmentCategoriesBinding
import com.albaha.easyfood.pojo.Category
import com.albaha.easyfood.viewModel.CategoryMealsViewModel
import com.albaha.easyfood.viewModel.HomeViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment() {

    private lateinit var categoryMealsAdapter: CategoriesAdapter
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel:HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observerCategory()
        prepareRecyclerView()
    }

    private fun observerCategory() {
        viewModel.observerCategoriesListLiveData().observe(viewLifecycleOwner,Observer{categores->
            categoryMealsAdapter.setCategoryList(categores as ArrayList<Category>)

        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoriesAdapter()
        binding.rvCategory.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }
    }
}