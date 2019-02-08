package com.innso.mobile.ui.activities.expenses;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.innso.mobile.R;
import com.innso.mobile.api.models.finance.ExpenseModel;
import com.innso.mobile.databinding.ActivityExpensesBinding;
import com.innso.mobile.ui.activities.BaseActivity;
import com.innso.mobile.ui.activities.bills.BillDetailActivity;
import com.innso.mobile.ui.adapters.GenericAdapter;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.itemViews.DefaultCategory;
import com.innso.mobile.ui.itemViews.ItemDetailBill;
import com.innso.mobile.ui.itemViews.ItemDetailExpense;
import com.innso.mobile.ui.models.Expense;
import com.innso.mobile.ui.models.list.GenericAdapterFactory;
import com.innso.mobile.ui.models.list.GenericCategoryItemAbstract;
import com.innso.mobile.ui.viewModels.ExpensesViewModel;

import java.util.List;

public class ExpensesActivity extends BaseActivity {

    private ActivityExpensesBinding binding;

    private ExpensesViewModel viewModel;

    private GenericAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expenses);
        viewModel = new ExpensesViewModel();
        binding.setViewModel(viewModel);
        initViews();
        initListeners();
    }

    private void initListeners() {
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.update((String) binding.spinnerYears.getSelectedItem()));
    }

    private void initViews() {
        enableActionBack();
        initRecyclerView();
        initSpinner();
    }

    private void initSpinner() {
        String[] letra = {"2018", "2017", "2016"};
        binding.spinnerYears.setAdapter(new ArrayAdapter<>(getBaseContext(), R.layout.simple_spinner_item, letra));
        binding.spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.update((String) binding.spinnerYears.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView() {
        listAdapter = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                switch (viewType) {
                    case TYPE_CATEGORY:
                        return new DefaultCategory(viewGroup.getContext());
                    default:
                        ItemDetailExpense item = new ItemDetailExpense(viewGroup.getContext());
                        item.setOnClickListener(view -> startDetailBillActivity(view));
                        return item;
                }
            }
        }, true);

        RecyclerView list = binding.recyclerBills;
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        list.setAdapter(listAdapter);
    }

    public void startDetailBillActivity(View view) {
        if (view instanceof ItemDetailBill) {
            Intent intent = new Intent(this, BillDetailActivity.class);
            intent.putExtra("bill", ((ItemDetailBill) view).getData());
            this.startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showMessage(event.getTypeSnackBar(), binding.getRoot(), event.getMessage(), Snackbar.LENGTH_LONG)));
        disposable.add(viewModel.observableStartActivity().subscribe(this::startActivity));
        disposable.add(viewModel.onBillsChange().subscribe(this::updateList));
    }

    private void updateList(List<ExpenseModel> expenses) {
        binding.swipeRefresh.setRefreshing(false);
        listAdapter.clearAll();
        Expense expense;
        for (int i = 0, size = expenses.size(); i < size; i++) {
            expense = new Expense(expenses.get(i));
            listAdapter.addItem(new GenericCategoryItemAbstract(expense, expense.getMonth()));
        }
    }

}
