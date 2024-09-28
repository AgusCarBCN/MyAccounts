package com.blogspot.agusticar.miscuentasv2.model

import com.blogspot.agusticar.miscuentasv2.component.CurrencySymbol

data class Currency(private val symbol: CurrencySymbol, private val iconResource:Int )

{
    val symbolItem: CurrencySymbol
    get() = symbol

    val iconResourceItem: Int
    get() = iconResource
}