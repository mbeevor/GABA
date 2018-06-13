package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_LIST;
import static com.bignerdranch.android.gaba.Model.Keys.NUMBER_SERVINGS;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_ID;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_IMAGE;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 26/05/2018.
 */

public class RecipeActivity extends AppCompatActivity {

    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    @BindView(R.id.ingredients_card_view)
    CardView ingredientsCardView;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        // get intent from main activity
        Intent intent = getIntent();
        recipeId = intent.getStringExtra(RECIPE_ID);
        recipeName = intent.getStringExtra(RECIPE_NAME);
        ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
        stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
        numberServings = intent.getStringExtra(NUMBER_SERVINGS);
        recipeImage = intent.getStringExtra(RECIPE_IMAGE);

        // update app name to name of recipe selected
        setTitle(recipeName);

        // create a FragmentManager to handle all fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // create recipe bundle
        final Bundle recipeBundleForFragment = new Bundle();
        recipeBundleForFragment.putString(RECIPE_ID, recipeId);
        recipeBundleForFragment.putString(RECIPE_NAME, recipeName);
        recipeBundleForFragment.putParcelableArrayList(INGREDIENTS_LIST, ingredientsList);
        recipeBundleForFragment.putParcelableArrayList(STEPS_LIST, stepsList);
        recipeBundleForFragment.putString(NUMBER_SERVINGS, numberServings);
        recipeBundleForFragment.putString(RECIPE_IMAGE, recipeImage);

        // create new fragment of recipe steps if not previously created
        if (savedInstanceState == null) {

            // pass recipe steps into recyclerview
            Fragment stepListFragment = new StepListFragment();
            stepListFragment.setArguments(recipeBundleForFragment);
            fragmentManager.beginTransaction()
                    .add(R.id.step_list_container, stepListFragment)
                    .commit();
        }

        // determine if creating a one or two-pane display
        if (findViewById(R.id.instruction_linear_layout) != null) {

            // create new fragment of ingredients to display by default
            if (savedInstanceState == null) {

                Fragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(recipeBundleForFragment);

                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_instruction_container, ingredientsFragment)
                        .commit();
            }

            // else, create onclicklistener to display ingredients when menu item clicked
            ingredientsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Fragment newIngredientsFragment = new IngredientsFragment();
                    newIngredientsFragment.setArguments(recipeBundleForFragment);

                    fragmentManager.beginTransaction()
                            .replace(R.id.recipe_instruction_container, newIngredientsFragment)
                            .commit();
                }
            });

        } else {
            // we're in single-pane mode
            ingredientsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtras(recipeBundleForFragment);
                    startActivity(intent);

                }
            });

        }
    }

}
