package com.example.weekendtimer.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weekendtimer.R
import com.example.weekendtimer.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

// @AndroidEntryPoint is an annotation which indicates that Hilt should provide some dependencies to this Fragment.
@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    // Binding object instance with nullable safety checks on its usage.
    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    // Initialize the ViewModel by using 'viewModels()' delegated property provided by Hilt.
    private val viewModel by viewModels<CategoriesViewModel>()

    // Creates and returns the view hierarchy associated with the fragment.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflates the view using View Binding, sets the inflated view as this fragment's view and stores the binding object.
        return FragmentCategoriesBinding.inflate(inflater, container, false).apply {
            _binding = this
            // Create an instance of CategoriesAdapter
            val adapter = CategoriesAdapter { category ->
                val args : Bundle = bundleOf("category" to category.strCategory)
                findNavController().navigate(
                    R.id.action_navigation_categories_to_mealsFragment,
                    args
                )
            }
            // Observes the categories LiveData in the ViewModel. When the categories data changes, the adapter updates the RecyclerView's data.
            viewModel.categories.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.setData(it) // Set new data into adapter
                }
            )
            // Sets the RecyclerView's adapter to the CategoriesAdapter.
            binding.rvCategories.adapter = adapter
            // Sets the RecyclerView's layout manager to be a LinearLayoutManager, which means the items in the RecyclerView will be displayed in a linear list.
            binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        }.root
    }
}
