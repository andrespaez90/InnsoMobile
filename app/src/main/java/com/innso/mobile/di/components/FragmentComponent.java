package com.innso.mobile.di.components;

import com.innso.mobile.di.scope.ApplicationScope;
import com.innso.mobile.ui.activities.CreateUserActivity;
import com.innso.mobile.ui.activities.SplashActivity;
import com.innso.mobile.ui.fragments.ProfileFragment;

import dagger.Component;

@ApplicationScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent extends AppComponent {

    void inject(ProfileFragment profileFragment);

}
