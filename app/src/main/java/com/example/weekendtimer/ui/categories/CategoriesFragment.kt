package com.example.weekendtimer.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weekendtimer.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CategoriesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentCategoriesBinding.inflate(inflater, container, false).apply {
            _binding = this
            val adapter = CategoriesAdapter()
            viewModel.categories.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                }
            )
            binding.rvCategories.adapter = adapter
            binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        }.root
    }
}