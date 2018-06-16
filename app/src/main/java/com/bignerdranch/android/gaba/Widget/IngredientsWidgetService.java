package com.bignerdranch.android.gaba.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.R;

import java.util.List;

/**
 * Created by Matthew on 16/06/2018.
 */

public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class IngredientsRemoteViewsFactory implements RemoteViewsFactory {

        private Context context;
        private String recipeName;
        private List<Ingredients> ingredientsList;

        public IngredientsRemoteViewsFactory(Context applicationContext, Intent intent) {

            context = applicationContext;
        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {


        }

        @Override
        public void onDestroy() {

            if (ingredientsList != null) {
                ingredientsList = null;
            }

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Ingredients ingredients = ingredientsList.get(position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient);
            String ingredientName = ingredients.getItemIngredient();
            String ingredientQuantity = ingredients.getItemQuantity();
            String ingredientMeasure = ingredients.getItemMeasure();
            String ingredientString = ingredientName + "" + ingredientQuantity + "" + ingredientMeasure;
            remoteViews.setTextViewText(R.id.ingredient_tv, ingredientString);
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
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}