package com.innso.mobile.ui.activities.expenses

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.innso.mobile.R
import com.innso.mobile.api.models.finance.ExpenseModel
import com.innso.mobile.databinding.ActivityExpensesBinding
import com.innso.mobile.ui.activities.BaseActivity
import com.innso.mobile.ui.activities.bills.BillDetailActivity
import com.innso.mobile.ui.adapters.GenericAdapter
import com.innso.mobile.ui.interfaces.GenericItemView
import com.innso.mobile.ui.itemViews.DefaultCategory
import com.innso.mobile.ui.itemViews.ItemDetailBill
import com.innso.mobile.ui.itemViews.ItemDetailExpense
import com.innso.mobile.ui.models.Expense
import com.innso.mobile.ui.models.list.GenericAdapterFactory
import com.innso.mobile.ui.models.list.GenericCategoryItemAbstract
import com.innso.mobile.ui.viewModels.ExpensesViewModel

class ExpensesActivity : BaseActivity() {

    private lateinit var binding: ActivityExpensesBinding

    private lateinit var viewModel: ExpensesViewModel

    private var listAdapter: GenericAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expenses)
        initViewModel()
        initViews()
        initListeners()
    }

    private fun initViewModel() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        subscribeViewModel(viewModel, binding.root)
        viewModel = ViewModelProviders.of(this).get(ExpensesViewModel::class.java)
        viewModel.onBillsChange().observe(this, Observer { this.updateList(it) })
    }

    private fun initListeners() {
        binding!!.swipeRefresh.setOnRefreshListener { viewModel!!.update(binding!!.spinnerYears.selectedItem as String) }
    }

    private fun initViews() {
        enableActionBack()
        initRecyclerView()
        initSpinner()
    }

    private fun initSpinner() {
        val letra = arrayOf("2018", "2017", "2016")
        binding!!.spinnerYears.adapter = ArrayAdapter(baseContext, R.layout.simple_spinner_item, letra)
        binding!!.spinnerYears.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel!!.update(binding!!.spinnerYears.selectedItem as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun initRecyclerView() {
        listAdapter = GenericAdapter(object : GenericAdapterFactory() {
            override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GenericItemView<*> {
                when (viewType) {
                    GenericAdapterFactory.TYPE_CATEGORY -> return DefaultCategory(viewGroup.context)
                    else -> {
                        val item = ItemDetailExpense(viewGroup.context)
                        item.setOnClickListener { view -> startDetailBillActivity(view) }
                        return item
                    }
                }
            }
        }, true)

        val list = binding!!.recyclerBills
        list.layoutManager = LinearLayoutManager(baseContext)
        list.adapter = listAdapter
    }

    fun startDetailBillActivity(view: View) {
        if (view is ItemDetailBill) {
            val intent = Intent(this, BillDetailActivity::class.java)
            intent.putExtra("bill", view.data)
            this.startActivity(intent)
        }
    }

    private fun updateList(expenses: List<ExpenseModel>) {
        binding!!.swipeRefresh.isRefreshing = false
        listAdapter!!.clearAll()
        var expense: Expense
        var i = 0
        val size = expenses.size
        while (i < size) {
            expense = Expense(expenses[i])
            listAdapter!!.addItem(GenericCategoryItemAbstract(expense, expense.month))
            i++
        }
    }

}
