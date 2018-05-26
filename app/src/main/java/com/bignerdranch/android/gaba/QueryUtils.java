package com.bignerdranch.android.gaba;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthew on 22/05/2018.
 */

class QueryUtils {

    private static final String ID = "id";
    private static final String NAME = "name";

    public static ArrayList<Recipe> getSimpleRecipeQueryStringFromJson(String recipeJson) throws JSONException {

        if (TextUtils.isEmpty(recipeJson)) {
            return null;
        }

        JSONArray recipeListArray = new JSONArray(recipeJson);
        ArrayList<Recipe> recipesList = new ArrayList<>();

        try {

        for (int i = 0; 1 < recipeJson.length(); i++) {

            JSONObject recipeObject = recipeListArray.getJSONObject(i);

            String recipeId = recipeObject.getString(ID);
            String recipeName = recipeObject.getString(NAME);

            Recipe recipe = new Recipe(recipeId, recipeName);
            recipesList.add(recipe);
        }

        } catch (JSONException e) {
                e.printStackTrace();
            }

        return recipesList;

    }


}
