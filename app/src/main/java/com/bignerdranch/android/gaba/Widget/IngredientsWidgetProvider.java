package com.bignerdranch.android.gaba.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.bignerdranch.android.gaba.MainActivity;
import com.bignerdranch.android.gaba.Model.SharedPreferences;
import com.bignerdranch.android.gaba.R;

import static com.bignerdranch.android.gaba.Model.Keys.ACTION_UPDATE_WIDGET;

/**
 * Created by Matthew on 16/06/2018.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        // open Main Activity if widget heading clicked
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent newPendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0);
        views.setOnClickPendingIntent(R.id.widget_heading, newPendingIntent);

        // check for recipe to find list from shared preferences
        if (SharedPreferences.getSharedPreferences(context) != null) {

            views.setTextViewText(R.id.widget_title_tv, SharedPreferences.getSharedPreferences(context).getRecipeName());
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
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String action = intent.getAction();

        if (action.equals(ACTION_UPDATE_WIDGET)) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
            ComponentName widget = new ComponentName(context.getApplicationContext(), IngredientsWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);
            onUpdate(context, appWidgetManager, appWidgetIds);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_title_tv);

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
