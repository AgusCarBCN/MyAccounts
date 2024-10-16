package com.blogspot.agusticar.miscuentasv2.createaccounts.view


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.createaccounts.model.Currency
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Account
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Entry
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.GetAllAccountsUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.InsertAccountUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.TransferUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.accountusecase.UpdateAccountBalance
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalExpensesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.GetSumTotalIncomesUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.entriesusecase.InsertEntryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.GetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.datastore.SetCurrencyCodeUseCase
import com.blogspot.agusticar.miscuentasv2.main.model.Category
import com.blogspot.agusticar.miscuentasv2.main.model.currencyLocales
import com.blogspot.agusticar.miscuentasv2.utils.Utils
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
    private val getAccounts: GetAllAccountsUseCase,
    private val updateBalance: UpdateAccountBalance,
    private val transferAmount:TransferUseCase

) : ViewModel() {

    private val _isEnableButtons = MutableLiveData<Boolean>()
    val isEnableButtons: LiveData<Boolean> = _isEnableButtons

    private val _isConfirmTransfer = MutableLiveData<Boolean>()
    val isConfirmTransfer: LiveData<Boolean> = _isConfirmTransfer

    private val _isCurrencyExpanded = MutableLiveData<Boolean>()
    val isCurrencyExpanded: LiveData<Boolean> = _isCurrencyExpanded

    private val _enableCurrencySelector = MutableLiveData<Boolean>()
    val enableCurrencySelector: LiveData<Boolean> = _enableCurrencySelector

    private val _currencyCode = MutableLiveData<String>()
    val currencyCode: LiveData<String> = _currencyCode

    // LiveData para los campos de texto
    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> = _amount

    //LiveData para cuenta seleccionada

    private val _accountSelected = MutableLiveData<Account?>()
    val accountSelected: MutableLiveData<Account?> = _accountSelected

    //LiveDatas de transferencia entre cuentas

    private val _destinationAccount = MutableLiveData<Account?>()
    val destinationAccount: MutableLiveData<Account?> = _destinationAccount

    private val _listOfAccounts = MutableLiveData<List<Account>>()
    val listOfAccounts: LiveData<List<Account>> = _listOfAccounts

    private val _currencyCodeList = MutableLiveData<List<Currency>>()
    val currencyCodeList: LiveData<List<Currency>> = _currencyCodeList

    init {
        viewModelScope.launch {
            _currencyCode.value = getCurrencyCode()
            _isCurrencyExpanded.value = false
            getAllAccounts()


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

    fun transferAmount(accountFromId:Int,accountToId: Int,amount:Double){

        try{
            viewModelScope.launch(Dispatchers.IO) {
                transferAmount.invoke(accountFromId, accountToId, amount)
                resetFields()
            }
        }catch (e: Exception){
            Log.d("Transaction", "Error: ${e.message}")
        }
    }


    fun updateEntry(accountId: Int, amount: Double,isTransferDestination:Boolean) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val account = _accountSelected.value
                val accountDestination=_destinationAccount.value
                val balance = account?.balance ?: 0.0
                val balanceDestination=accountDestination?.balance?:0.0
                if(!isTransferDestination) {
                    Log.d("Update", "amount ${amount}")
                    Log.d("Update", "balance ${balance}")
                    val newBalance = balance + amount
                    Log.d("Update", "newbalance ${newBalance}")
                    // Actualiza los saldos en ambas cuentas
                    updateAccountBalance(accountId, newBalance)

                }else{
                    Log.d("Update", "amount ${amount}")
                    Log.d("Update", "balance ${balanceDestination}")
                    val newBalanceDestination = balanceDestination + amount
                    Log.d("Update", "newbalance ${newBalanceDestination}")
                    // Actualiza los saldos en ambas cuentas
                    updateAccountBalance(accountId, newBalanceDestination)

                }

            }


        } catch (e: Exception) {
            Log.d("Cuenta", "Error: ${e.message}")
        }

    }

    private fun updateAccountBalance(accountId: Int, newBalance: Double) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                updateBalance.invoke(accountId, newBalance)
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
    fun onDisableCurrencySelector(){
        _enableCurrencySelector.postValue(false)

    }

    private fun resetFields() {
        _name.postValue("") // Vaciar el nombre de la cuenta
        _amount.postValue("") // Vaciar el balance de la cuenta
        _accountSelected.postValue(null)
        _destinationAccount.postValue(null)
        _isConfirmTransfer.postValue(false)
    }

    fun onCurrencySelectedChange(currencySelected: String) {
        _currencyCode.value = currencySelected

    }


    fun onAccountSelected(accountSelected: Account) {
        _accountSelected.value = accountSelected
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



    fun onTextFieldsChanged(newName:String,newBalance: String) {
        if (Utils.isValidDecimal(newBalance)) {
            _amount.value = newBalance
        }
        _name.value = newName
        _isEnableButtons.value=enableButton(newName,newBalance)
    }





    fun isValidExpense(amount: Double): Boolean = (_accountSelected.value?.balance ?: 0.0) >= amount

    private fun enableButton(description: String, amount: String): Boolean =
        description.isNotBlank() && amount.isNotEmpty() && description.isNotBlank() && amount.isNotBlank()


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


}



