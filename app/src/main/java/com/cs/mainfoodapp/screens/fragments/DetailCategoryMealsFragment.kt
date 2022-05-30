package com.cs.mainfoodapp.screens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cs.mainfoodapp.R
import com.cs.mainfoodapp.databinding.FragmentCategoriesBinding
import com.cs.mainfoodapp.databinding.FragmentDetailCategoryBinding
import com.cs.mainfoodapp.databinding.FragmentHomeBinding
import com.cs.mainfoodapp.screens.adapters.DetailCategoryMealsAdapter
import com.cs.mainfoodapp.screens.viewmodel.DetailCategoryViewModel
import com.cs.mainfoodapp.screens.viewmodel.HomeViewModel


class DetailCategoryMealsFragment : Fragment() {
    private val viewModel: DetailCategoryViewModel by viewModels()
    private val arguments : DetailCategoryMealsFragmentArgs by navArgs()
    lateinit var categoryMealAdapter: DetailCategoryMealsAdapter

    private lateinit var categoryName: String




    lateinit var binding: FragmentDetailCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryName = arguments.categoryName


        prepareRecyclerView()
        viewModel.getSeparateMealsByCategory(categoryName)
        getSeparateMealsByCategory()
        detailCategoryListClick()

    }


    private fun prepareRecyclerView(){
        categoryMealAdapter = DetailCategoryMealsAdapter()
        binding.rvMeal.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealAdapter
        }
    }


    private fun getSeparateMealsByCategory() {
        viewModel.separateItemsCategoryLiveData.observe(viewLifecycleOwner, Observer{ mealsList ->

            binding.tvCategoryMealsAppBar.text = categoryName
            binding.tvCategoryCount.text = "Total Items: ${mealsList.size}"
            categoryMealAdapter.setMealsList(mealsList)

        })
    }

    private fun detailCategoryListClick(){
        categoryMealAdapter.onItemClick = { meal ->

            val bundle = Bundle().apply {
                // use same key as u provide in the nav_graph
                putString("mealId" , meal.idMeal)
                putString("mealName" , meal.strMeal)
                putString("mealThumb" , meal.strMealThumb)
            }
            findNavController().navigate(R.id.action_detailCategoryMealsFragment_to_testFragment,bundle)
        }
    }

}