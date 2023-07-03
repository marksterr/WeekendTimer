package com.example.weekendtimer.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weekendtimer.R
import com.example.weekendtimer.databinding.FragmentTimerBinding
import dagger.hilt.android.AndroidEntryPoint

// The @AndroidEntryPoint annotation tells Hilt this fragment needs to participate in dependency injection.
@AndroidEntryPoint
// This is a Fragment. A Fragment represents a portion of the user interface in an Activity.
class TimerFragment : Fragment() {

    // Here we're getting a reference to the TimerViewModel using by viewModels(),
    // which will give us a new or existing ViewModel scoped to this Fragment.
    private val viewModel: TimerViewModel by viewModels()

    // We're using view binding to access our views. _binding is nullable and will be set to null in onDestroyView.
    private var _binding: FragmentTimerBinding? = null
    // This is a non-nullable property that delegates to _binding. If _binding is null, this will throw a NullPointerException.
    private val binding get() = _binding!!

    // This function is called to inflate the layout for this Fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Here we're inflating our binding.
        _binding = FragmentTimerBinding.inflate(inflater, container, false)

        // We're setting the lifecycleOwner for our binding to the viewLifecycleOwner, which represents the lifecycle of this Fragment's view (as opposed to the lifecycle of the Fragment itself).
        // This allows LiveData in our ViewModel to automatically update our UI.
        binding.lifecycleOwner = viewLifecycleOwner
        // We're setting the ViewModel for our binding to our TimerViewModel.
        binding.viewModel = viewModel

        // We're observing the 'navigateToMain' LiveData in our ViewModel. When it's true, we navigate to the home destination
        // and call 'onMainNavigated()' in our ViewModel to reset the LiveData.
        viewModel.navigateToMain.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                findNavController().navigate(R.id.action_timer_to_home)
                viewModel.onMainNavigated()
            }
        }

        // We're returning the root view of our binding.
        return binding.root
    }

    // This is called when the view created in 'onCreateView' is destroyed.
    // Here we're cleaning up our binding.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
