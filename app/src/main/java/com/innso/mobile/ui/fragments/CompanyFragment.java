package com.innso.mobile.ui.fragments;

import androidx.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innso.mobile.R;
import com.innso.mobile.databinding.FragmentCompanyBinding;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.viewModels.CompanyViewModel;
import com.innso.mobile.utils.StringUtils;

public class CompanyFragment extends BaseFragment {

    private CompanyViewModel viewModel;

    private FragmentCompanyBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company, container, false);
        viewModel = new CompanyViewModel();
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    private void subscribe(){
        disposable.add(viewModel.startActivityEvent().subscribe(this::startActivity));
    }

    private void initView() {
        binding.textViewCompanyName.setText(StringUtils.setSpannablesFromRegex(getString(R.string.company_name),
                "^(.*?)\\n", new StyleSpan(Typeface.BOLD), new RelativeSizeSpan(1.2f)));
        binding.textViewCompanyNit.setText(StringUtils.setSpannablesFromRegex(getString(R.string.company_nit), "(.*):", new StyleSpan(Typeface.BOLD)));
    }

}
