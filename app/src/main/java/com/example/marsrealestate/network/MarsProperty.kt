package com.example.marsrealestate.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsProperty(
    val id: String,
    val img_src: String,
    val price: Int,
    val type: String
):Parcelable