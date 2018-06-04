package com.bignerdranch.android.gaba;

import android.content.Intent;
import android.os.Bundle;
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
        Recipe recipe = new Recipe(recipeSelected.getRecipeId(),
                recipeSelected.getRecipeName(),
                recipeSelected.getIngredientsList(),
                recipeSelected.getStepsList(),
                recipeSelected.getNumberServings(),
                recipeSelected.getRecipeImage()
                );

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);



    }

}
