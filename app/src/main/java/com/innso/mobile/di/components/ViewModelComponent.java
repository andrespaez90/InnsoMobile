package com.innso.mobile.di.components;

import com.innso.mobile.di.scope.ApplicationScope;
import com.innso.mobile.ui.viewModels.AddBillViewModel;
import com.innso.mobile.ui.viewModels.AddExpenseViewModel;
import com.innso.mobile.ui.viewModels.BillListViewModel;
import com.innso.mobile.viewModels.HomeViewModel;
import com.innso.mobile.viewModels.company.ExpensesViewModel;
import com.innso.mobile.ui.viewModels.FinanceViewModel;
import com.innso.mobile.ui.viewModels.NewCustomerViewModel;
import com.innso.mobile.ui.viewModels.NewUserViewModel;
import com.innso.mobile.viewModels.company.ProfileViewModel;
import com.innso.mobile.ui.viewModels.UsersListViewModel;
import com.innso.mobile.viewModels.onBoarding.LoginViewModel;
import com.innso.mobile.viewModels.onBoarding.SplashViewModel;

import org.jetbrains.annotations.NotNull;

import dagger.Component;


@ApplicationScope
@Component(dependencies = AppComponent.class)
public interface ViewModelComponent extends AppComponent {

    void inject(SplashViewModel splashViewModel);

    void inject(NewUserViewModel newUserViewModel);

    void inject(LoginViewModel loginViewModel);

    void inject(ProfileViewModel profileViewModel);

    void inject(UsersListViewModel usersListViewModel);

    void inject(NewCustomerViewModel newCustomerViewModel);

    void inject(AddBillViewModel addBillViewModel);

    void inject(BillListViewModel billListViewModel);

    void inject(FinanceViewModel financeViewModel);

    void inject(AddExpenseViewModel addExpenseViewModel);

    void inject(ExpensesViewModel expensesViewModel);

    void inject(@NotNull HomeViewModel homeViewModel);
}
