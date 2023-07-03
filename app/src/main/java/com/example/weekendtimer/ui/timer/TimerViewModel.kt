package com.example.weekendtimer.ui.timer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.system.measureTimeMillis

// The @HiltViewModel annotation tells Hilt to provide this ViewModel.
// The @Inject constructor tells Hilt how to construct this ViewModel.
@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    // A Job object from the kotlinx.coroutines library representing the countdown operation, allowing it to be cancelled.
    private var countdownJob: Job? = null

    // The MutableLiveData object that represents the countdown display text. It's private because only the ViewModel should modify it.
    // MutableLiveData is a type of LiveData that can be modified.
    private val _countdownDisplay = MutableLiveData("3:000")
    // The public LiveData object representing the countdown display text. Views will observe this but cannot modify it.
    val countdownDisplay: LiveData<String> = _countdownDisplay

    // The MutableLiveData object that represents whether to navigate to the main screen. It's private because only the ViewModel should modify it.
    private val _navigateToMain = MutableLiveData(false)
    // The public LiveData object representing whether to navigate to the main screen. Views will observe this but cannot modify it.
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    // Function to start the countdown. Cancels any previous countdowns.
    fun startCountdown() {
        countdownJob?.cancel() // Cancel any existing countdown job

        // Start a new countdown in the ViewModel's scope, allowing it to be cancelled when the ViewModel is cleared.
        countdownJob = viewModelScope.launch {
            // Set the initial countdown time in milliseconds.
            val initialMillis = 3000L
            var millisRemaining = initialMillis // Variable to keep track of the remaining time

            // Countdown loop. Continues until no time remains.
            while (millisRemaining >= 0) {
                // Measure the time spent in this block, so we can adjust the remaining time.
                val timeElapsed = measureTimeMillis {
                    // Calculate the remaining time in seconds and milliseconds.
                    val secondsRemaining = (millisRemaining / 1000).toInt()
                    val millisecondsRemaining = (millisRemaining % 1000).toInt()

                    // Format the countdown text and set the _countdownDisplay LiveData.
                    val countdownText = String.format(Locale.getDefault(), "%d:%03d", secondsRemaining, millisecondsRemaining)
                    _countdownDisplay.value = countdownText

                    // Wait for a short period of time before the next loop iteration.
                    delay(10) // Adjust the delay for a smoother countdown if needed
                    // Decrease the remaining time by the delay amount.
                    millisRemaining -= 10 // Decrease the milliseconds by the delay amount
                }

                // Adjust the remaining time based on the elapsed time. This ensures the countdown stays accurate even if there's a delay in the code execution.
                millisRemaining -= timeElapsed
            }

            // When the countdown finishes, set _navigateToMain to true, which will trigger navigation in the view.
            _navigateToMain.value = true
        }
    }

    // Function to reset _navigateToMain after navigation is complete.
    fun onMainNavigated() {
        _navigateToMain.value = false
    }

    // When the ViewModel is cleared (usually when the associated view is destroyed), cancel the countdown job to avoid doing work for a view that no longer exists.
    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel() // Cancel countdown job when the ViewModel is cleared
    }
}
