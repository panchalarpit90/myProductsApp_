package com.example.myProductApp.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    val barcode: String? = null,
    val createdAt: String? = null,
    val qrCode: String? = null,
    val updatedAt: String? = null
):Parcelable