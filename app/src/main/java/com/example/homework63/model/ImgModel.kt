package com.example.homework63.model

import java.io.Serializable

data class ImgModel(
    var img: String? = null,
    var isClicked: Boolean = false
) : Serializable
