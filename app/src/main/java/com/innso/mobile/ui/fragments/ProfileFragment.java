package com.innso.mobile.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.innso.mobile.R;
import com.innso.mobile.databinding.FragmentCompanyBinding;
import com.innso.mobile.databinding.FragmentProfileBinding;
import com.innso.mobile.ui.BaseFragment;
import com.innso.mobile.ui.activities.SplashActivity;
import com.innso.mobile.ui.itemViews.ItemDetailMonth;
import com.innso.mobile.ui.viewModels.ProfileViewModel;
import com.innso.mobile.utils.StringUtils;

import javax.inject.Inject;

public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;

    private ProfileViewModel viewModel;

    private FirebaseUser firebaseUser;

    @Inject
    FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        getComponent().inject(this);
        viewModel = new ProfileViewModel();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setViewModel(viewModel);
        firebaseUser = firebaseAuth.getCurrentUser();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribe();
    }

    public void subscribe(){
        disposable.add(viewModel.observableStartActivity().subscribe(this::startActivityFormEvent));
        disposable.add(viewModel.closeSession().subscribe(this::closeSession));
    }


    private void startActivityFormEvent(Class<?> clazz){
        startActivity(new Intent(getContext(), clazz));
    }

    private void closeSession(Boolean closeSession){
        Intent intent = new Intent(getContext(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void initView() {
        binding.textViewCompanyName.setText(StringUtils.setSpannablesFromRegex(firebaseUser.getDisplayName()+"\n"+firebaseUser.getEmail(),
                "^(.*?)\\n", new StyleSpan(Typeface.BOLD), new RelativeSizeSpan(1.2f)));
    }

}
