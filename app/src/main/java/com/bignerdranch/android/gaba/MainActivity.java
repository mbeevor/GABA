package com.bignerdranch.android.gaba;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Keys;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Widget.IngredientsWidgetProvider;

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
        recipe.putString(Keys.RECIPE_ID, recipeSelected.getRecipeId());
        recipe.putString(Keys.RECIPE_NAME, recipeSelected.getRecipeName());
        recipe.putParcelableArrayList(Keys.INGREDIENTS_LIST, recipeSelected.getIngredientsList());
        recipe.putParcelableArrayList(Keys.STEPS_LIST, recipeSelected.getStepsList());
        recipe.putString(Keys.NUMBER_SERVINGS, recipeSelected.getNumberServings());
        recipe.putString(Keys.RECIPE_IMAGE, recipeSelected.getRecipeImage());

        final Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtras(recipe);
        this.startActivity(intent);

        // send broadcast to update widget to ingredients for recipe selected
        final Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        widgetIntent.setComponent(new ComponentName(this, IngredientsWidgetProvider.class));
        widgetIntent.putExtras(recipe);
        sendBroadcast(widgetIntent);


    }

}
