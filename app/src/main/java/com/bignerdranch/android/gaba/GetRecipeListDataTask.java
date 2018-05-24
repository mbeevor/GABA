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


    public GetRecipeListDataTask(AsyncTaskListener aysncTaskListener) {

        delegate = aysncTaskListener;
    }

    @Override
    protected List<Recipe> doInBackground(URL... urls) {
        try {

            if (urls != null) {

                URL queryUrl = urls[0];
                String httpQuery = NetworkUtils.getResponseFromHttpUrl(queryUrl);

                if (httpQuery != null) {
                    recipeList = QueryUtils.getSimpleRecipeQueryStringFromJson(httpQuery);
                    return recipeList;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        recipeList = recipes;

        if (recipeList != null) {
            delegate.onTaskComplete(recipeList);
        }

    }
}
