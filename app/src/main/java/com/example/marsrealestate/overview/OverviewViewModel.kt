package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsrealestate.network.MarsApi
import com.example.marsrealestate.network.MarsApiFilter
import com.example.marsrealestate.network.MarsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


enum class StatusApi{LOADING,ERROR}
class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<StatusApi>()
    val status: LiveData<StatusApi>
        get() = _status
    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty?>()
    val navigateToSelectedProperty: LiveData<MarsProperty?>
        get() = _navigateToSelectedProperty
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateProperties(filter:MarsApiFilter) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _status.value=StatusApi.LOADING
                var listResult = MarsApi.retrofitService.getProperties(filter.value)
                    _properties.value = listResult

            } catch (e: Exception) {
                _status.value = StatusApi.ERROR
            }
        }
    }
    fun updateFilter(filter: MarsApiFilter){
        getMarsRealEstateProperties(filter)

    }
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }


}