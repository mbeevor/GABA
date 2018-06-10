package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Recipe;

import java.util.List;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.long_app_name);

    }

    @Override
    public void onRecipeSelected(List<Recipe> recipes, int position) {

        Recipe recipeSelected = recipes.get(position);

        Bundle recipe = new Bundle();
        recipe.putString("recipeId", recipeSelected.getRecipeId());
        recipe.putString("recipeName", recipeSelected.getRecipeName());
        recipe.putParcelableArrayList("ingredientsList", recipeSelected.getIngredientsList());
        recipe.putParcelableArrayList("stepsList", recipeSelected.getStepsList());
        recipe.putString("numberServings", recipeSelected.getNumberServings());
        recipe.putString("recipeImage", recipeSelected.getRecipeImage());

        final Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtras(recipe);
        this.startActivity(intent);


    }

}
