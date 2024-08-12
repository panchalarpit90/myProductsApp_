package com.example.myProductApp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val comment: String? = null,
    val reviewerName:String? = null,
    val reviewerEmail:String? = null,
    val rating: Double = 0.0

) : Parcelable
