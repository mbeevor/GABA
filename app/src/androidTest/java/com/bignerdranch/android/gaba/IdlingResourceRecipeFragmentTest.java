package com.bignerdranch.android.gaba;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Matthew on 18/06/2018.
 */

@RunWith(AndroidJUnit4.class)
public class IdlingResourceRecipeFragmentTest {


    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = mainActivityTestRule.getActivity().getIdlingResource();
        // this is now deprecated
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void idlingResourceTest() {
        onView(withId(R.id.list_recyclerview)).check(matches(isDisplayed()));
        onView(withId(R.id.list_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.registerIdlingResources(idlingResource);
        }
    }

}
