package com.bignerdranch.android.gaba.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;

/**
 * Created by Matthew on 16/06/2018.
 */


public class IngredientsWidgetService extends RemoteViewsService {

    private Context context;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewRemoteViewsFactory(this.getApplicationContext());
    }

    private class IngredientsListViewRemoteViewsFactory implements RemoteViewsFactory {
        public IngredientsListViewRemoteViewsFactory(Context applicationContext) {
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientsList != null) return ingredientsList.size();
            else return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (ingredientsList == null) {
                return null;
            } else {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
                Ingredients ingredients = ingredientsList.get(position);

                String ingredientName = ingredients.getItemIngredient();
                String ingredientQuantity = ingredients.getItemQuantity();
                String ingredientMeasure = ingredients.getItemMeasure();
                String ingredientString = ingredientName + "" + ingredientQuantity + "" + ingredientMeasure;

                remoteViews.setTextViewText(R.id.widget_list_item_tv, ingredientString);
                return remoteViews;
            }
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}


