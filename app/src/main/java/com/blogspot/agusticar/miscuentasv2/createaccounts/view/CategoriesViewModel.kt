package com.blogspot.agusticar.miscuentasv2.createaccounts.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.Category
import com.blogspot.agusticar.miscuentasv2.main.data.database.entities.CategoryType
import com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase.GetAllCategoriesByType
import com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase.InsertCategoryUseCase
import com.blogspot.agusticar.miscuentasv2.main.domain.database.categoryusecase.UpdateCheckedCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val insertCategory:InsertCategoryUseCase,
    private val getAllCategoriesByType:GetAllCategoriesByType,
    private val upDateCategoryChecked:UpdateCheckedCategoryUseCase
): ViewModel() {

    //LiveData para la lista de Categorias
    private val _listOfCategories = MutableLiveData<List<Category>>()
    val listOfCategories: LiveData<List<Category>> = _listOfCategories

    //LiveData para categoria seleccionada

    private val _categorySelected = MutableLiveData<Category>()
    val categorySelected: LiveData<Category> = _categorySelected

    fun populateCategories(){
        viewModelScope.launch(Dispatchers.IO)
         {
            incomeCategories.forEach { category->
                insertCategory.invoke(category)
            }
            expenseCategories.forEach { category->
                insertCategory.invoke(category)
            }
         }
    }
    fun getAllCategoriesByType(type:CategoryType){
        viewModelScope.launch(Dispatchers.IO){
            _listOfCategories.postValue(getAllCategoriesByType.invoke(type))
        }

    }

    fun onCategorySelected(categorySelected: Category) {
        _categorySelected.value = categorySelected
    }


    fun updateCategoryCheckedState(categoryId: Int, isChecked: Boolean) {
        viewModelScope.launch {
            upDateCategoryChecked.invoke(categoryId, isChecked)
            getAllCategoriesByType(CategoryType.EXPENSE)
        }
    }

    private val incomeCategories = listOf(
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_salary,
            nameResource = R.string.salary
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_dividens,
            nameResource = R.string.dividens
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_rent,
            nameResource = R.string.rental
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_freelances,
            nameResource = R.string.freelance
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_sales,
            nameResource = R.string.sales
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_donation,
            nameResource = R.string.subsidies
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_lotery,
            nameResource = R.string.lotery
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_otherincomes,
            nameResource = R.string.otherincomes
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_premium,
            nameResource = R.string.awards
        ),
        Category(
            type = CategoryType.INCOME,
            iconResource = R.drawable.ic_category_winasset,
            nameResource = R.string.benefit_assets
        )
    )

    private val expenseCategories = listOf(
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_grocery,
            nameResource = R.string.food
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_mortgage,
            nameResource = R.string.morgage
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_electricity,
            nameResource = R.string.electricitybill
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_water,
            nameResource = R.string.waterbill
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_gasbill,
            nameResource = R.string.gasbill
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_publictansport,
            nameResource = R.string.publictransport
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_fuelcar,
            nameResource = R.string.fuel_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_otherinsurance,
            nameResource = R.string.insurances
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_healthbill,
            nameResource = R.string.health
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_leiure,
            nameResource = R.string.entertainment
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_subscriptions,
            nameResource = R.string.subscriptions
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_vacation,
            nameResource = R.string.vacations_travel_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_clothing,
            nameResource = R.string.clothing
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_education,
            nameResource = R.string.courses_books_materials_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_house,
            nameResource = R.string.house
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_repaircar,
            nameResource = R.string.car
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_sport,
            nameResource = R.string.gym
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_pet,
            nameResource = R.string.pets
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_personalcare,
            nameResource = R.string.personal_care_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_gif,
            nameResource = R.string.gifts_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_donation,
            nameResource = R.string.donations_title
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_lostasset,
            nameResource = R.string.lost_assets
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_books,
            nameResource = R.string.books
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_music,
            nameResource = R.string.music
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_hobies,
            nameResource = R.string.hobbies
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_tax,
            nameResource = R.string.taxes
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_loan,
            nameResource = R.string.loans
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_electronic,
            nameResource = R.string.electronics
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_coffe,
            nameResource = R.string.coffee
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_tabac,
            nameResource = R.string.tobacco
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_sportsuplement,
            nameResource = R.string.supplements
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_bike,
            nameResource = R.string.motorcycle
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_garden,
            nameResource = R.string.garden
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_teraphy,
            nameResource = R.string.therapies
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_alcohol,
            nameResource = R.string.alcohol
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_game,
            nameResource = R.string.gambling
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_restaurant,
            nameResource = R.string.restaurants
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_kids,
            nameResource = R.string.children
        ),
        Category(
            type = CategoryType.EXPENSE,
            iconResource = R.drawable.ic_category_otherincomes,
            nameResource = R.string.other_expenses
        )
    )

}