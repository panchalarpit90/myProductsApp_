package com.example.myProductApp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Dimensions(
    val length: Double = 0.0,
    val width: Double = 0.0,
    val height: Double = 0.0
) : Parcelable