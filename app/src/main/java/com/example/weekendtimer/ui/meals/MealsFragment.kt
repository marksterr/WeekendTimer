package com.example.weekendtimer.ui.meals

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weekendtimer.databinding.FragmentMealsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealsFragment : Fragment() {

    private var _binding: FragmentMealsBinding? = null
    private val binding get() = _binding!!
    private val category by lazy { arguments?.getString("category") }
    private val viewModel by viewModels<MealsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentMealsBinding.inflate(inflater, container, false).apply {
            _binding = this
            viewModel.getMeals(category!!)
            val adapter = MealsAdapter()
            viewModel.meals.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.setData(it) // Set new data into adapter
                }
            )
            binding.rvMeals.adapter = adapter
            binding.rvMeals.layoutManager = LinearLayoutManager(requireContext())
        }.root
    }
}