package com.bignerdranch.android.gaba.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.RecipesPreferences;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 16/06/2018.
 */


public class IngredientsWidgetService extends RemoteViewsService {

    private Context context;
    private List<String> ingredients;

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

        }

        @Override
        public void onDataSetChanged() {
            loadIngredients();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredients != null) return ingredients.size();
            else return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (ingredients == null) {
                return null;
            } else {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
                remoteViews.setTextViewText(R.id.widget_list_item_tv, ingredients.get(position));
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

    private void loadIngredients() {

        if (RecipesPreferences.getRecipePreferences(context) != null) {
            ingredients.clear();
            for (Ingredients ingredientsItem : RecipesPreferences.getRecipePreferences(context).getIngredientsList()) {
                ingredients.add(String.valueOf(ingredientsItem.getItemIngredient()));
            }
        } else {
            ingredients = null;
        }

    }
}


