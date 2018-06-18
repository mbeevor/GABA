package com.bignerdranch.android.gaba;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bignerdranch.android.gaba.Model.Keys;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by Matthew on 18/06/2018.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest {

    @Rule public IntentsTestRule<RecipeActivity> recipeActivityBasicTestRule
            = new IntentsTestRule<>(RecipeActivity.class);

    @Before
    public void stubAllExternalIntents() {
        // block all external intents
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void pickRecipeStep_openDetailActivityOnMobile() {

        intending(hasComponent(hasShortClassName(".DetailActivity")))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, DetailActivity.createResultData(Keys.RECIPE_NAME)));

    }

}
