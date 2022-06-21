package com.tenessine.intoreactiveflow.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @field:SerializedName("features")
    val features: List<PlacesItem>
)

data class PlacesItem(
    @field:SerializedName("place_name")
    val placeName: String,
)