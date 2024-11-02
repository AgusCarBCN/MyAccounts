package com.blogspot.agusticar.miscuentasv2.main.data.apicurrencydata


import com.blogspot.agusticar.miscuentasv2.retrofit.Currency
import com.blogspot.agusticar.miscuentasv2.retrofit.CurrencyConverterApi
import retrofit2.Response
import javax.inject.Inject

class CurrencyConverterRepository @Inject constructor(
    private val api: CurrencyConverterApi
) {
    suspend fun convertCurrency(fromCurrency: String, toCurrency: String): Response<Currency> {
        return api.convertCurrency(fromCurrency, toCurrency)
    }
}