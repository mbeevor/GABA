package com.bignerdranch.android.gaba.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Matthew on 18/06/2018.
 */

public class SimpleIdlingResource implements IdlingResource {

    @Nullable private  volatile ResourceCallback callBack;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        callBack = callback;
    }
}
