package com.innso.mobile.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.innso.mobile.R;
import com.innso.mobile.api.models.users.UserRequest;
import com.innso.mobile.databinding.ActivityUsersBinding;
import com.innso.mobile.ui.adapters.GenericAdapter;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.ui.itemViews.ItemDetailUser;
import com.innso.mobile.ui.models.list.GenericAdapterFactory;
import com.innso.mobile.ui.models.list.GenericItemAbstract;
import com.innso.mobile.ui.viewModels.UsersListViewModel;

import java.util.List;

public class UsersActivity extends BaseActivity {

    private ActivityUsersBinding binding;

    private GenericAdapter userListAdapter;

    private UsersListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        viewModel = new UsersListViewModel();
        initViews();
        binding.setViewModel(viewModel);
    }


    private void initViews() {
        userListAdapter = new GenericAdapter(new GenericAdapterFactory() {
            @Override
            public GenericItemView onCreateViewHolder(ViewGroup ViewGroup, int viewType) {
                return new ItemDetailUser(getBaseContext());
            }
        });
        RecyclerView list = binding.recyclerListUsers;
        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        list.setAdapter(userListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposable.add(viewModel.observableSnackBar().subscribe(event -> showMessage(event.getTypeSnackBar(), binding.getRoot(), event.getMessage())));
        disposable.add(viewModel.allUsers().subscribe(this::addUser));
        disposable.add(viewModel.observableStartActivity().subscribe(activity -> startActivity(new Intent(this, activity))));
    }

    private void addUser(List<UserRequest> users) {
        binding.progressbar.setVisibility(View.GONE);
        userListAdapter.clearAll();
        for (UserRequest user : users) {
            userListAdapter.addItem(new GenericItemAbstract(user, GenericAdapterFactory.TYPE_ITEM));
        }
    }
}
