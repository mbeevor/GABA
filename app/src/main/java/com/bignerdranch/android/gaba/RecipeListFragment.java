package com.bignerdranch.android.gaba;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeListFragment extends Fragment {

    @BindView(R.id.recylcerview_main_activity) RecyclerView recyclerView;


    // empty constructor
    public RecipeListFragment() {

    }

    // define recipe click listener and interface
    OnRecipeClickListener onRecipeClickListener;

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onRecipeClickListener = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ButterKnife.apply(getView());
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // create GridLayoutManager
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        // Return the root view
        return rootView;
    }
}
