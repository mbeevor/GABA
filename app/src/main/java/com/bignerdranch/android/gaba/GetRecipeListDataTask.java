package com.bignerdranch.android.gaba;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 22/05/2018.
 */

class GetRecipeListDataTask extends AsyncTask<URL, Void, List<Recipe>> {

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
                    try {
                        recipeList = QueryUtils.getSimpleRecipeQueryStringFromJson(httpQuery);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
