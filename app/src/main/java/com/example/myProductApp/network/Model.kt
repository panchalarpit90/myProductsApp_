package com.example.myProductApp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model(
    val limit: Int = 0,
    val products: List<Product> = emptyList(),
    val skip: Int = 0,
    val total: Int = 0
) : Parcelable