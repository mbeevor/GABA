package com.bignerdranch.android.gaba.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.bignerdranch.android.gaba.MainActivity;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.RecipesPreferences;
import com.bignerdranch.android.gaba.R;

/**
 * Created by Matthew on 16/06/2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    private Recipe recipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        // open Main Activity if widget clicked
        Intent detailIntent = new Intent(context, MainActivity.class);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, detailIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_layout, newPendingIntent);

        if (RecipesPreferences.getRecipePreferences(context) == null) {
            views.setTextViewText(R.id.widget_title_tv, "No recipe selected");
        } else {
            views.setTextViewText(R.id.widget_title_tv, RecipesPreferences.getRecipePreferences(context).getRecipeName());

            Intent intent = new Intent(context, IngredientsWidgetService.class);
            views.setRemoteAdapter(R.id.widget_listview, intent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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
