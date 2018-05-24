package com.bignerdranch.android.gaba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Matthew on 21/05/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapterViewHolder>{

    private List<Recipe> recipeList;
    private Context context;
    private OnItemClickHandler onItemClickHandler;

    public interface OnItemClickHandler {
        void onItemClick(View item, int position);
    }

    //default constructor
    public RecipeListAdapter(Context thisContext, OnItemClickHandler handler) {
        context = thisContext;
        onItemClickHandler = handler;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutForListItem, parent, false);
        RecipeListAdapterViewHolder viewHolder = new RecipeListAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {

        if (recipeList != null) {

            Recipe recipe = recipeList.get(position);
            String recipeName = recipe.getRecipeName();

            RecipeListAdapterViewHolder recipeListAdapterViewHolder = holder;
            recipeListAdapterViewHolder.recipeNameTextView.setText(recipeName);
        }

    }


    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        } else{
            return recipeList.size();
        }
    }
}
