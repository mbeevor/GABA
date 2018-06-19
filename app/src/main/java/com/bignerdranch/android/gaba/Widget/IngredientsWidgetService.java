package com.bignerdranch.android.gaba.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.SharedPreferences;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;

/**
 * Created by Matthew on 16/06/2018.
 */


public class IngredientsWidgetService extends RemoteViewsService {

    private Context context;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<String> ingredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListViewRemoteViewsFactory(this.getApplicationContext());
    }

    private class IngredientsListViewRemoteViewsFactory implements RemoteViewsFactory {

        IngredientsListViewRemoteViewsFactory(Context applicationContext) {

            context = applicationContext;
            ingredients = new ArrayList<>();
        }

        @Override
        public void onCreate() {
            loadIngredients();
        }

        @Override
        public void onDataSetChanged() {
            loadIngredients();
        }

        @Override
        public void onDestroy() {
            if (ingredients != null) {
                ingredients.clear();
            }
        }

        @Override
        public int getCount() {
            if (ingredients != null) {
                return ingredients.size();
            } else {
                return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            remoteViews.setTextViewText(R.id.widget_list_item_tv, ingredients.get(position));
            return remoteViews;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    private void loadIngredients() {

        if (SharedPreferences.getSharedPreferences(context) != null) {

            ingredientsList = SharedPreferences.getSharedPreferences(context).getIngredientsList();

            for (Ingredients i : ingredientsList) {

                String ingredient = i.getItemIngredient();
                String quantity = i.getItemQuantity();
                String measure = i.getItemMeasure();
                String ingredientString = quantity + " " + measure + " " + ingredient;

                ingredients.add(ingredientString);
            }
        }

    }
}


