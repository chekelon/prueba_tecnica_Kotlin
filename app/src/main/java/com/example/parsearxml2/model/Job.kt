package com.example.parsearxml2.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job (
    @SerializedName("title")
    val title: String?=null,
    @SerializedName("date")
    val date: String?=null,
    @SerializedName("referencenumber")
    val referencenumber: String?=null,
    @SerializedName("url")
    val url: String?=null,
    @SerializedName("company")
    val company: String?=null,
    @SerializedName("city")
    val city: String?=null,
    @SerializedName("state")
    val state: String?=null,
    @SerializedName("country")
    val country: String?=null,
    @SerializedName("description")
    val description: String?=null
):Parcelable