package com.innso.mobile.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.innso.mobile.R;
import com.innso.mobile.api.models.finance.BillModel;
import com.innso.mobile.databinding.FragmentBillListBinding;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.activities.BillDetailActivity;
import com.innso.mobile.ui.adapters.GenericAdapter;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.itemViews.DefaultCategory;
import com.innso.mobile.ui.itemViews.ItemDetailBill;
import com.innso.mobile.ui.models.Bill;
import com.innso.mobile.ui.models.list.GenericAdapterFactory;
import com.innso.mobile.ui.models.list.GenericCategoryItemAbstract;
import com.innso.mobile.ui.viewModels.BillListViewModel;

import java.util.List;

public class BillListFragment extends BaseFragment {

    private FragmentBillListBinding binding;

    private BillListViewModel viewModel;

    private GenericAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_list, container, false);
        viewModel = new BillListViewModel();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }


    private void initViews() {
        listAdapter = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                switch (viewType) {
                    case TYPE_CATEGORY:
                        return new DefaultCategory(viewGroup.getContext());
                    default:
                        ItemDetailBill item = new ItemDetailBill(viewGroup.getContext());
                        item.setOnClickListener(view -> startDetailBillActivity(view));
                        return item;
                }
            }
        }, true);
        RecyclerView list = binding.recyclerBills;
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(listAdapter);

        String[] letra = {"2018", "2017", "2016"};
        binding.spinnerYears.setAdapter(new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, letra));
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

    public void startDetailBillActivity(View view) {
        if (view instanceof ItemDetailBill) {
            Intent intent = new Intent(getActivity(), BillDetailActivity.class);
            intent.putExtra("bill", ((ItemDetailBill) view).getData());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showMessage(event.getTypeSnackBar(), binding.getRoot(), event.getMessage(), Snackbar.LENGTH_LONG)));
        disposable.add(viewModel.observableStartActivity().subscribe(this::startActivityFormEvent));
        disposable.add(viewModel.onBillsChange().subscribe(this::updateList));
    }

    private void updateList(List<BillModel> bills) {
        listAdapter.clearAll();
        Bill bill;
        for (int i = 0, size = bills.size(); i < size; i++) {
            bill = new Bill(bills.get(i));
            listAdapter.addItem(new GenericCategoryItemAbstract(bill, bill.getCategory()));
        }
    }

}
