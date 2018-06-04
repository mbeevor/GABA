package com.bignerdranch.android.gaba.Utilities;

import android.text.TextUtils;

import com.bignerdranch.android.gaba.Model.Ingredients;
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

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INGREDIENTS_LIST = "ingredients";
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    private static final String STEPS = "steps";
    private static final String STEPS_ID = "id";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";
    private static final String THUMBNAIL = "thumbnailURL";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "image";

    public static ArrayList<Recipe> getSimpleRecipeQueryStringFromJson(String recipeJson) {

       if (TextUtils.isEmpty(recipeJson)) {
            return null;
        }

        ArrayList<Recipe> recipesList = new ArrayList<>();
        ArrayList<Ingredients> ingredientsList = new ArrayList<>();
        ArrayList<Steps> stepsList = new ArrayList<>();

        try {

            JSONArray recipeListArray = new JSONArray(recipeJson);

            for (int i = 0; 1 <recipeListArray.length(); i++) {

            JSONObject recipeObject = recipeListArray.getJSONObject(i);

            String recipeId = recipeObject.getString(ID);
            String recipeName = recipeObject.getString(NAME);

            JSONArray ingredientsArray = recipeObject.getJSONArray(INGREDIENTS_LIST);

            for (int j = 0; j <ingredientsArray.length(); j++) {

                JSONObject currentRecipe = ingredientsArray.getJSONObject(j);

                String quantity = currentRecipe.getString(QUANTITY);
                String measure = currentRecipe.getString(MEASURE);
                String ingredient = currentRecipe.getString(INGREDIENT);


                Ingredients ingredientItem = new Ingredients(quantity, measure, ingredient);
                ingredientsList.add(ingredientItem);
            }


            JSONArray stepsArray = recipeObject.getJSONArray(STEPS);

            for (int k = 0; k <stepsArray.length(); k++) {

                JSONObject currentRecipe = stepsArray.getJSONObject(k);

                String id = currentRecipe.getString(STEPS_ID);
                String shortDescription = currentRecipe.getString(SHORT_DESCRIPTION);
                String description = currentRecipe.getString(DESCRIPTION);
                String videoUrl = currentRecipe.getString(VIDEO_URL);
                String thumbnailUrl = currentRecipe.getString(THUMBNAIL);

                Steps step = new Steps(id, shortDescription, description, videoUrl, thumbnailUrl);
                stepsList.add(step);

            }

            String numberServings = recipeObject.getString(SERVINGS);
            String recipeImage = recipeObject.getString(IMAGE);


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
