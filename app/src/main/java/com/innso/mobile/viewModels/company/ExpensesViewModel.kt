package com.innso.mobile.viewModels.company

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innso.mobile.api.controllers.FinanceController
import com.innso.mobile.api.models.finance.ExpenseModel
import com.innso.mobile.ui.activities.expenses.AddExpenseActivity
import com.innso.mobile.viewModels.AndroidViewModel
import com.innso.mobile.viewModels.models.StartActivityModel
import javax.inject.Inject

class ExpensesViewModel : AndroidViewModel() {

    private val expenses = MutableLiveData<List<ExpenseModel>>()

    @Inject
    lateinit var financeController: FinanceController

    init {
        getComponent().inject(this)
        disposables.add(financeController.expenses
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .subscribe({ expenses.postValue(it) }, { this.showServiceError(it) }))
    }

    fun onClick(view: View) {
        startActivity.postValue(StartActivityModel(AddExpenseActivity::class.java))
    }

    fun update(yearSelected: String) {
        financeController.getExpenses(yearSelected)
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .subscribe({ expenses.postValue(it) }, { this.showServiceError(it) })
    }

    fun onBillsChange(): LiveData<List<ExpenseModel>> = expenses
}
