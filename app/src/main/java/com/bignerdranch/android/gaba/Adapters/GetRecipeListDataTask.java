package com.bignerdranch.android.gaba.Adapters;

import android.os.AsyncTask;

import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Utilities.NetworkUtils;
import com.bignerdranch.android.gaba.Utilities.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 22/05/2018.
 */

public class GetRecipeListDataTask extends AsyncTask<URL, Void, List<Recipe>> {

    private List<Recipe> recipeList;
    private AsyncTaskListener delegate = null;


    public GetRecipeListDataTask(AsyncTaskListener asyncTaskListener) {

        delegate = asyncTaskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Recipe> doInBackground(URL... urls) {
        try {

            if (urls != null) {

                URL recipeUrl = urls[0];
                String httpQuery;
                httpQuery = NetworkUtils.getResponseFromHttpUrl(recipeUrl);
                if (httpQuery != null) {
                    recipeList = QueryUtils.getSimpleRecipeQueryStringFromJson(httpQuery);
                    return recipeList;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {

        if (recipes != null) {
            delegate.onTaskComplete(recipes);
        }

    }
}
