package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.bignerdranch.android.gaba.Adapters.StepListAdapter;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.SharedPreferences;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_CARD;
import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_LIST;
import static com.bignerdranch.android.gaba.Model.Keys.NUMBER_SERVINGS;
import static com.bignerdranch.android.gaba.Model.Keys.POSITION;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_ID;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_IMAGE;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 26/05/2018.
 */

public class RecipeActivity extends AppCompatActivity implements StepListAdapter.OnStepClickHandler {

    private Recipe recipe;
    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    @BindView(R.id.ingredients_card_view)
    CardView ingredientsCardView;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;
    private String ingredientsCard = INGREDIENTS_CARD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        // get recipe from Shared Preferences
        recipe = SharedPreferences.getSharedPreferences(this);

        recipeId = recipe.getRecipeId();
        recipeName = recipe.getRecipeName();
        ingredientsList = recipe.getIngredientsList();
        stepsList = recipe.getStepsList();
        numberServings = recipe.getNumberServings();
        recipeImage = recipe.getRecipeImage();

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
            // we're in single-pane mode - set onClickListener for ingredients card
            ingredientsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    recipeBundleForFragment.putString(INGREDIENTS_CARD, ingredientsCard);
                    intent.putExtras(recipeBundleForFragment);
                    startActivity(intent);

                }
            });


        }
    }

    @Override
    public void onItemClick(List<Steps> steps, int position) {

        Fragment stepDetailFragment = new StepDetailFragment();
        Bundle stepBundleForFragment = new Bundle();
        stepBundleForFragment.putString(RECIPE_NAME, recipeName);
        stepBundleForFragment.putInt(POSITION, position);
        stepBundleForFragment.putParcelableArrayList(STEPS_LIST, stepsList);

        stepDetailFragment.setArguments(stepBundleForFragment);

        // determine if displaying one or two-panes
        if (findViewById(R.id.instruction_linear_layout) != null) {

            // two pane - so replace right hand fragment with recipe selected
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_instruction_container, stepDetailFragment)
                    .commit();

        } else {

            // one pane - so launch new activity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtras(stepBundleForFragment);
            startActivity(intent);

        }
    }
}
