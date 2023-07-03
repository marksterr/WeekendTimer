package com.example.weekendtimer.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Declaring the HomeViewModel class. It's a subclass of the ViewModel class.
class HomeViewModel : ViewModel() {

    // Creating a private MutableLiveData object. This type of LiveData can be changed, unlike regular LiveData.
    // The 'apply' block will execute immediately, and 'value' is set to the specified string.
    private val _text = MutableLiveData<String>().apply {
        value = "This is an app that displays a list of meal categories"
    }

    // Declaring a public LiveData object. It's immutable, so it can only be changed within this ViewModel.
    // This LiveData object references the private MutableLiveData, effectively making it read-only outside the ViewModel.
    val text: LiveData<String> = _text
}
