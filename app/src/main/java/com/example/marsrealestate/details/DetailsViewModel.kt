package com.example.marsrealestate.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marsrealestate.network.MarsProperty

class DetailsViewModel(marsProperty: MarsProperty,app: Application) : ViewModel (){
    private val _selectedProperty=MutableLiveData<MarsProperty>()
    val selectedProperty:LiveData<MarsProperty>
        get() = _selectedProperty
    init {
        _selectedProperty.value=marsProperty
    }

}