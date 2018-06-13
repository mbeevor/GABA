package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.gaba.Adapters.IngredientsListAdapter;
import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Keys;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {

    public RecyclerView ingredientsRecyclerview;
    public IngredientsListAdapter ingredientsListAdapter;
    private ArrayList<Ingredients> ingredientsList;

    public IngredientsFragment() {
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
            ingredientsList = recipeBundleForFragment.getParcelableArrayList(Keys.INGREDIENTS_LIST);
        }

        View rootView = inflater.inflate(R.layout.fragment_activity_list, container, false);

        ingredientsRecyclerview = rootView.findViewById(R.id.list_recyclerview);

        // create Linear LayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        ingredientsRecyclerview.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        ingredientsRecyclerview.setHasFixedSize(true);

        ingredientsListAdapter = new IngredientsListAdapter(getContext());
        ingredientsListAdapter.setData(ingredientsList);
        ingredientsRecyclerview.setAdapter(ingredientsListAdapter);
        return rootView;

        }

    }

