package com.blogspot.agusticar.miscuentasv2.createaccounts.view

import androidx.compose.ui.input.key.Key.Companion.U
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

    init {
        viewModelScope.launch {
            _currencyCode.value = getCurrencyCode()
            _currencySymbol.value = getCurrencySymbol()

        }
    }

    fun onCurrencySelectedChange(currencySelected: String) {
        _currencyCode.value = currencySelected

    }

    fun setCurrencySymbol(currencyCode: String) {

        viewModelScope.launch {
            setCurrencyCode.invoke(currencyCode)
            val symbol = getSymbol(currencyCode)
            setCurrencySymbol.invoke(symbol)

        }
    }


    fun getListOfCurrencyCode(): List<Currency> {
        _currencyCodeList.value = currencies
        return currencies
    }

    private fun getSymbol(currency: String): String {
        _currencySymbol.value = currencySymbols[currency]
        return currencySymbols[currency] ?: "USD"
    }

   /* private val currencySymbols = mapOf(
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
        "VND" to "₫",     // Dong vietnamita
        "ARS" to "$",     //Peso argentino
        "KRW" to "₩",  //South Korean won
        "HNL" to "L"   //Honduras
    )*/

    /*  private val currencies = listOf(
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
}*/
    private val currencies = listOf(
        // Lista completa de todas las divisas del mundo, ordenadas alfabéticamente por código:

        Currency("AED", "UAE Dirham", R.drawable.sa),
        Currency("AFN", "Afghan Afghani", R.drawable.af),
        Currency("ALL", "Albanian Lek", R.drawable.al),
        Currency("AMD", "Armenian Dram", R.drawable.am),
        Currency("AOA", "Angolan Kwanza", R.drawable.ao),
        Currency("ARS", "Peso Argentino", R.drawable.ar),
        Currency("AUD", "Australian Dollar", R.drawable.au),
        Currency("AWG", "Aruban Florin", R.drawable.aw),
        Currency("AZN", "Azerbaijani Manat", R.drawable.az),
        Currency("BAM", "Bosnia-Herzegovina Convertible Mark", R.drawable.ba),
        Currency("BBD", "Barbadian Dollar", R.drawable.bb),
        Currency("BDT", "Bangladeshi Taka", R.drawable.bd),
        Currency("BGN", "Bulgarian Lev", R.drawable.bg),
        Currency("BHD", "Bahraini Dinar", R.drawable.bh),
        Currency("BIF", "Burundian Franc", R.drawable.bi),
        Currency("BMD", "Bermudian Dollar", R.drawable.bm),
        Currency("BND", "Brunei Dollar", R.drawable.bn),
        Currency("BOB", "Bolivian Boliviano", R.drawable.bo),
        Currency("BRL", "Real brasileño", R.drawable.br),
        Currency("BSD", "Bahamian Dollar", R.drawable.bs),
        Currency("BTN", "Bhutanese Ngultrum", R.drawable.bt),
        Currency("BWP", "Botswana Pula", R.drawable.bw),
        Currency("BYN", "Belarusian Ruble", R.drawable.by),
        Currency("BZD", "Belize Dollar", R.drawable.bz),
        Currency("CAD", "Canadian Dollar", R.drawable.ca),
        Currency("CDF", "Congolese Franc", R.drawable.cd),
        Currency("CHF", "Swiss Franc", R.drawable.ch),
        Currency("CLP", "Peso chileno", R.drawable.cl),
        Currency("CNY", "Yuan chino", R.drawable.cn),
        Currency("COP", "Peso colombiano", R.drawable.co),
        Currency("CRC", "Costa Rican Colón", R.drawable.cr),
        Currency("CUP", "Cuban Peso", R.drawable.cu),
        Currency("CVE", "Cape Verdean Escudo", R.drawable.cv),
        Currency("CZK", "Czech Koruna", R.drawable.cz),
        Currency("DJF", "Djiboutian Franc", R.drawable.dj),
        Currency("DKK", "Danish Krone", R.drawable.dk),
        Currency("DOP", "Dominican Peso", R.drawable.dom),
        Currency("DZD", "Algerian Dinar", R.drawable.dz),
        Currency("EGP", "Egyptian Pound", R.drawable.eg),
        Currency("ERN", "Eritrean Nakfa", R.drawable.er),
        Currency("ETB", "Ethiopian Birr", R.drawable.et),
        Currency("EUR", "Euro", R.drawable.eu),
        Currency("FJD", "Fijian Dollar", R.drawable.fj),
        Currency("FKP", "Falkland Islands Pound", R.drawable.fk),
        Currency("FOK", "Faroese Króna", R.drawable.fo),
        Currency("GBP", "British Pound", R.drawable.gb),
        Currency("GEL", "Georgian Lari", R.drawable.ge),
        Currency("GHS", "Ghanaian Cedi", R.drawable.gh),
        Currency("GIP", "Gibraltar Pound", R.drawable.gi),
        Currency("GMD", "Gambian Dalasi", R.drawable.gm),
        Currency("GNF", "Guinean Franc", R.drawable.gn),
        Currency("GTQ", "Guatemalan Quetzal", R.drawable.gt),
        Currency("GYD", "Guyanese Dollar", R.drawable.gy),
        Currency("HKD", "Hong Kong Dollar", R.drawable.hk),
        Currency("HNL", "Lempira de Honduras", R.drawable.hn),
        Currency("HRK", "Croatian Kuna", R.drawable.hr),
        Currency("HTG", "Haitian Gourde", R.drawable.ht),
        Currency("HUF", "Hungarian Forint", R.drawable.hu),
        Currency("IDR", "Indonesian Rupiah", R.drawable.id),
        Currency("ILS", "Israeli Shekel", R.drawable.il),
        Currency("INR", "Indian Rupee", R.drawable.`in`),
        Currency("IQD", "Iraqi Dinar", R.drawable.iq),
        Currency("IRR", "Iranian Rial", R.drawable.ir),
        Currency("ISK", "Icelandic Krona", R.drawable.`is`),
        Currency("JMD", "Jamaican Dollar", R.drawable.jm),
        Currency("JOD", "Jordanian Dinar", R.drawable.jo),
        Currency("JPY", "Yen japonés", R.drawable.jp),
        Currency("KES", "Kenyan Shilling", R.drawable.ke),
        Currency("KGS", "Kyrgyzstani Som", R.drawable.kg),
        Currency("KHR", "Cambodian Riel", R.drawable.kh),
        Currency("KMF", "Comorian Franc", R.drawable.km),
        Currency("KRW", "South Korean Won", R.drawable.kr),
        Currency("KWD", "Kuwaiti Dinar", R.drawable.kw),
        Currency("KYD", "Cayman Islands Dollar", R.drawable.ky),
        Currency("KZT", "Kazakhstani Tenge", R.drawable.kz),
        Currency("LAK", "Lao Kip", R.drawable.la),
        Currency("LBP", "Lebanese Pound", R.drawable.lb),
        Currency("LKR", "Sri Lankan Rupee", R.drawable.lk),
        Currency("LRD", "Liberian Dollar", R.drawable.lr),
        Currency("LSL", "Lesotho Loti", R.drawable.ls),
        Currency("LYD", "Libyan Dinar", R.drawable.ly),
        Currency("MAD", "Moroccan Dirham", R.drawable.ma),
        Currency("MDL", "Moldovan Leu", R.drawable.md),
        Currency("MGA", "Malagasy Ariary", R.drawable.mg),
        Currency("MKD", "Macedonian Denar", R.drawable.mk),
        Currency("MMK", "Burmese Kyat", R.drawable.mm),
        Currency("MNT", "Mongolian Tögrög", R.drawable.mn),
        Currency("MOP", "Macanese Pataca", R.drawable.mo),
        Currency("MRU", "Mauritanian Ouguiya", R.drawable.mr),
        Currency("MUR", "Mauritian Rupee", R.drawable.mu),
        Currency("MVR", "Maldivian Rufiyaa", R.drawable.mv),
        Currency("MWK", "Malawian Kwacha", R.drawable.mw),
        Currency("MXN", "Peso mexicano", R.drawable.mx),
        Currency("MYR", "Malaysian Ringgit", R.drawable.my),
        Currency("MZN", "Mozambican Metical", R.drawable.mz),
        Currency("NAD", "Namibian Dollar", R.drawable.na),
        Currency("NGN", "Nigerian Naira", R.drawable.ng),
        Currency("NIO", "Nicaraguan Córdoba", R.drawable.ni),
        Currency("NOK", "Norwegian Krone", R.drawable.no),
        Currency("NPR", "Nepalese Rupee", R.drawable.np),
        Currency("NZD", "New Zealand Dollar", R.drawable.nz),
        Currency("OMR", "Omani Rial", R.drawable.om),
        Currency("PAB", "Panamanian Balboa", R.drawable.pa),
        Currency("PEN", "Sol peruano", R.drawable.pe),
        Currency("PGK", "Papua New Guinean Kina", R.drawable.pg),
        Currency("PHP", "Peso filipino", R.drawable.ph),
        Currency("PKR", "Pakistani Rupee", R.drawable.pk),
        Currency("PLN", "Polish Zloty", R.drawable.pl),
        Currency("PYG", "Paraguayan Guarani", R.drawable.py),
        Currency("QAR", "Qatari Riyal", R.drawable.qa),
        Currency("RON", "Romanian Leu", R.drawable.ro),
        Currency("RSD", "Serbian Dinar", R.drawable.rs),
        Currency("RUB", "Russian Ruble", R.drawable.ru),
        Currency("RWF", "Rwandan Franc", R.drawable.rw),
        Currency("SAR", "Saudi Riyal", R.drawable.sa),
        Currency("SBD", "Solomon Islands Dollar", R.drawable.sb),
        Currency("SCR", "Seychellois Rupee", R.drawable.sc),
        Currency("SDG", "Sudanese Pound", R.drawable.sd),
        Currency("SEK", "Swedish Krona", R.drawable.se),
        Currency("SGD", "Singapore Dollar", R.drawable.sg),
        Currency("SHP", "Saint Helena Pound", R.drawable.sh),
        Currency("SLL", "Sierra Leonean Leone", R.drawable.sl),
        Currency("SOS", "Somali Shilling", R.drawable.so),
        Currency("SRD", "Surinamese Dollar", R.drawable.sr),
        Currency("SSP", "South Sudanese Pound", R.drawable.ss),
        Currency("STN", "São Tomé and Príncipe Dobra", R.drawable.st),
        Currency("SYP", "Syrian Pound", R.drawable.sy),
        Currency("SZL", "Eswatini Lilangeni", R.drawable.sz),
        Currency("THB", "Thai Baht", R.drawable.th),
        Currency("TJS", "Tajikistani Somoni", R.drawable.tj),
        Currency("TMT", "Turkmenistani Manat", R.drawable.tm),
        Currency("TND", "Tunisian Dinar", R.drawable.tn),
        Currency("TOP", "Tongan Paʻanga", R.drawable.to),
        Currency("TRY", "Turkish Lira", R.drawable.tr),
        Currency("TTD", "Trinidad and Tobago Dollar", R.drawable.tt),
        Currency("TWD", "New Taiwan Dollar", R.drawable.tw),
        Currency("TZS", "Tanzanian Shilling", R.drawable.tz),
        Currency("UAH", "Ukrainian Hryvnia", R.drawable.ua),
        Currency("UGX", "Ugandan Shilling", R.drawable.ug),
        Currency("USD", "US Dollar", R.drawable.us),
        Currency("UYU", "Uruguayan Peso", R.drawable.uy),
        Currency("UZS", "Uzbekistani Som", R.drawable.uz),
        Currency("VES", "Venezuelan Bolívar", R.drawable.ve),
        Currency("VND", "Vietnamese Dong", R.drawable.vn),
        Currency("VUV", "Vanuatu Vatu", R.drawable.vu),
        Currency("WST", "Samoan Tala", R.drawable.ws),
        Currency("XAF", "Central African CFA Franc", R.drawable.cf),
        Currency("XOF", "West African CFA Franc", R.drawable.wf),
        Currency("XPF", "CFP Franc", R.drawable.pf),
        Currency("YER", "Yemeni Rial", R.drawable.ye),
        Currency("ZAR", "South African Rand", R.drawable.za),
        Currency("ZMW", "Zambian Kwacha", R.drawable.zm),
        Currency("ZWL", "Zimbabwean Dollar", R.drawable.zw)
    )
    private val currencySymbols = mapOf(
        // Lista original:
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
        "VND" to "₫",       // Dong vietnamita
        "ARS" to "$",       // Peso argentino
        "KRW" to "₩",       // Won surcoreano
        "HNL" to "L",       // Lempira de Honduras

        // Divisas adicionales:
        "AFN" to "؋",       // Afgani afgano
        "ALL" to "L",       // Lek albanés
        "AMD" to "֏",       // Dram armenio
        "ANG" to "ƒ",       // Florín antillano neerlandés
        "AOA" to "Kz",      // Kwanza angoleño
        "AWG" to "ƒ",       // Florín arubeño
        "AZN" to "₼",       // Manat azerbaiyano
        "BAM" to "KM",      // Marco convertible bosnioherzegovino
        "BBD" to "Bds$",    // Dólar de Barbados
        "BDT" to "৳",       // Taka bangladesí
        "BHD" to ".د.ب",    // Dinar bahreiní
        "BIF" to "FBu",     // Franco burundés
        "BMD" to "$",       // Dólar bermudeño
        "BND" to "B$",      // Dólar de Brunéi
        "BOB" to "Bs.",     // Boliviano
        "BSD" to "$",       // Dólar bahameño
        "BTN" to "Nu.",     // Ngultrum butanés
        "BWP" to "P",       // Pula botsuano
        "BYN" to "Br",      // Rublo bielorruso
        "BZD" to "$",       // Dólar beliceño
        "CDF" to "FC",      // Franco congoleño
        "CRC" to "₡",       // Colón costarricense
        "CUP" to "₱",       // Peso cubano
        "CVE" to "$",       // Escudo caboverdiano
        "CZK" to "Kč",      // Corona checa
        "DJF" to "Fdj",     // Franco yibutiano
        "DOP" to "RD$",     // Peso dominicano
        "DZD" to "دج",      // Dinar argelino
        "EGP" to "£",       // Libra egipcia
        "ERN" to "Nfk",     // Nakfa eritreo
        "ETB" to "Br",      // Birr etíope
        "FJD" to "$",       // Dólar fiyiano
        "FKP" to "£",       // Libra malvinense
        "FOK" to "kr",      // Corona feroesa
        "GEL" to "₾",       // Lari georgiano
        "GHS" to "₵",       // Cedi ghanés
        "GIP" to "£",       // Libra gibraltareña
        "GMD" to "D",       // Dalasi gambiano
        "GNF" to "FG",      // Franco guineano
        "GTQ" to "Q",       // Quetzal guatemalteco
        "GYD" to "$",       // Dólar guyanés
        "HRK" to "kn",      // Kuna croata
        "HTG" to "G",       // Gourde haitiano
        "HUF" to "Ft",      // Forint húngaro
        "IDR" to "Rp",      // Rupia indonesia
        "IQD" to "ع.د",     // Dinar iraquí
        "IRR" to "﷼",      // Rial iraní
        "ISK" to "kr",      // Corona islandesa
        "JMD" to "J$",      // Dólar jamaiquino
        "JOD" to "د.ا",    // Dinar jordano
        "KES" to "KSh",     // Chelín keniano
        "KGS" to "с",       // Som kirguís
        "KHR" to "៛",      // Riel camboyano
        "KMF" to "CF",      // Franco comorense
        "KPW" to "₩",       // Won norcoreano
        "KWD" to "د.ك",     // Dinar kuwaití
        "KYD" to "$",       // Dólar de las Islas Caimán
        "KZT" to "₸",       // Tenge kazajo
        "LAK" to "₭",      // Kip laosiano
        "LBP" to "ل.ل",     // Libra libanesa
        "LKR" to "Rs",      // Rupia de Sri Lanka
        "LRD" to "$",       // Dólar liberiano
        "LSL" to "L",       // Loti lesotense
        "LYD" to "ل.د",     // Dinar libio
        "MAD" to "MAD",     // Dirham marroquí
        "MDL" to "L",       // Leu moldavo
        "MGA" to "Ar",      // Ariary malgache
        "MKD" to "ден",     // Denar macedonio
        "MMK" to "Ks",      // Kyat birmano
        "MNT" to "₮",      // Tugrik mongol
        "MOP" to "MOP$",    // Pataca de Macao
        "MRU" to "UM",      // Ouguiya mauritana
        "MUR" to "₨",      // Rupia mauriciana
        "MVR" to "Rf",      // Rufiyaa maldiva
        "MWK" to "MK",      // Kwacha malauí
        "MZN" to "MT",      // Metical mozambiqueño
        "NAD" to "$",       // Dólar namibio
        "NGN" to "₦",      // Naira nigeriana
        "NIO" to "C$",      // Córdoba nicaragüense
        "NPR" to "₨",      // Rupia nepalí
        "OMR" to "ر.ع.",   // Rial omaní
        "PAB" to "B/.",     // Balboa panameño
        "PGK" to "K",       // Kina de Papúa Nueva Guinea
        "PKR" to "₨",      // Rupia pakistaní
        "PYG" to "₲",      // Guaraní paraguayo
        "QAR" to "ر.ق",     // Riyal catarí
        "RON" to "lei",     // Leu rumano
        "RSD" to "дин",     // Dinar serbio
        "RWF" to "FRw",     // Franco ruandés
        "SAR" to "ر.س",     // Riyal saudí
        "SBD" to "$",       // Dólar de las Islas Salomón
        "SCR" to "₨",      // Rupia seychelense
        "SDG" to "ج.س.",   // Libra sudanesa
        "SHP" to "£",       // Libra de Santa Helena
        "SLL" to "Le",      // Leone de Sierra Leona
        "SOS" to "Sh",      // Chelín somalí
        "SRD" to "$",       // Dólar surinamés
        "SSP" to "£",       // Libra sursudanesa
        "STN" to "Db",      // Dobra de Santo Tomé y Príncipe
        "SYP" to "£",       // Libra siria
        "SZL" to "L",       // Lilangeni de Suazilandia
        "TJS" to "SM",      // Somoni tayiko
        "TMT" to "T",       // Manat turcomano
        "TND" to "د.ت",    // Dinar tunecino
        "TOP" to "T$",      // Paʻanga tongano
        "TTD" to "TT$",     // Dólar de Trinidad y Tobago
        "TWD" to "NT$",     // Nuevo dólar taiwanés
        "TZS" to "Sh",      // Chelín tanzano
        "UAH" to "₴",      // Grivna ucraniana
        "UGX" to "Sh",      // Chelín ugandés
        "UYU" to "$U",      // Peso uruguayo
        "UZS" to "лв",      // Som uzbeko
        "VES" to "Bs.S",    // Bolívar venezolano
        "VUV" to "VT",      // Vatu vanuatuense
        "WST" to "WS$",     // Tala samoano
        "XAF" to "FCFA",    // Franco CFA de África Central
        "XCD" to "$",       // Dólar del Caribe Oriental
        "XOF" to "CFA",     // Franco CFA de África Occidental
        "XPF" to "₣",      // Franco CFP
        "YER" to "﷼",      // Rial yemení
        "ZMW" to "ZK",      // Kwacha zambiano
        "ZWL" to "$",       // Dólar zimbabuense
    )


}