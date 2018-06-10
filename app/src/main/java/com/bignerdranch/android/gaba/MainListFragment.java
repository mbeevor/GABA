package com.bignerdranch.android.gaba;

import android.app.ProgressDialog;
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

import com.bignerdranch.android.gaba.Adapters.AsyncTaskListener;
import com.bignerdranch.android.gaba.Adapters.GetRecipeListDataTask;
import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Utilities.NetworkUtils;

import java.net.URL;
import java.util.List;


public class MainListFragment extends Fragment {

    public RecyclerView recyclerView;
    public List<Recipe> recipeList;
    public RecipeListAdapter recipeListAdapter;
    public RecipeListAdapter.OnRecipeClickListener onRecipeClickListener;

    // empty constructor
    public MainListFragment() {

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


        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading");
        progressDialog.show();


        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_list_recyclerview);

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

        });

        recyclerView.setAdapter(recipeListAdapter);
        loadRecipeList();

        progressDialog.dismiss();

        // Return the root view
        return rootView;
    }

    private void loadRecipeList() {

        URL getRecipeListUrl = NetworkUtils.recipeUrl();
        new GetRecipeListDataTask(new GetRecipeDataListener())
                .execute(getRecipeListUrl);

    }


    public class GetRecipeDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(List<Recipe> list) {

            recipeList = list;

            if (recipeList != null){
                recipeListAdapter.updateRecipeData(recipeList);
            }

        }
    }

    // method to calculate size of Grid based on device configuration
    private int setGridColumns() {

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
