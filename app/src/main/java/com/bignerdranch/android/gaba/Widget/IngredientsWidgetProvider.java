package com.bignerdranch.android.gaba.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.bignerdranch.android.gaba.DetailActivity;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;

import static com.bignerdranch.android.gaba.Model.Keys.ACTION_UPDATE_WIDGET;
import static com.bignerdranch.android.gaba.Model.Keys.INGREDIENTS_LIST;
import static com.bignerdranch.android.gaba.Model.Keys.RECIPE_NAME;
import static com.bignerdranch.android.gaba.Model.Keys.STEPS_LIST;

/**
 * Created by Matthew on 16/06/2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();

        if (action.equals(ACTION_UPDATE_WIDGET)) {

            recipeName = intent.getStringExtra(RECIPE_NAME);
            ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
            stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName widget = new ComponentName(context.getApplicationContext(), IngredientsWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
            onUpdate(context, appWidgetManager, appWidgetIds);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(widget), R.id.widget_listview);

        }

        super.onReceive(context, intent);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        IngredientsWidgetService.updateWidgetIntent(context, recipeName, ingredientsList, stepsList);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, String name, ArrayList ingredients, ArrayList steps, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, name, ingredients, steps, appWidgetId);

        }
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        String name, ArrayList ingredients, ArrayList steps, int appWidgetId) {


        Intent detailIntent = new Intent(context, DetailActivity.class);
        Bundle extras = new Bundle();
        extras.putString(RECIPE_NAME, name);
        extras.putParcelableArrayList(INGREDIENTS_LIST, ingredients);
        extras.putParcelableArrayList(STEPS_LIST, steps);
        detailIntent.putExtras(extras);

        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, detailIntent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.widget_title_tv, name);
        // TODO: add listview?
        views.setOnClickPendingIntent(R.id.widget_layout, newPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }



    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }

}
