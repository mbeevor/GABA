package com.bignerdranch.android.gaba;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.IdlingResource.SimpleIdlingResource;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.SharedPreferences;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeClickListener {


    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;

    @Nullable
    private IdlingResource idlingResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.long_app_name);
        getIdlingResource();

    }

    @Override
    public void onRecipeSelected(List<Recipe> recipes, int position) {

        Recipe recipeSelected = recipes.get(position);
        recipeId = recipeSelected.getRecipeId();
        recipeName = recipeSelected.getRecipeName();
        ingredientsList = recipeSelected.getIngredientsList();
        stepsList = recipeSelected.getStepsList();
        numberServings = recipeSelected.getNumberServings();
        recipeImage = recipeSelected.getRecipeImage();

        // add recipe to Shared Preferences
        Recipe recipe = new Recipe(recipeId, recipeName, ingredientsList,
                stepsList, numberServings, recipeImage);
        SharedPreferences.setSharedPreferences(this, recipe, getApplication());

    }


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
