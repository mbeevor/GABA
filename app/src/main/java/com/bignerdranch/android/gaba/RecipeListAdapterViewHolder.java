package com.bignerdranch.android.gaba;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Matthew on 22/05/2018.
 */

public class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private List<Recipe> recipeList;
    private RecipeListAdapter.OnItemClickHandler onItemClickHandler;


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
                onItemClickHandler.onItemClick(view, position);
            }
        }

    }
}
