package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Keys;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.Widget.IngredientsWidgetService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeClickListener {


    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;
    private String numberServings;
    private String recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.long_app_name);


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

        Bundle recipe = new Bundle();
        recipe.putString(Keys.RECIPE_ID, recipeId);
        recipe.putString(Keys.RECIPE_NAME, recipeName);
        recipe.putParcelableArrayList(Keys.INGREDIENTS_LIST, ingredientsList);
        recipe.putParcelableArrayList(Keys.STEPS_LIST, stepsList);
        recipe.putString(Keys.NUMBER_SERVINGS, numberServings);
        recipe.putString(Keys.RECIPE_IMAGE, recipeImage);

        // start service to update widget
        IngredientsWidgetService.updateWidgetIntent(this, recipeName, ingredientsList, stepsList);

        // send recipe to RecipeActivity via intent
        final Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtras(recipe);
        this.startActivity(intent);

    }
}
