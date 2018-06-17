package com.bignerdranch.android.gaba.Model;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.bignerdranch.android.gaba.Widget.IngredientsWidgetProvider;
import com.google.gson.Gson;

/**
 * Created by Matthew on 17/06/2018.
 */

public class RecipesPreferences {

    private RecipesPreferences() {
        throw new AssertionError();
    }

    static final String RECIPE = "recipe";

    public static Recipe getRecipePreferences(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
    }

    public static void setRecipePreferences(Context context, Recipe recipe, Application application) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE, json)
                .apply();

        Intent intent = new Intent(context, IngredientsWidgetProvider.class);
        intent.setAction(Keys.ACTION_UPDATE_WIDGET);
        int[] ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(new ComponentName(application, IngredientsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        application.sendBroadcast(intent);

           }
}
