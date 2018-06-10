package com.bignerdranch.android.gaba.Utilities;

import android.text.TextUtils;

import com.bignerdranch.android.gaba.Model.Ingredients;
import com.bignerdranch.android.gaba.Model.Keys;
import com.bignerdranch.android.gaba.Model.Recipe;
import com.bignerdranch.android.gaba.Model.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Matthew on 22/05/2018.
 */

public class QueryUtils {



    public static ArrayList<Recipe> getSimpleRecipeQueryStringFromJson(String recipeJson) {

       if (TextUtils.isEmpty(recipeJson)) {
            return null;
        }

        ArrayList<Recipe> recipesList = new ArrayList<>();

        try {

            JSONArray recipeListArray = new JSONArray(recipeJson);

            for (int i = 0; 1 <recipeListArray.length(); i++) {

            JSONObject recipeObject = recipeListArray.getJSONObject(i);

            String recipeId = recipeObject.getString(Keys.ID);
            String recipeName = recipeObject.getString(Keys.NAME);

            JSONArray ingredientsArray = recipeObject.getJSONArray(Keys.INGREDIENTS);
                ArrayList<Ingredients> ingredientsList = new ArrayList<>();


                for (int j = 0; j <ingredientsArray.length(); j++) {

                JSONObject currentRecipe = ingredientsArray.getJSONObject(j);

                String quantity = currentRecipe.getString(Keys.QUANTITY);
                String measure = currentRecipe.getString(Keys.MEASURE);
                String ingredient = currentRecipe.getString(Keys.INGREDIENT);


                Ingredients ingredientItem = new Ingredients(quantity, measure, ingredient);
                ingredientsList.add(ingredientItem);
            }


            JSONArray stepsArray = recipeObject.getJSONArray(Keys.STEPS);
                ArrayList<Steps> stepsList = new ArrayList<>();


                for (int k = 0; k <stepsArray.length(); k++) {

                JSONObject currentRecipe = stepsArray.getJSONObject(k);

                String id = currentRecipe.getString(Keys.STEPS_ID);
                String shortDescription = currentRecipe.getString(Keys.SHORT_DESCRIPTION);
                String description = currentRecipe.getString(Keys.DESCRIPTION);
                String videoUrl = currentRecipe.getString(Keys.VIDEO_URL);
                String thumbnailUrl = currentRecipe.getString(Keys.THUMBNAIL);

                Steps step = new Steps(id, shortDescription, description, videoUrl, thumbnailUrl);
                stepsList.add(step);

            }

            String numberServings = recipeObject.getString(Keys.SERVINGS);
            String recipeImage = recipeObject.getString(Keys.IMAGE);


            Recipe recipe = new Recipe(recipeId,
                    recipeName,
                    ingredientsList,
                    stepsList,
                    numberServings,
                    recipeImage);
            recipesList.add(recipe);

        }

        } catch (JSONException e) {
                e.printStackTrace();
            }

        return recipesList;

    }




}
