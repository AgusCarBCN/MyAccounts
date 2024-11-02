package com.blogspot.agusticar.miscuentasv2.main.domain.apidata

import com.blogspot.agusticar.miscuentasv2.main.data.apicurrencydata.CurrencyConverterRepository
import com.blogspot.agusticar.miscuentasv2.retrofit.Currency
import retrofit2.Response
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val repository: CurrencyConverterRepository
) {
    suspend operator fun invoke(fromCurrency: String, toCurrency: String): Response<Currency> {
        return repository.convertCurrency(fromCurrency, toCurrency)
    }
}