package com.innso.mobile.ui.viewModels

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innso.mobile.api.controllers.FinanceController
import com.innso.mobile.api.models.finance.FinanceYearSummary
import com.innso.mobile.api.models.finance.SummaryMonth
import com.innso.mobile.ui.activities.bills.BillsActivity
import com.innso.mobile.ui.activities.expenses.ExpensesActivity
import com.innso.mobile.utils.DateUtils
import com.innso.mobile.viewModels.AndroidViewModel
import com.innso.mobile.viewModels.models.StartActivityModel
import java.text.ParseException
import java.util.*
import javax.inject.Inject

class FinanceViewModel : AndroidViewModel() {

    private val revenue = MutableLiveData<List<Double>>()

    private val expenses = MutableLiveData<List<Double>>()

    private val totalMonth = MutableLiveData<List<Double>>()

    private val totalRevenue = MutableLiveData<Double>()

    private val totalExpenditure = MutableLiveData<Double>()

    private val totalBanks = MutableLiveData<Double>()

    private val totalCash = MutableLiveData<Double>()

    var currentYear = ObservableField("")

    @Inject
    lateinit var financeController: FinanceController

    init {
        getComponent().inject(this)
        currentYear.set(Calendar.getInstance().get(Calendar.YEAR).toString())
    }

    fun getSummary() {
        showLoading()
        financeController.getSummary(currentYear.get())
                .subscribe({ this.updateRevenue(it) }, { this.showServiceError(it) })
    }

    fun onNextYear(view: View) {
        currentYear.set((Integer.parseInt(currentYear.get()!!) + 1).toString())
        getSummary()
    }

    fun onLastYear(view: View) {
        currentYear.set((Integer.parseInt(currentYear.get()!!) - 1).toString())
        getSummary()
    }

    @Throws(ParseException::class)
    private fun updateRevenue(yearSummary: FinanceYearSummary) {
        val summaryMonth = sortFinanceSummary(yearSummary.monthSummary)
        val revenuePerMonth = ArrayList<Double>(12)
        val expensesPerMonth = ArrayList<Double>(12)
        totalMonth.postValue(getRevenueExpensesValues(summaryMonth, revenuePerMonth, expensesPerMonth))
        revenue.postValue(revenuePerMonth)
        expenses.postValue(expensesPerMonth)
        totalRevenue.postValue(yearSummary.totalRevenue)
        totalExpenditure.postValue(yearSummary.totalExpediture)
        totalBanks.postValue(yearSummary.totalBanks)
        totalCash.postValue(yearSummary.totalCash)
        hideLoading()
    }

    private fun getRevenueExpensesValues(summaryMonth: Array<SummaryMonth?>, revenueVales: ArrayList<Double>, expenseValues: ArrayList<Double>): ArrayList<Double> {
        val totalMonth = ArrayList<Double>(12)
        for (month in summaryMonth) {
            month?.let {
                revenueVales.add(month.revenue)
                expenseValues.add(month.expenses)
                totalMonth.add(month.revenue - month.expenses)
            } ?: run {
                revenueVales.add(0.0)
                expenseValues.add(0.0)
                totalMonth.add(0.0)
            }
        }
        return totalMonth
    }

    private fun sortFinanceSummary(response: Map<String, SummaryMonth>): Array<SummaryMonth?> {
        val summaryMonths = arrayOfNulls<SummaryMonth>(12)
        for ((key, value) in response) {
            summaryMonths[DateUtils.getMonth(key)] = value
        }
        return summaryMonths
    }

    fun onExpensesClick(view: View) =
            startActivity.postValue(StartActivityModel(ExpensesActivity::class.java))

    fun onRevenueClick(view: View) =
            startActivity.postValue(StartActivityModel(BillsActivity::class.java))

    fun onRevenueUpdated(): LiveData<List<Double>> = revenue

    fun onExpensesUpdated(): LiveData<List<Double>> = expenses

    fun onTotalMonthUpdated(): LiveData<List<Double>> = totalMonth

    fun onTotalRevenueUpdated(): LiveData<Double> = totalRevenue

    fun onTotalExpenditureUpdated(): LiveData<Double> = totalExpenditure

    fun onTotalBanksUpdated(): LiveData<Double> = totalBanks

    fun onTotalCashUpdated(): LiveData<Double> = totalCash
}
