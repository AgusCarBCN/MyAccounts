package com.blogspot.agusticar.miscuentasv2.createaccounts.model

data class Currency(private val code: String, private val description:String )

{
    val currencyCode: String
        get() = code

    val currencyDescription: String
    get() = description

    override fun toString(): String {
        return "$currencyCode  $description"
    }
}