package com.bignerdranch.android.gaba.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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


public class IngredientsWidgetService implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;
    private ArrayList<Steps> stepsList;

    IngredientsWidgetService(Context applicationContext, Intent intent) {
        context = applicationContext;
        recipeName = intent.getStringExtra(RECIPE_NAME);
        ingredientsList = intent.getParcelableArrayListExtra(INGREDIENTS_LIST);
        stepsList = intent.getParcelableArrayListExtra(STEPS_LIST);
        loadIngredients();
    }

    public class IngredientsViewsService extends RemoteViewsService {

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {

            return new IngredientsWidgetService(this.getApplicationContext(), intent);
        }
    }

    private void loadIngredients() {


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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}