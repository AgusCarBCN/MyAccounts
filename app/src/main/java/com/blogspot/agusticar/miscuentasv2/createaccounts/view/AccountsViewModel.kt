package com.blogspot.agusticar.miscuentasv2.createaccounts.view


import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.SnackBarController
import com.blogspot.agusticar.miscuentasv2.SnackBarEvent
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.model.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.GetAllAccountsUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.InsertAccountUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.UpdateAccountBalance
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.InsertEntryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val getCurrencyCode: GetCurrencyCodeUseCase,
    private val setCurrencyCode: SetCurrencyCodeUseCase,
    private val addAccount: InsertAccountUseCase,
    private val addEntry:InsertEntryUseCase,
    private val getAccounts: GetAllAccountsUseCase,
    private val updateBalance: UpdateAccountBalance

) : ViewModel() {


    private val _isCurrencyExpanded = MutableLiveData<Boolean>()
    val isCurrencyExpanded: LiveData<Boolean> = _isCurrencyExpanded

    private val _currencyCode = MutableLiveData<String>()
    val currencyCode: LiveData<String> = _currencyCode

    // LiveData para los campos de texto
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> = _amount

    //LiveData para categoria seleccionada

    private val _categorySeleted = MutableLiveData<Category>()
    val categorySelected: LiveData<Category> = _categorySeleted

    //LiveData para cuenta seleccionada

    private val _accountSelected = MutableLiveData<Account>()
    val accountSelected: LiveData<Account> = _accountSelected

    //LiveDatas de transferencia entre cuentas

    private val _originAccount = MutableLiveData<Account>()
    val originAccount: LiveData<Account> = _originAccount

    private val _destinationAccount = MutableLiveData<Account>()
    val destinationAccount: LiveData<Account> = _destinationAccount


    private val _listOfAccounts = MutableLiveData<List<Account>>()
    val listOfAccounts: LiveData<List<Account>> = _listOfAccounts

    private val _listOfCategories = MutableLiveData<List<Category>>()
    val listOfCategories: LiveData<List<Category>> = _listOfCategories

    private val _currencyCodeList = MutableLiveData<List<Currency>>()
    val currencyCodeList: LiveData<List<Currency>> = _currencyCodeList

    init {
        viewModelScope.launch {
            _currencyCode.value = getCurrencyCode()
            _isCurrencyExpanded.value = false

        }
    }


    fun getCategories(status: Boolean) {
        if(status){
            _listOfCategories.value=incomeCategories
        }else{
            _listOfCategories.value=expenseCategories
        }
    }


    fun addAccount(account: Account) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                addAccount.invoke(account)
                Log.d("Cuenta", "Cuenta creada")
                resetFields()
            }
        } catch (e: Exception) {
            Log.d("Cuenta", "Error: ${e.message}")
        }
    }
    fun transferEntry(
                      originEntry:Entry,
                      destinationEntry:Entry):Int {
        var success=1
        val originAccount=_originAccount.value
        val destinationAccount=_destinationAccount.value
        if(isValidTransfer()){
            viewModelScope.launch(Dispatchers.IO){
                var balanceOrigin=originAccount?.balance?:0.0
                var balanceDestination=destinationAccount?.balance?:0.0
                if(balanceOrigin>=originEntry.amount){
                    //Actualización de balances de cuentas en base de datos
                    balanceOrigin-=originEntry.amount
                    addEntry.invoke(originEntry)
                    updateAccountBalance(originAccount?.id?:0, balanceOrigin)
                    balanceDestination+=destinationEntry.amount
                    addEntry.invoke(destinationEntry)
                    updateAccountBalance(destinationAccount?.id?:0, balanceDestination)
                } else {
                    success=0
                }
            }
        }else{
            success=2
        }
        return success
    }
    fun addEntry(entry: Entry):Int {
        var success=1
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val account=_accountSelected.value
                var balance=account?.balance?:0.0
                if(balance>=entry.amount) {
                    val id = account?.id ?: 0
                    balance += entry.amount
                    addEntry.invoke(entry)
                    updateAccountBalance(id, balance)
                }else{
                    success=0
                }
                resetFields()
            }
        } catch (e: Exception) {
            Log.d("Cuenta", "Error: ${e.message}")
        }
        return success
    }
    private fun updateAccountBalance(accountId: Int, newBalance: Double) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                updateBalance.invoke(accountId, abs(newBalance))
            }
        } catch (e: Exception) {
            Log.d("Cuenta", "Error: ${e.message}")
        }
    }

    fun getAllAccounts() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _listOfAccounts.postValue(getAccounts.invoke())
                Log.d("Cuentas creadas", "Cuentas cargadas")
            }
        } catch (e: Exception) {
            Log.d("Cuentas", "Error al cargar")
        }

    }

    private fun resetFields() {
        _name.postValue("") // Vaciar el nombre de la cuenta
        _amount.postValue("") // Vaciar el balance de la cuenta
    }

    fun onCurrencySelectedChange(currencySelected: String) {
        _currencyCode.value = currencySelected

    }

    fun onCategorySelected(categorySelected: Category) {
        _categorySeleted.value = categorySelected
    }

    fun onAccountSelected(accountSelected: Account) {
        _accountSelected.value = accountSelected
    }

    fun onOriginAccountSelected(accountSelected: Account) {
        _originAccount.value = accountSelected
    }
    fun onDestinationAccountSelected(accountSelected: Account) {
        _destinationAccount.value = accountSelected
    }

    fun setCurrencyCode(currencyCode: String) {
        viewModelScope.launch {
            setCurrencyCode.invoke(currencyCode)

        }
    }


    fun getListOfCurrencyCode(): List<Currency> {
        //Ordeno la lista por la descripción de la divisa
        val sortedCurrencies = currencies.sortedBy { it.currencyDescription }
        _currencyCodeList.value = sortedCurrencies

        return sortedCurrencies
    }

    fun onExpandedChange(newValue: Boolean) {
        _isCurrencyExpanded.value = newValue
    }

    fun onNameChanged(newName: String) {
        _name.value = newName

    }

    fun onAmountChanged(newBalance: String) {
        if (isValidDecimal(newBalance)) {
            _amount.value = newBalance
        }
    }

    fun numberFormat(amount: Double): String {

        val locale = currencyLocales[_currencyCode.value] ?: Locale.GERMAN
        // Formatear la cantidad en la moneda especificada
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        // Iniciar la carga de cuentas solo cuando el Composable se inicia
        return numberFormat.format(
            abs(amount)
        )
    }

    private fun isValidTransfer():Boolean=_originAccount.value?.id==_destinationAccount.value?.id

    private fun isValidDecimal(text: String): Boolean {

        return text.isEmpty() || text.matches(Regex("^([1-9]\\d*|0)?(\\.\\d*)?\$"))
    }
    //Función para darle formato de la divisa actual a una cantidad de dinero

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


    private val incomeCategories = listOf(
        Category(
            iconResource = R.drawable.ic_category_salary,
            name = R.string.salary,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_dividens,
            name = R.string.dividens,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_rent,
            name = R.string.rental,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_freelances,
            name = R.string.freelance,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_sales,
            name = R.string.sales,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_donation,
            name = R.string.subsidies,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_lotery,
            name = R.string.lotery,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_otherincomes,
            name = R.string.otherincomes,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_premium,
            name = R.string.awards,  // Usando el recurso de string
            isIncome = true
        ),
        Category(
            iconResource = R.drawable.ic_category_winasset,
            name = R.string.benefit_assets,  // Usando el recurso de string
            isIncome = true
        ),
    )
    private val expenseCategories = listOf(
        Category(
            iconResource = R.drawable.ic_category_grocery,
            name = R.string.food,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_mortgage,
            name = R.string.morgage,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_electricity,
            name = R.string.electricitybill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_water,
            name = R.string.waterbill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_gasbill,
            name = R.string.gasbill,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_publictansport,
            name = R.string.publictransport,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_fuelcar,
            name = R.string.fuel_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_otherinsurance,
            name = R.string.insurances,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_healthbill,
            name = R.string.health,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_leiure,
            name = R.string.entertainment,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_subscriptions,
            name = R.string.subscriptions,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_vacation,
            name = R.string.vacations_travel_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_clothing,
            name = R.string.clothing,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_education,
            name = R.string.courses_books_materials_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_house,
            name = R.string.house,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_repaircar,
            name = R.string.car,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_sport,
            name = R.string.gym,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_pet,
            name = R.string.pets,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_personalcare,
            name = R.string.personal_care_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_gif,
            name = R.string.gifts_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_donation,
            name = R.string.donations_title,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_lostasset,
            name = R.string.lost_assets,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_books,
            name = R.string.books,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_music,
            name = R.string.music,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_hobies,
            name = R.string.hobbies,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_tax,
            name = R.string.taxes,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_loan,
            name = R.string.loans,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_electronic,
            name = R.string.electronics,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_coffe,
            name = R.string.coffee,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_tabac,
            name = R.string.tobacco,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_sportsuplement,
            name = R.string.supplements,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_bike,
            name = R.string.motorcycle,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_garden,
            name = R.string.garden,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_teraphy,
            name = R.string.therapies,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_alcohol,
            name = R.string.alcohol,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_game,
            name = R.string.gambling,  // Usando el recurso de string
            isIncome = false
        ),
        Category(
            iconResource = R.drawable.ic_category_otherincomes,
            name = R.string.other_expenses,  // Usando el recurso de string
            isIncome = false
        )
    )
}



