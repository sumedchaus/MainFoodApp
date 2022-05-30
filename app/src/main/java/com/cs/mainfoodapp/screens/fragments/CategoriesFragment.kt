package com.cs.mainfoodapp.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cs.mainfoodapp.R
import com.cs.mainfoodapp.databinding.FragmentCategoriesBinding
import com.cs.mainfoodapp.screens.adapters.CategoriesAdapter
import com.cs.mainfoodapp.screens.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var categoryListAdapter: CategoriesAdapter


    private lateinit var binding: FragmentCategoriesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryListAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCategories()
        getCategoriesList()
        categoryListItemsRecyclerView()
        onCategoryClick()
    }

    private fun getCategoriesList() {
        viewModel.categoryListLiveData.observe(viewLifecycleOwner, Observer { categoryList ->
            categoryListAdapter.differ.submitList(categoryList)

        })
    }

    private fun categoryListItemsRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(activity, 3)

            adapter = categoryListAdapter
        }
    }

    private fun onCategoryClick() {
        categoryListAdapter.onItemClick = { category ->

            val bundle = Bundle().apply {
                // use same key as u provide in the nav_graph
                putString("categoryName",category.strCategory)
            }
            findNavController().navigate(R.id.action_categoriesFragment_to_detailCategoryMealsFragment, bundle)
        }
    }

}