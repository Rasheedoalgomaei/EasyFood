package com.albaha.easyfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.albaha.easyfood.R
import com.albaha.easyfood.activites.MainActivity
import com.albaha.easyfood.databinding.FragmentMealBottomSheetBinding
import com.albaha.easyfood.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val MEAL_ID = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [MealBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealBottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var mealId: String? = null

    private lateinit var binding:FragmentMealBottomSheetBinding
    private lateinit var viewModel:HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

        }
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMealBottomSheetBinding.inflate(inflater)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {

            viewModel.getMealById(it)
        }

        observeBottomSheet()

    }

    private fun observeBottomSheet() {
        viewModel.observerBottomSheetMeals().observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.strMealThumb)
                .into(binding.imgBottomSheet)
            binding.tvBottomSheetMealName.text=it.strMeal
            binding.tvBottomSheetArea.text=it.strArea
            binding.tvBottomSheetCategory.text=it.strCategory
        })
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}