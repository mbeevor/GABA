package com.bignerdranch.android.gaba;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 21/05/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder>{

    private List<Recipe> recipeList;
    private Context context;
    private OnRecipeClickListener onRecipeClickListener;

    public interface OnRecipeClickListener {
        void onRecipeSelected(List<Recipe> recipes, int position);
    }

    //default constructor
    public RecipeListAdapter(Context thisContext, OnRecipeClickListener listener) {
        context = thisContext;
        onRecipeClickListener = listener;
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

    public void updateRecipeData(List<Recipe> recipeData) {

        if (recipeData != null) {
            recipeList = new ArrayList<>(recipeData);
            notifyDataSetChanged();
        }

    }

    public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView recipeNameTextView;

        public RecipeListAdapterViewHolder(final View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (recipeList != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onRecipeClickListener.onRecipeSelected(recipeList, position);
                }
            }

        }
    }
}
