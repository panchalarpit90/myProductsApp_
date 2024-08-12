package com.example.myProductApp.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myProductApp.network.Product
import com.example.myProductApp.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class StatusApi { LOADING, ERROR, LOADED }

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<StatusApi>()
    val status: LiveData<StatusApi>
        get() = _status

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _navigateToSelectedProduct = MutableLiveData<Product?>()
    val navigateToSelectedProduct: LiveData<Product?>
        get() = _navigateToSelectedProduct

    private val _displayList = MutableLiveData<List<ListItem>>()
    val displayList: LiveData<List<ListItem>>
        get() = _displayList

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _status.postValue(StatusApi.LOADING)
                val result = ProductApi.retrofitService.getProducts(limit = 100)
                _products.postValue(result.products)
                processProducts(result.products)
                _status.postValue(StatusApi.LOADED)
            } catch (e: Exception) {
                _status.postValue(StatusApi.ERROR)
            }
        }
    }

    private fun processProducts(products: List<Product>) {
        val displayList = mutableListOf<ListItem>()
        val groupedProducts = products.groupBy { it.category }

        groupedProducts.forEach { (category, products) ->
            if (category != null) {
                displayList.add(ListItem.CategoryHeader(category))
                products.forEach { product ->
                    displayList.add(ListItem.ProductItem(product))
                }
            }
        }
        _displayList.postValue(displayList)
    }

    fun displayProductDetails(product: Product) {
        _navigateToSelectedProduct.value = product
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }
}
