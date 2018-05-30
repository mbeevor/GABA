package com.bignerdranch.android.gaba;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;
import java.util.List;


public class RecipeListFragment extends Fragment {

    RecyclerView recyclerView;
    public List<Recipe> recipeList;
    private RecipeListAdapter recipeListAdapter;
    RecipeListAdapter.OnRecipeClickListener onRecipeClickListener;

    // empty constructor
    public RecipeListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onRecipeClickListener = (RecipeListAdapter.OnRecipeClickListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_list_recylerview);

        // create Grid LayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), setGridColumns());
        recyclerView.setLayoutManager(layoutManager);

        // set recyclerView to have a fixed size so that all items in the list are the same size.
        recyclerView.setHasFixedSize(true);

        recipeListAdapter = new RecipeListAdapter(getContext(), new RecipeListAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeSelected(List<Recipe> recipes, int position) {
                onRecipeClickListener.onRecipeSelected(recipeList, position);
            }

//            @Override
//            public void onItemClick(View item, int position) {
//                Recipe recipePosition = recipeList.get(position);
//                Recipe recipe = new Recipe(recipePosition.getRecipeId(),
//                        recipePosition.getRecipeName());
//                Intent detailFragmentIntent = new Intent(getContext(), DetailActivity.class)
//                        .putExtra("recipe", recipe);
//                startActivity(detailFragmentIntent);
//            }
        });

        recyclerView.setAdapter(recipeListAdapter);
        loadRecipeList();

        // Return the root view
        return rootView;
    }

    private void loadRecipeList() {

        URL getRecipeListUrl = NetworkUtils.recipeUrl();
        new GetRecipeListDataTask(new GetRecipeDataListener())
                .execute(getRecipeListUrl);

    }


    private class GetRecipeDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(List<Recipe> list) {

            recipeList = list;

            if (recipeList != null){
                recipeListAdapter.updateRecipeData(recipeList);
            }

        }
    }

    // method to calculate size of Grid based on device configuration
    public int setGridColumns() {

        int gridColumns = 0;

        switch (getResources().getConfiguration().orientation) {

            case Configuration.ORIENTATION_PORTRAIT:
                gridColumns = 1;
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                gridColumns = 2;
                break;
        }

        return gridColumns;

    }
}
