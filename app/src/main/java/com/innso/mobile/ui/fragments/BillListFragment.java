package com.innso.mobile.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innso.mobile.R;
import com.innso.mobile.databinding.FragmentBillListBinding;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.viewModels.BillListViewModel;

public class BillListFragment extends BaseFragment {

    private FragmentBillListBinding binding;

    private BillListViewModel viewModel;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe() {
        disposable.add(viewModel.observableStartActivity().subscribe(this::startActivityFormEvent));
    }
}
