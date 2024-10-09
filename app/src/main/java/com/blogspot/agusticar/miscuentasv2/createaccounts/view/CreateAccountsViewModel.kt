package com.blogspot.agusticar.miscuentasv2.createaccounts.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetCurrencySymbolUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.SetSymbolCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountsViewModel @Inject constructor(private val getCurrencyCode:GetCurrencyCodeUseCase,
    private val setCurrencyCode:SetCurrencyCodeUseCase,
    private val getCurrencySymbol:GetCurrencySymbolUseCase,
    private val setCurrencySymbol:SetSymbolCurrencyUseCase) : ViewModel() {


    private val _currencyCode = MutableLiveData<String>()
    val currencyCode: LiveData<String> = _currencyCode

    private val _currencySymbol = MutableLiveData<String>()
    val currencySymbol: LiveData<String> = _currencySymbol

    private val _currencyCodeList = MutableLiveData<List<Currency>>()
    val currencyCodeList: LiveData<List<Currency>> = _currencyCodeList

    init{
        viewModelScope.launch {
            _currencyCode.value=getCurrencyCode()
            _currencySymbol.value=getCurrencySymbol()

        }
    }

    fun onCurrencySelectedChange(currencySelected:String){
        _currencyCode.value = currencySelected

    }

    fun setCurrencySymbol(currencyCode:String){

        viewModelScope.launch {
            setCurrencyCode.invoke(currencyCode)
            val symbol=getSymbol(currencyCode)
            setCurrencySymbol.invoke(symbol)

        }
    }


    fun getListOfCurrencyCode():List<Currency>{
        _currencyCodeList.value = currencies
        return currencies
    }

    private fun getSymbol(currency:String):String{
        _currencySymbol.value=currencySymbols[currency]
        return currencySymbols[currency]?:"USD"
    }

    private val currencySymbols = mapOf(
        "USD" to "$",       // Dólar estadounidense
        "EUR" to "€",       // Euro
        "JPY" to "¥",       // Yen japonés
        "GBP" to "£",       // Libra esterlina
        "AUD" to "A$",      // Dólar australiano
        "CAD" to "C$",      // Dólar canadiense
        "CHF" to "CHF",     // Franco suizo
        "CNY" to "¥",       // Yuan chino
        "SEK" to "kr",      // Corona sueca
        "NZD" to "NZ$",     // Dólar neozelandés
        "MXN" to "$",       // Peso mexicano
        "SGD" to "S$",      // Dólar de Singapur
        "HKD" to "HK$",     // Dólar de Hong Kong
        "NOK" to "kr",      // Corona noruega
        "RUB" to "₽",       // Rublo ruso
        "INR" to "₹",       // Rupia india
        "BRL" to "R$",      // Real brasileño
        "ZAR" to "R",       // Rand sudafricano
        "DKK" to "kr",      // Corona danesa
        "PLN" to "zł",      // Zloty polaco
        "THB" to "฿",       // Baht tailandés
        "AED" to "د.إ",     // Dirham de los Emiratos Árabes Unidos
        "MYR" to "RM",      // Ringgit malayo
        "PHP" to "₱",       // Peso filipino
        "ILS" to "₪",       // Shekel israelí
        "TRY" to "₺",       // Lira turca
        "CLP" to "$",       // Peso chileno
        "COP" to "$",       // Peso colombiano
        "PEN" to "S/.",     // Sol peruano
        "VND" to "₫"  ,     // Dong vietnamita
        "ARS" to "$"   ,     //Peso argentino
        "KRW" to "₩"   ,  //South Korean won
        "HNL"   to  "L"   //Honduras
    )
    private val currencies = listOf(
        Currency("USD", "US Dollar", R.drawable.us),
        Currency("EUR", "Euro", R.drawable.eu),
        Currency("JPY", "Yen japonés", R.drawable.jp),
        Currency("GBP", "British Pound", R.drawable.gb),
        Currency("AUD", "Australian Dollar", R.drawable.au),
        Currency("CAD", "Canadian Dollar", R.drawable.ca),
        Currency("CHF", "Swiss Franc", R.drawable.ch),
        Currency("CNY", "Yuan chino", R.drawable.cn),
        Currency("SEK", "Swedish Krona", R.drawable.se),
        Currency("NZD", "New Zealand Dollar", R.drawable.nz),
        Currency("MXN", "Peso mexicano", R.drawable.mx),
        Currency("SGD", "Singapore Dollar", R.drawable.sg),
        Currency("HKD", "Hong Kong Dollar", R.drawable.hk),
        Currency("NOK", "Norwegian Krone", R.drawable.no),
        Currency("RUB", "Russian Ruble", R.drawable.ru),
        Currency("INR", "Indian Rupee", R.drawable.`in`),
        Currency("BRL", "Real brasileño", R.drawable.br),
        Currency("ZAR", "South African Rand", R.drawable.za),
        Currency("DKK", "Danish Krone", R.drawable.dk),
        Currency("PLN", "Polish Zloty", R.drawable.pl),
        Currency("THB", "Thai Baht", R.drawable.th),
        Currency("AED", "UAE Dirham", R.drawable.sa),
        Currency("MYR", "Malaysian Ringgit", R.drawable.my),
        Currency("PHP", "Peso filipino", R.drawable.ph),
        Currency("ILS", "Israeli Shekel", R.drawable.il),
        Currency("TRY", "Turkish Lira", R.drawable.tr),
        Currency("CLP", "Peso chileno", R.drawable.cl),
        Currency("COP", "Peso colombiano", R.drawable.co),
        Currency("PEN", "Sol peruano", R.drawable.pe),
        Currency("VND", "Vietnamese Dong", R.drawable.vn),
        Currency("ARS", "Peso Argentino", R.drawable.ar),
        Currency("KRW", "South Korean Won", R.drawable.kr),
        Currency("HNL","Lempira de Honduras", R.drawable.hn)
    )
}

