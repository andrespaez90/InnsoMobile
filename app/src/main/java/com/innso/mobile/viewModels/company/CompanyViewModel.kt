package com.innso.mobile.viewModels.company


import android.view.View
import com.innso.mobile.ui.activities.NewCustomerActivity
import com.innso.mobile.ui.activities.UsersActivity
import com.innso.mobile.ui.activities.bills.BillsActivity
import com.innso.mobile.ui.activities.expenses.ExpensesActivity
import com.innso.mobile.viewModels.AndroidViewModel
import com.innso.mobile.viewModels.models.StartActivityModel

class CompanyViewModel : AndroidViewModel() {

    fun addUser(view: View) =
            startActivity.postValue(StartActivityModel(UsersActivity::class.java))

    fun addCustomer(view: View) =
            startActivity.setValue(StartActivityModel(NewCustomerActivity::class.java))

    fun showBills(view: View) =
            startActivity.setValue(StartActivityModel(BillsActivity::class.java))

    fun addExpense(view: View) =
            startActivity.setValue(StartActivityModel(ExpensesActivity::class.java))
}
