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

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private var countdownJob: Job? = null

    private val _countdownDisplay = MutableLiveData("3:000")
    val countdownDisplay: LiveData<String> = _countdownDisplay

    private val _navigateToMain = MutableLiveData(false)
    val navigateToMain: LiveData<Boolean> = _navigateToMain

    fun startCountdown() {
        countdownJob?.cancel()

        countdownJob = viewModelScope.launch {
            val initialMillis = 3000L
            var millisRemaining = initialMillis

            while (millisRemaining >= 0) {
                val timeElapsed = measureTimeMillis {
                    val secondsRemaining = (millisRemaining / 1000).toInt()
                    val millisecondsRemaining = (millisRemaining % 1000).toInt()

                    val countdownText = String.format(Locale.getDefault(), "%d:%03d", secondsRemaining, millisecondsRemaining)
                    _countdownDisplay.value = countdownText

                    delay(10) // Adjust the delay for a smoother countdown if needed
                    millisRemaining -= 10 // Decrease the milliseconds by the delay amount
                }

                // Adjust the remaining time based on the elapsed time
                millisRemaining -= timeElapsed
            }

            _navigateToMain.value = true
        }
    }

    fun onMainNavigated() {
        _navigateToMain.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel() // Cancel countdown job when the ViewModel is cleared
    }
}