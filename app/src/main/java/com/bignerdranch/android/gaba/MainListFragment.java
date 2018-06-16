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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.android.gaba.Adapters.AsyncTaskListener;
import com.bignerdranch.android.gaba.Adapters.GetRecipeListDataTask;
import com.bignerdranch.android.gaba.Adapters.RecipeListAdapter;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Utilities.NetworkUtils;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainListFragment extends Fragment {

    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    @BindView(R.id.empty_view)
    public TextView emptyTextView;
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
            onRecipeClickListener = (RecipeListAdapter.OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_activity_main, container, false);
        ButterKnife.bind(this, rootView);


        recyclerView = rootView.findViewById(R.id.list_recyclerview);

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


        // Return the root view
        return rootView;
    }

    public void loadRecipeList() {

        URL getRecipeListUrl = NetworkUtils.recipeUrl();
        new GetRecipeListDataTask(new GetRecipeDataListener())
                .execute(getRecipeListUrl);


    }


    public class GetRecipeDataListener implements AsyncTaskListener {

        @Override
        public void onTaskComplete(List<Recipe> list) {

            recipeList = list;

            if (recipeList != null) {
                recipeListAdapter.updateRecipeData(recipeList);
                showResultsView();
            } else {
                showErrorView();
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

    private void showLoadingView() {

        // hide empty text view and show progress bar
        progressBar.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
    }

    private void showResultsView() {

        // hide the error message and show the recycler view
        progressBar.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {

        // hide the recycler view and show the error message text view
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.VISIBLE);
    }
}
