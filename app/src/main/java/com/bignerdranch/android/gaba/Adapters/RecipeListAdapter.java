package com.bignerdranch.android.gaba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.gaba.R;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        int layoutForListItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(layoutForListItem, parent, false);
        RecipeListAdapterViewHolder viewHolder = new RecipeListAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapterViewHolder holder, int position) {

        if (recipeList != null) {

            Recipe recipe = recipeList.get(position);
            String recipeName = recipe.getRecipeName();
            String imageView = recipe.getRecipeImage();
            String numberServings = recipe.getNumberServings();

            RecipeListAdapterViewHolder recipeListAdapterViewHolder = holder;

            // set recipe name
            recipeListAdapterViewHolder.recipeNameTextView.setText(recipeName);
            recipeListAdapterViewHolder.numberServingsTextView.setText(numberServings);

            // check if there is an image to display, if not use default
            if (TextUtils.isEmpty(imageView)) {
                recipeListAdapterViewHolder.recipeNameImageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                Picasso.with(recipeListAdapterViewHolder.recipeNameImageView.getContext())
                        .load(imageView)
                        .into(recipeListAdapterViewHolder.recipeNameImageView);
            }
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


        @BindView(R.id.recipe_name_tv) public TextView recipeNameTextView;
        @BindView(R.id.recipe_name_iv) public ImageView recipeNameImageView;
        @BindView(R.id.number_servings_tv) public TextView numberServingsTextView;

        public RecipeListAdapterViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
