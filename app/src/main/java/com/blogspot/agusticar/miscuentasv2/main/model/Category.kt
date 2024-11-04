package com.blogspot.agusticar.miscuentasv2.main.model


data class Category(

    val iconResource: Int,
    val name: Int,
    val isIncome: Boolean,
    var isChecked:Boolean=false
){
    var isCheckedValue:Boolean
        get() =isChecked
        set(value) {
            isChecked = value
        }
}