package com.bignerdranch.android.gaba.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mbeev on 07/06/2018.
 */

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.IngredientsListAdapterViewHolder> {

    private List<Ingredients> ingredientsList;
    private Context context;

    //default constructor
    public IngredientsListAdapter(Context thisContext) {
        context = thisContext;
        setHasStableIds(true);
    }

    @Override
    public IngredientsListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int layoutForIngredients = R.layout.list_item_ingredient;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(layoutForIngredients, parent, false);
        IngredientsListAdapterViewHolder viewHolder = new IngredientsListAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsListAdapterViewHolder holder, int position) {


        Ingredients ingredients = ingredientsList.get(position);
        String itemQuantity = ingredients.getItemQuantity();
        String itemMeasure = ingredients.getItemMeasure();
        String itemIngredient = ingredients.getItemIngredient();

        IngredientsListAdapterViewHolder viewHolder = holder;

        String ingredientString = itemQuantity + " " + itemMeasure + " " + itemIngredient;
        viewHolder.ingredientTextView.setText(ingredientString);

    }

    @Override
    public int getItemCount() {

        if (ingredientsList == null) {
            return 0;
        } else {
            return ingredientsList.size();
        }
    }

    public void setData(ArrayList<Ingredients> ingredients) {

        ingredientsList = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientsListAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_tv) public TextView ingredientTextView;

        public IngredientsListAdapterViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}