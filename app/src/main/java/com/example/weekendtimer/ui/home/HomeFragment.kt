package com.example.weekendtimer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weekendtimer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

// Declaring the HomeFragment class and it's a subclass of the Fragment class.
@AndroidEntryPoint
class HomeFragment : Fragment() {

    // Declaring a nullable binding variable that can be accessed from anywhere inside the class.
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    // The non-null assertion operator is used to avoid having to null-check every time _binding is accessed.
    private val binding get() = _binding!!

    // The onCreateView() method is called when it's time for the fragment to draw its UI for the first time.
    // This method returns the View that is the root of the fragment's layout.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Instantiating the ViewModel using ViewModelProvider.
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Inflate the layout XML file for this fragment using the inflater, and assign it to _binding.
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Getting a reference to the root View of the fragment's layout.
        val root: View = binding.root

        // Getting a reference to the TextView widget with ID textHome from the binding object.
        val textView: TextView = binding.textHome

        // Observing the LiveData object from the ViewModel. When it changes, the code in the lambda will be executed.
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it // Setting the text of the TextView to the value of the LiveData object.
        }

        // Returning the root View.
        return root
    }

    // This method is called when the view and other resources created in onCreateView() need to be destroyed.
    override fun onDestroyView() {
        super.onDestroyView()

        // Nullify the binding object when the view is destroyed to avoid memory leaks.
        _binding = null
    }
}
