package com.bignerdranch.android.gaba.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static com.bignerdranch.android.gaba.Model.Keys.ACTION_UPDATE_WIDGET;
import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_LIST;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 16/06/2018.
 */



public class IngredientsWidgetService extends IntentService {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;

    public IngredientsWidgetService() {
        super("IngredientsWidgetService");
    }

    public static void updateWidgetIntent(Context context, String name, ArrayList ingredients, ArrayList steps) {

        Intent intent = new Intent(context, IngredientsWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(RECIPE_NAME, name);
        intent.putExtra(INGREDIENTS_LIST, ingredients);
        intent.putExtra(STEPS_LIST, steps);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_APPWIDGET_UPDATE.equals(action)) {
                recipeName = intent.getStringExtra(RECIPE_NAME);
                ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
                stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName widget = new ComponentName(this, IngredientsWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
        IngredientsWidgetProvider.updateWidgets(this, appWidgetManager, recipeName,
                ingredientsList, stepsList, appWidgetIds);

    }
}