package com.innso.mobile.di.components;

import com.innso.mobile.di.scope.ApplicationScope;
import com.innso.mobile.ui.activities.CreateUserActivity;
import com.innso.mobile.ui.activities.SplashActivity;

import dagger.Component;

@ApplicationScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {

    void inject(SplashActivity splashActivity);

    void inject(CreateUserActivity createUserActivity);
}
