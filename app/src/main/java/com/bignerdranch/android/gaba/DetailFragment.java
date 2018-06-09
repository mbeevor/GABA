package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.gaba.Adapters.IngredientsListAdapter;
import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Steps;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    public RecyclerView ingredientsRecyclerview;
    public IngredientsListAdapter ingredientsListAdapter;
    private String recipeId;
    private String recipeName;
    private ArrayList<Ingredients> ingredientsList;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle recipeBundleForFragment) {
        recipeBundleForFragment = getArguments();
        if (recipeBundleForFragment != null) {
            recipeName = recipeBundleForFragment.getString("recipeName");
            ingredientsList = recipeBundleForFragment.getParcelableArrayList("ingredientsList");
        };

        View rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

        ingredientsRecyclerview = rootView.findViewById(R.id.ingredients_recycler_view);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerview.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        ingredientsRecyclerview.setHasFixedSize(true);

        ingredientsListAdapter = new IngredientsListAdapter(ingredientsList);
        ingredientsRecyclerview.setAdapter(ingredientsListAdapter);
        ingredientsListAdapter.notifyDataSetChanged();
        return rootView;

        }

    }

