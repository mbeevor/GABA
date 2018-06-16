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
        super.onReceive(context, intent);

        final String action = intent.getAction();

        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

            recipeName = intent.getStringExtra(RECIPE_NAME);
            ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
            stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName widget = new ComponentName(context.getApplicationContext(), IngredientsWidgetProvider.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
            onUpdate(context, appWidgetManager, appWidgetIds);

        }

    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.ingredients_widget
            );

            if (recipeName != null) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString(RECIPE_NAME, recipeName);
                extras.putParcelableArrayList(INGREDIENTS_LIST, ingredientsList);
                extras.putParcelableArrayList(STEPS_LIST, stepsList);
                detailIntent.putExtras(extras);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                Intent intent = new Intent(context, IngredientsWidgetService.class);
                views.setRemoteAdapter(R.id.widget_listview, intent);
                views.setTextViewText(R.id.widget_title_tv, recipeName);
                views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
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
