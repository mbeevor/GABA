package com.bignerdranch.android.gaba.Model;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.bignerdranch.android.gaba.RecipeActivity;
import com.bignerdranch.android.gaba.Widget.IngredientsWidgetProvider;
import com.google.gson.Gson;

import static com.bignerdranch.android.gaba.Model.Keys.RECIPE;

/**
 * Created by Matthew on 17/06/2018.
 */

public class SharedPreferences {

    public static Recipe getSharedPreferences(Context context) {
        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(RECIPE, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return recipe;
    }

    public static void setSharedPreferences(Context context, Recipe recipe, Application application) {
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(RECIPE, json)
                .apply();


        // send intent for widget
        Intent widgetIntent = new Intent(context, IngredientsWidgetProvider.class);
        widgetIntent.setAction(Keys.ACTION_UPDATE_WIDGET);
        int[] ids = AppWidgetManager.getInstance(application)
                .getAppWidgetIds(new ComponentName(application, IngredientsWidgetProvider.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        application.sendBroadcast(widgetIntent);

        // send intent to RecipeActivity
        Intent intent = new Intent(application, RecipeActivity.class);
        intent.putExtra(RECIPE, recipe);
        application.startActivity(intent);

    }
}
