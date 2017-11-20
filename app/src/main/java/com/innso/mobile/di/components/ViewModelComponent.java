package com.innso.mobile.di.components;

import com.innso.mobile.di.scope.ApplicationScope;
import com.innso.mobile.ui.viewModels.LoginViewModel;
import com.innso.mobile.ui.viewModels.NewUserViewModel;
import com.innso.mobile.ui.viewModels.SplashViewModel;

import dagger.Component;


@ApplicationScope
@Component(dependencies = AppComponent.class)
public interface ViewModelComponent extends AppComponent {

    void inject(SplashViewModel splashViewModel);

    void inject(NewUserViewModel newUserViewModel);

    void inject(LoginViewModel loginViewModel);
}