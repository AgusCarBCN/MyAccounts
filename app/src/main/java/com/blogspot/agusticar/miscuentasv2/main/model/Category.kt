package com.blogspot.agusticar.miscuentasv2.main.model


class Category(private val description:Int,private val iconResource:Int) {

    val categoryDescription: Int
        get()= description

    val categoryIcon: Int
        get()= iconResource
}